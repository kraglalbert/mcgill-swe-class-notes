# Query Evaluation

1. [Overview](#overview)
2. [Reduction Factor](#reduction-factor)
3. [Selection in Clustered B+ Trees](#selection-in-clustered-b+-trees)
4. [Selection in Unclustered B+ Trees](#selection-in-unclustered-b+-trees)
5. [Selections on 2 or More Attributes](#selections-on-2-or-more-attributes)
6. [External Sorting](#external-sorting)
7. [Equality Joins](#equality-joins)
8. [Other Operations](#other-operations)
9. [Execution Plan](#execution-plan)
10. [Optimization Techniques](#optimization-techniques)

### Overview

* Processing of SQL queries
  * The **parser** translates query strings into an internal query representation
    * Checks for syntax errors
    * Checks existence of relations and attributes
    * Replaces views with their definitions
  * The **query optimizer** translates the query into an efficient execution plan
  * The **plan executor** executes the execution plan and delivers data back to the client
* Query decomposition
  * Queries are composed of a few basic operators
    * e.g. SELECT, JOIN, etc.
  * Several algorithms exist for implementing each relational operator
    * No "best" algorithm exists
    * Algorithm choice depends on size of relation, existing indexes, size of buffer pool, etc.
* Access path: the method used to retrieve a set of tuples from a relation
* Cost model
  * Input:
    * The query
    * DB statistics (e.g. distribution of values for a particular attribute)
    * Resource availability and cost
  * Our cost model
    * Number of I/O = number of pages accessed
    * Assume that root and all intermediate nodes of B+ tree are in memory, but leaf nodes are not
    * A page $P$ might be accessed more than once
* Basic query processing operations
  * Selection $\sigma$
    * Scan through the entire relation if no index
    * Use the index if it exists for one or more of the selection attributes
  * Projection $\Pi$
  * Sorting, joining, grouping, and duplicate elimination
* Concatenation of operators: execution tree
  * Leaf nodes are the base relations
  * Inner nodes are the operators
  * Tuples from the base relation(s) flow into the parent operator nodes
    * Output from these operator nodes flows to their respective parents
  * Output from the root is the final result

### Reduction Factor

* The reduction factor is defined as:

  $rf(\sigma_{condition}(Rel)) = \frac{|\sigma_{condition}(Rel)|}{|Rel|}$

* In other words, it is the ratio of (*# returned tuples*) to (*total # of tuples in relation*)
* If the reduction factor is not known, the DBMS makes simple assumptions
  
  * e.g. assume uniform distribution: $\frac{1}{|R|}$ or $\frac{1}{|\text{possible values for attribute}|}$
* The size of a query result will then be $(\text{# input tuples})\times (\text{reduction factor})$

### Selection in Clustered B+ Trees

* Recall: a clustered index means that matching tuples are in *adjacent* data pages
* Cost:
  * Path from root the the leftmost leaf $lq$ with qualifying data entry
    * Inner pages are already in memory, so 1 I/O to retrieve $lq$ page
  * Retrieve page of first qualifying tuple: 1 I/O
  * Retrieve all following pages are long as search criteria is fulfilled
  * Each data page is only retrieved once
  * The number of data pages is $\frac{\text{# matching tuples}}{\text{tuples per page}}$
    * e.g. of 20% of tuples qualify then 20% of all data pages are returned
* Examples
  * Example 1: `SELECT * FROM Users WHERE uid = 123`
    * Only 1 tuple matches because `uid` is a primary key
    * I/O cost: 1 index leaf page + 1 data page
  * Example 2: `SELECT * FROM Users WHERE uname LIKE 'B%'`
    * Here the system estimates the number of matching tuples (e.g. around 100 tuples match)
    * Since the index is clustered, they are all on a few pages (e.g. 2 data pages)
    * I/O cost: 1 index leaf page + 2 data pages
  * Example 3: `SELECT * FROM Users WHERE uname < 'F'`
    * Estimate is that around 10,000 tuples match
    * If there are 40,000 tuples total, then this is 25% of all data pages
    * I/O cost: 1 index leaf page + (25% of all data pages)

### Selection in Unclustered B+ Trees

* Path to first qualifying leaf is the same as in clustered B+ trees

* What is the difference?

  * Each qualifying tuple can be in a different page, no adjacency

  * Retrieve page of first qualifying tuple: 1 I/O
  * Retrieve page of second, third, fourth, ... qualifying tuples
  * Sometimes a page may have already been fetched previously
    * It could still be in memory or it may have been replaced

* In the worst case, the number of data pages equals the number of matching tuples:

  $\text{# data pages} = \text{# matching tuples}$

* Examples
  * Example 1: `SELECT * FROM Users WHERE uid = 123`
    * Same as before
  * Example 2: `SELECT * FROM Users WHERE uname LIKE 'B%'`
    * Estimate that around 100 tuples match
    * In the worst case these tuples are in 100 different data pages
    * I/O cost: 1 index leaf page + 100 data pages
  * Example 3: `SELECT * FROM Users WHERE uname < 'F'`
    * Estimate is that around 10,000 tuples match (25% of total) 
    * Likely that nearly every data page has a matching tuple!
    * A simple scan is faster!

> **Lesson Learned:** 
>
> *Indices are usually only useful with very small reduction factors*.

### Selections on 2 or More Attributes

* Suppose we're querying for `A=100 AND B=50` and we have 500 pages total
* If we have no index, then we must access all 500 pages
* If we have an index on attribute A:
  * Get all tuples for A=100 through index, and then check the value of B for each
  * Cost is the same as the query for only A=100
* If we have 2 indexes (one for each attribute):
  * Find rids where A=100 through the index on A
  * Find rids where B=50 through the index on B
  * Build an intersection of the rids and then retrieve the appripriate data page for each rid
  * In some cases it's enough to just use the index on A (e.g. when A is unique or has a small reduction factor)
* What if we query for `A=100 OR B=50`?
  * With no index we still need to fetch all 500 pages
  * An index on the A attribute is not useful
  * If we have two indexes:
    * Find rids where A=100 through A index and vice versa for the B index
    * Build a **union** of rids instead of an intersection

### External Sorting

* Say we have some tuples and we want to sort on one of their attributes:

  ```
  (123, John, 2)
  (123, Adam, 8)
  (123, Bobby, 7)
  (123, Bobby, 8)
  ```

* Assuming a page contains 2 tuples and we want to sort by the last attribute, we can represent the tuples as follows:

  > Page 1: [2, 8]
  >
  > Page 2: [7, 8]

  * i.e. each "page" contains only the attributes we want to sort by

* In general, suppose we have $N$ pages and $B$ buffer frames

  * If $B \geq N$, then we can sort everything in-memory easily

  * If not:

    * Bring $B$ pages into buffer
    * Sort with any main memory sorting algorithm
    * Write out to disk to a temporary file
    * This is called a **run** of $B$ pages

  * In total, there will be $\lceil \frac{N}{B} \rceil$ runs for each **pass** through the sorting procedure

    * Each run produces an input frame which is fed into the next pass of the algorithm
    * If there is only one input frame, then this is actually the final output and the algorithm halts
    * If $(\text{# input frames}) \leq B$ then the final output can be obtained in that pass (using merge sort)
    * If $(\text{# input frames}) > B$ then the input frames are merged and at least one more pass is required

  * In general: 

    $\text{Number of passes} = \lceil \text{log}_{B-1}\lceil \frac{N}{B}\rceil\rceil$

    $Cost = 2N \times (\text{# of passes})$

* Sorting together with other operators
  * If everything fits into main memory, then only one pass is required
    * Read number of data pages
    * Sort and pipeline into next query operator
  * If 2 passes needed:
    * Pass 0: read # of pages, write # of pages (must write temp. results)
    * Pass 1: read # pages, sort and pipeline result into next operator
    * Cost: $3 \times (\text{# of pages})$
  * If 3 passes needed:
    * Cost: $5 \times (\text{# of pages})$

* Sorting in real life
  * Blocked I/O
    * Use more output pages and flush to consecutive blocks on disk
    * Might lead to more I/O but each I/O is cheaper
  * Other optimizations
    * At write in pass 0 to disk (if needed):
      * Do projection on necessary attributes, making each tuple smaller and leading to less pages
      * Projection is pushed to the lowest level possible

### Equality Joins

* Join cardinality estimation
  * $|Users \bowtie GroupMembers| = ?$
    * Join attribute is primary key for Users
    * Each GroupMember tuple matches exactly with one Users tuple
    * Therefore the result is $|GroupMembers|$
  * $|Users \times GroupMembers| = |Users| \times |GroupMembers|$
  * For other joins the cardinality is more difficult to estimate
  * $|Users \bowtie \sigma_{stars>3} (GroupMembers)|$
    * Result: $|\sigma_{stars>3} (GroupMembers)|$
  * $|\sigma_{experience>5}(Users) \bowtie GroupMembers|$
    * Assuming experience is in the range of 1-10, the reduction factor on Users is 0.5
    * Result: $\frac{1}{2}|GroupMembers|$

* Simple nested loop join

  ```python
  for tuple u in U:
  	for tuple g in GM:
  		if u.uid == g.uid:
  			# add <u, g> to result
  ```

  * For each tuple in the outer relation (Users) we scan the entire inner relation (GroupMembers)
  * Cost: $(\text{# UserPages}) + |Users|\times \text{(# GroupMemberPages)}$
  * Not good—we need a page-oriented algorithm

* Page nested loop join

  * For each page $p_u$ of Users U, get each page $p_g$ of GroupMembers GM

  ```python
  for page p_u in U:
  	for page p_g in GM:
  		for each tuple u in p_u:
  			for each tuple g in p_g:
  				if u.uid == g.uid:
  					# add <u, g> to result
  ```

  * Cost: $(\text{# UserPages}) + (\text{# UserPages})\times \text{(# GroupMemberPages)}$

* Block nested loop join
  * For each block of pages $bp_u$ of Users U, get each page $p_g$ of GroupMembers GM
  * Block of pages $bp_u$ and one page of GM must fit into main memory
    * Load block into main memory
    * Get first page from GM; do all matching between users in $bp_u$ and group members in first GM page
    * Get second page from GM; replace the first GM page in memory
    * Repeat for all pages of GM
  * Cost: $(\text{# UserPages}) + \frac{(\text{# UserPages})}{|bp_u|} \times \text{(# GroupMemberPages)}$

* Index nested loop join
  * For each tuple in the outer relation Users U we find the matching tuples in GroupMembers GM through an index
  * Condition: GM must have an index on the join attribute
  * The index **must** be on the inner relation
  * Cost: $\text{(# OuterPages)} + CARD(OuterRelation) \times (\text{cost of finding matching tuples in the inner relation})$

* Block nested loop vs. Index

  * Best case for block nested loop (if outer relation fits in memory):

    $(\text{# OuterPages}) + (\text{# InnerPages})$

  * Best case for index nested loop:

    $(\text{# OuterPages}) + CARD(Outer) \times (\text{# matching Inner tuples})$

  * Index nested loop wins if:

    $(\text{# InnerPages}) > CARD(Outer) \times \text{(# matching Inner tuples)}$

    * e.g. this could happen if Outer is the result of a selection that only selected very few tuples

* Sort-merge join

  * Sort U and GM on the join column, then scan them to do a "merge" on the join column and output result tuples
  * In loop:
    * Assume the scan cursor currently points to tuple $u$ in U and tuple $g$ in GM
    * Advance scan cursor of U until $u.uid \geq g.uid$ and then advance scan cursor of GM so that $g.uid \geq u.uid$
    * Do this until $u.uid=g.uid$
    * At this point, all U tuples with same value in uid (current U group) and all GM tuples with the same value in uid (current GM group) match
    * Output <u, g> for all pairs of such tuples 

  * U is scanned once; each GM group is scanned once per matching U tuple
    * Multiple scans of a GM group are likely to find needed pages in buffer
  * Cost:
    * If relations are already sorted: $(\text{# UserPages}) + (\text{# GMPages})$
    * If relations need to be sorted:
      * Sort relations and write sorted relations to temporary stable storage

### Other Operations

* Projection
  * Usually done on the fly with another operation (or pipelined)
  * More complex: `DISTINCT`
    * Expensive operation!
    * Requires sorting in order to eliminate duplicates
    * Often done at the very end (since there are less tuples) or whever the relation is sorted for some other reason
    * As a database user, use `DISTINCT` only when absolutely necessary
* Set operations
  * Intersection and cross-product are special cases of joins
  * Union and Except are similar
  * Sorting-based approach to union:
    * Sort both relations (on combination of all attributes)
    * Scan sorted relations and merge them
* Aggregate operations
  * Usually done at the very last step after all selections, joins, etc.
  * Without grouping
    * In general, this requires scanning the relation
  * With grouping
    * Sort on `GROUP BY` attributes, then scan relation and compute aggregate for each group
    * Can improve upon this by combining sorting and aggregate computation

### Execution Plan

* A plan describes how a query is executed

* Step-by-step example

  ```sql
  SELECT GM.gid, COUNT(*), AVG(U.age)
  FROM Users U, GroupMembers GM
  WHERE U.uid = GM.uid AND U.experience = 10
  GROUP BY GM.gid
  HAVING AVG(U.age) < 25
  ```

  * Scan the Users table, determine all tuples with experience = 10 (via scan or index)
    * Results are placed in intermediate relation R1
  * Join R2 and GroupMembers
    * This gives intermediate relation R2
  * Eliminate attributes other than gid and age
    * Results in R3
  * Group the tuples of R3 on gid (done by sort)
    * Results in R4
  * Scan R4, count and perform average
    * Results in R5
  * Return all tuples with `AVG(age) < 25`

* Pipelining
  * Execution within one operator
    * As soon as a tuple is determined it is forwarded to the next operator
  * Parallel execution of operators!
  * No materialization of intermediate relations if it can be avoided
  * For a block nested loop join, can pass along tuples as soon as there are enough to fill a block

### Optimization Techniques

* Algebraic optimization
  * Use simple rules to perform operations that eliminate a lot of tuples first
    * e.g. push down selections and projections
* Cost-based optimizations
  * Consider a set of alternative plans created by algebraic optimization
    * Use dynamic programming in bottom-up fashion for deep join plans
    * Pass 1: find best 1-relation plan for each relation
    * Pass 2: find best way to join result of (N-1)-relation plan (as outer) to another relation (all 2-relation plans)
    * Pass N: Find best way to join result of a (N-1)-relation plan (as outer) to the N-th relation (all N-relation plans)
    * This approach prunes high-cost alternatives
    * Beneficial to retain alternatives with interesting features, e.g. cheapest plan overall and cheapest plan for each *interesting order* of the tuples
  * Consider for each operator how it could be executed
    * Key issues: available indexes, available operator implementations
* Push projections
  * Pushing down projections does not reduce the number of tuples, but it does reduce the **size** of each tuple in intermediate results
  * Be careful to not lose attributes that are needed later on
* System tuning
  * If your application has some standard, well-known queries then it would be smart to create the appropriate indices to speed up these queries
  * If your application has many updates and inserts:
    * Careful with creating indices
    * Each `INSERT` will insert new value in each tuple
    * Updates change some indices