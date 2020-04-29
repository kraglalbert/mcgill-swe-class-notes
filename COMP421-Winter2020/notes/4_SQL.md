# SQL

1. [The SELECT Statement](#the-select-statement)
2. [Multirelational Queries](#multirelational-queries)
3. [Nested Queries](#nested-queries)
4. [Miscellaneous](#miscellaneous)
5. [Integrity Constraints](#integrity-constraints)

### The SELECT Statement

* Structure of a query:

  ```sql
  SELECT desired attributes
  FROM list of relations
  WHERE condition is true (optional)
  ```

* Example:

  ```sql
  SELECT sname, rating
  FROM skaters
  WHERE rating > 9 OR age < 12
  ```

* If a tuple satisfies the `WHERE` clause, then it is added to the output

* Set vs. multi-set (SQL vs. relational algebra)

  * No elimination of duplicates in SQL (as long as there is no violation of primary key/uniqueness constraint)
  * Results of SQL queries are generally not sets

* Boolean operators in the `WHERE` clause

  * Comparisons can be combined using `AND`, `OR`, and `NOT`

* Strings

  * `name LIKE '%e_g'`
  * `%` represents any string
  * `_` represents any character

* How can you eliminate duplicates?

  * Use `DISTINCT`
  * Example: `SELECT DISTINCT age FROM skaters`

* Can use `*` to select all attributes in a relation

  * Example: `SELECT * FROM skaters WHERE rating < 9`

* Columns in the output of a `SELECT` can be renamed

  * Example:

    ```sql
    SELECT sname, 
    	rating AS reality,
    	rating+1 AS upgrade,
    	'10' AS dream
    FROM skaters
    ```

    Output columns: `sname, reality, upgrade, dream`

* Ordering data

  * Use `ORDER BY`

  * Example:

    ```sql
    SELECT *
    FROM skaters
    ORDER BY age, rating
    ```

### Multirelational Queries

* Cross-product

  * List multiple relations in the `FROM` clause
  * Example: `SELECT * FROM skaters, participates`

* Join

  * Combination of cross-product and selection

  * Must indicate comparison, even with natural join

  * Example: "*Give me the names of all skaters that participated in a competition*"

    ```sql
    SELECT sname
    FROM skaters, participates
    WHERE skaters.sid = participates.sid
    
    SELECT sname
    FROM skaters JOIN participates
    ON skaters.sid = participates.sid
    ```

* Range variables

  * Optional, but **must be used** when the same relation appears twice in the `FROM` clause

  * Example: "*Find all skaters that have participated in the same competition*"

    ```sql
    SELECT p1.sid, p2.sid
    FROM particpates p1, participates p2
    WHERE p1.cid = p2.cid AND p1.sid < p2.sid
    ```

* Union, intersection, difference

  * Input relations for set operators must be set-compatible, i.e. they must have:

    * The same number of attributes
    * The attributes, taken in order, must have the same type

  * Result relation is a **set** (not multi-set)

  * Many systems do not provide primitives for intersection and difference

  * Union example: "*Find skaters' sid that have participated in a regional or a local competition*"

    ```sql
    SELECT P.sid
    FROM participates P, competition C
    WHERE P.cid = C.cid AND (C.type = 'regional' OR C.type = 'local')
    
    SELECT P.sid
    FROM participates P, competition C
    WHERE P.cid = C.cid AND C.type = 'local'
    UNION
    SELECT P.sid
    FROM participates P, competition C
    WHERE P.cid = C.cid AND C.type = 'regional'
    ```

  * Intersect example: "*Find skaters' sid that have participated in a regional and a local competition*"

    ```sql
    SELECT P.sid
    FROM participates P, competition C
    WHERE P.cid = C.cid AND C.type = 'local'
    INTERSECT
    SELECT P.sid
    FROM participates P, competition C
    WHERE P.cid = C.cid AND C.type = 'regional'
    ```

  * Use `EXCEPT` for set difference

* Multiset semantics
  * Multiset union, e.g. $\{1,2,2\} \cup \{1,2,3,3\} = \{1,1,2,2,2,3,3\}$
    * Sum the times an element appears in the two multisets
  * Multiset intersection, e.g. $\{1,2,2\} \cap \{1,1,2,2,3,3\} = \{1,2,2\}$
    * Take the minimum of the number of occurences in each multiset
  * Multiset difference, e.g. $\{1,2,2\} - \{1,2,3,3\} = \{2\}$
    * Subtract the number of occurences in the multisets
* Although SQL generally works with multisets, it uses set semantics for union/intersection/difference
  
  * To enforce multiset semantics, use `UNION ALL`, `INTERSECT ALL`, `EXCEPT ALL`

### Nested Queries

* A `WHERE` clause can itself contain an SQL query

  * The inner query is called a **subquery**

* Can be achieved using the `IN` operator

* Example:

  ```sql
  SELECT sname
  FROM skaters
  WHERE sid IN (SELECT sid FROM participates WHERE cid = 101)
  ```

  * Can also use `NOT IN`
  
* Correlated subqueries

  * A subquery is **correlated** if it references the outer query
  * Executes once for each candidate row considered by the outer query
  * The inner query is driven by the outer query

### Miscellaneous 

* The `EXISTS` operator

  * `EXISTS <relation>` is true if and only if the relation is non-empty

  * Example: "*Find names of skaters who have participated in competition 101*"

    ```sql
    SELECT S.name
    FROM skaters S
    WHERE EXISTS (SELECT * 
    			  FROM participates P
    			  WHERE P.cid = 101 AND P.sid = S.sid)
    ```

  * Scoping rule: to refer to the outer skater in the inner subquery, we need to give a range variable to the outer relation

  * A subquery that refers to values from a surrounding query is called a **correlated subquery**

  * Since the inner query depends on the row of the outer query it must be reevaluated for each row in the outer query

* Quantifiers

  * `ANY` and `ALL` behave as existential and universal quantifiers, respectively

  * Syntax

    * `WHERE attr op ANY (SELECT ...)`
    * `WHERE attr op ALL (SELECT ...)`
    * `op` is one of `<, =, >, <>, <=, >=`

  * Example: "*Find the skater with the highest rating*"

    ```sql
    SELECT *
    FROM skaters
    WHERE rating >= ALL (SELECT rating FROM skaters)
    ```

### Integrity Constraints

* An integrity constraint describes conditions that every *legal* instance of a relation must satisfy
  
  * i.e. restrictions on attribute values of tuples
* Inserts/updates/deletes that violate integrity constraints are disallowed
* Covered so far:
  * On individual tuples
    * Data type; NOT NULL
  * For relation as a whole
    * Primary key and unique constraints
    * No two tuples may have the same value
  * Across relations
    * Referential integrity through foreign key constraint
* Rule of thumb to classify an integrity constraint
  * If you can "see" only that tuple and the schema definition, can you say if it will fail the integrity constraint?
    * If yes, then it is an attribute/tuple-level integrity constraint
    * e.g. NULL value in a NOT NULL column
    * e.g. CHARACTER value in an INTEGER column
  * If you can only "see" all the tuples already in the table and the schema definition, can you say if it will fail the integrity constraint?
    * If yes, then it is a table/relation-level integrity constraint
    * e.g. the new tuple has a value for its primary key which is already present in the table

* If a condition must hold for a specific attribute, use the `CHECK` clause when creating a table

  * Example:

    ```sql
    CREATE TABLE skaters (
    	sid INTEGER PRIMARY KEY NOT NULL,
    	sname VARCHAR(20),
    	rating INTEGER CHECK(rating > 0 AND rating < 11),
    	age INTEGER
    )
    ```

  * Condition is checked only when the associated attribute changes (i.e. in an insert or update, but not delete)

  * If this condition is violated the sytem rejects the modification

  * In SQL the condition can be anything that could follow a `WHERE` clause (including subqueries)

  * If a condition covers several attributes:

    ```sql
    CREATE TABLE skaters (
    	sid INTEGER PRIMARY KEY NOT NULL,
    	sname VARCHAR(20),
    	rating INTEGER,
    	age INTEGER,
        CHECK(rating <= 4 OR age > 5)
    )
    ```

* Naming constraints

  * What if constraints change? Solution: name constraints

  * Example:

    ```sql
    CREATE TABLE skaters (
    	sid INTEGER NOT NULL,
    	sname VARCHAR(20),
    	rating INTEGER CONSTRAINT rat CHECK (rating > 0 AND rating < 11),
    	age INTEGER,
    	CONSTRAINT pk PRIMARY KEY (sid),
    	CONSTRAINT ratage CHECK (rating <=1 OR age > 5)
    )
    ```

  * This allows constraints to be dropped and recreated later on:

    ```sql
    ALTER TABLE skaters DROP CONSTRAINT ratage
    
    ALTER TABLE skaters ADD CONSTRAINT ratage CHECK (rating <= 5 OR age > 5)
    ```

    

