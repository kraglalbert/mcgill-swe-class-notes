# Advanced SQL

1. [Aggregation](#aggregation)
2. [Grouping](#grouping)
3. [Views](#views)
4. [NULL Values](#null-values)
5. [Joins](#joins)
6. [Database Modifications](#database-modifications)

### Aggregation

* Significant extension of relation algebra

  * Available operations: `COUNT`, `SUM`, `AVG`, `MAX`, and `MIN`
  * All are applied to a single column, with the exception of `COUNT(*)`

* Counting

  * Output is a relation with only one tuple

  * Example: "*Count the number of tuples in the skaters table*"

    ```sql
    SELECT COUNT(*)
    FROM skaters
    ```

  * Example: "*Count the number of different ratings*"

    ```sql
    SELECT COUNT(DISTINCT rating)
    FROM skaters
    ```

* Average

  * Example: "*What is the average age of skaters with rating 7?*"

    ```sql
    SELECT AVG(age)
    FROM skaters
    WHERE rating = 7
    ```

  * Example: "*What is the average age of skaters with rating 7, and how many are there?*"

    ```sql
    SELECT AVG(age), COUNT(*)
    FROM skaters
    WHERE rating = 7
    ```

* Max/min

  * Example: "*Give the names of the skaters with the highest ratings*"

    ```sql
    SELECT sname
    FROM skaters
    WHERE rating = (SELECT MAX(rating) FROM skaters)
    ```

  * Example: "*Give the name of the skater that is the first in the alphabet*"

    ```sql
    SELECT MIN(sname)
    FROM skaters
    ```

* Aggregate operations **cannot be nested**

  * Incorrect example:

    ```sql
    SELECT S.rating
    FROM skaters S
    WHERE S.age = (SELECT MIN(AVG(S2.age)) FROM skaters S2)
    ```

### Grouping

* Aggregate operators cna be applied to all qualifying tuples, but sometimes we want to apply them to each of several *groups* of tuples

* Example: group skaters by rating

  ```sql
  SELECT AVG(age), MIN(age)
  FROM skaters
  GROUP BY rating
  ```

* One output tuple is generated per group

* The `SELECT` clause can include both aggregate fields and attributes, e.g.:

  ```sql
  SELECT rating, MIN(age)
  FROM skaters
  GROUP BY rating
  ORDER BY rating
  ```

* `SELECT` lists with aggregation
  
* If any aggregation is used, then each element in the attribute list of the `SELECT` clause **must either be aggregated or appear in a** `GROUP BY` **clause**
  
* The `HAVING` clause

  * A selection on groups, just as the `WHERE` clause is a selection on tuples

  * Example: "*For each rating, find the minimum age of the skaters with this rating. Only consider rating levels with at least 2 skaters*"

    ```sql
    SELECT rating, MIN(age)
    FROM skaters
    GROUP BY rating
    HAVING COUNT(*) >= 2
    ```

  * Example: "*For each rating > 5, find the average age of the skaters with this rating. Only consider rating levels where there are at least 2 skaters*"

    ```sql
    SELECT rating, AVG(age)
    FROM skaters
    WHERE rating > 5
    GROUP BY rating
    HAVING COUNT(*) >= 2
    ```

### Views

* A view is just an unmaterialized relation; we store a definition rather than a set of tuples

  * Example:

    ```sql
    CREATE VIEW ActiveSkaters (sid, sname)
    	AS SELECT DISTINCT S.sid, S.sname
    	FROM skaters S, participates P
    	WHERE S.sid = P.sid
    ```

* Views can be used to present necessary information (or a summary) while hiding details in the underlying relations
* Equivalent to renaming in relational algebra
* Views can be dropped using the `DROP VIEW` command
* Views can be treated as if they were materialized relations

### NULL Values

* A NULL value represents something unknown/missing/inapplicable

* When a NULL value is compared to any other value (including NULL) using a comparison operator, the result is "unknown"

* Can use `IS NULL` to check if a value is NULL

* Arithmetic operations

  * When at least one operand has a NULL value, then the result is NULLs
  * Cannot use NULL as an operand (e.g. 7 < NULL)

* Logic tables

  * NOT A

    | A       | NOT A   |
    | ------- | ------- |
    | true    | false   |
    | false   | true    |
    | unknown | unknown |

  * A AND B

    | A \ B       | true    | false | unknown |
    | ----------- | ------- | ----- | ------- |
    | **true**    | true    | false | unknown |
    | **false**   | false   | false | false   |
    | **unknown** | unknown | false | unknown |

  * A OR B

    | A \ B       | true | false   | unknown |
    | ----------- | ---- | ------- | ------- |
    | **true**    | true | true    | true    |
    | **false**   | true | false   | unknown |
    | **unknown** | true | unknown | unknown |

* Query evaluation with NULL values
  * The qualification in the `WHERE` clause eliminates tuples where the qualification does not evaluate to "true" (i.e. tuples that evaluate to "false" or "unknown" are eliminated)
  * SQL defines that rows are duplicates if corresponding columns are either equal or both contain NULL
  * `COUNT` handles NULLs like other values, i.e. they are counted
  * All other aggregate operators simply discard NULL values

### Joins

* Inner join (default)

  * Dangling tuples: no match in the other relation means that tuple does not get outputted

  * Example:

    ```sql
    SELECT sname
    FROM skaters S JOIN participates P
    ON S.sid = P.sid
    ```

* Outer join

  * Dangling tuples: no match in the other relation means that a dummy tuple is inserted

  * Example:

    ```sql
    SELECT sname
    FROM skaters S LEFT OUTER JOIN participates P
    WHERE S.sid = P.sid
    ```

  * In this case, every tuple in `S` will get outputted
    
    * If there is no matching tuple from `P`, the columns from `P` in that tuple of the output will all have NULL values
  * Types of outer joins:
    * `A LEFT OUTER JOIN B`
      * Pad dangling tuples from A
    * `A RIGHT OUTER JOIN B`
      * Pad dangling tuples from B
    * `A FULL OUTER JOIN B`
      * Pad dangling tuples from A and B

### Database Modifications

* Inserting tuples

  * Can specify only values or values as well as attributes

  * Examples:

    ```sql
    INSERT INTO skaters VALUES (68, 'Jacky', 10, 10)
    
    INSERT INTO skaters (sid, name) VALUES (68, 'Jacky')
    ```

* Deleting tuples

  * Examples:

    ```sql
    DELETE FROM competitions WHERE cid = 103
    
    DELETE FROM competitions
    ```

  * The second statement deletes all tuples in the relation, but not the relation itself

* Updating existing tuples

  * Examples:

    ```sql
    UPDATE skaters
    SET ranking = 10, age = age + 1
    WHERE name = 'debby' OR name = 'lily'
    ```

* SQL2 semantics: all conditions in a modification statement must be evaluated by the system *before* any modifications occur