# SQL Application

1. [Pure SQL](#pure-sql)
2. [SQL in Application Code](#sql-in-application-code)
3. [Database-Specific Programming Languages](#database-specific-programming-languages)

4. [Application Architecture](#application-architecture)
5. [Connection Pooling](#connection-pooling)

### Pure SQL

* Example: interactive user interface for DB2/PostgreSQL
* Execution:
  * We can type any SQL statement
  * Statement is sent to DBMS, executed within DBMS, then response is sent back
  * User can be on the same machine or different machine than the DBMS
* Limitation: SQL lacks features of traditional application programming languages

### SQL in Application Code

* SQL commands can be called from within a host language (e.g. Java or Python)
* Two main integration approaches
  * Embed SQL in host language
  * Create special API to call SQL commands (e.g. JDBC)
* Program-database interaction
  * Application program executes at the client side
  * Each SQL statement is sent to the server, executed there, and the result is returned to the client
* JDBC
  * Java Database Connectivity (JDBC)
  * Special, standardized interface of objects and method calls
  * Attempts to be DBMS-neutral
    * A "driver" traps the calls and translates them into DBMS-specific calls
    * Database can be across a network
  * Four components
    * At client:
      * Application (submits SQL statements)
      * Driver manager (handles drivers for different DBMSs)
      * Driver (connects to a data source, transmits requests, and returns/translates results and error codes)
    * At server
      * Data source (processes SQL statements)

* Execution overview
  * Load Driver
    * Since only known at runtime which DBMS the application is going to access, drivers are loaded dynamically
  * Connect to data source
    * Connection object represents the connection between a Java client and DBMS
    * Each connection identifies a logical session
  * Execute SQL statements

* Connecting to a database

  ```java
  import java.sql.*;
  
  class ConnectionExample {
  	public static void main(String[] args) throws SQLException {
  		// load the DB2 JDBC driver
  		// alternative 1
  		// Class.forName("com.ibm.db2.jcc.DB2Driver");
  		// alternative 2
  		DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver());
  		
  		// connect to database
  		Connection conn = DriverManager.getConnection(
  			"jdbc:db2://comp421.cs.mcgill.ca:50000/cs421"
  			"username",
  			"password"
  		);
  		
  		// close the connection
  		conn.close();
  	}
  }
  ```

* Executing a statement

  * Create a statement object to submit SQL statements to the driver

    * `Statement stmt = conn.createStatement();`

  * Close it when no longer needed

    * `stmt.close();`

  * Update/insert/create statement:

    ```java
    stmt.executeUpdate("CREATE TABLE skaters " + 
    	"(sid INT, name VARCHAR(40), rating INT, age INT)");
    
    String sqlString = "INSERT INTO skaters " +
    	"VALUES (58, 'Lily', 10, 12)";
    	
    stmt.executeUpdate(sqlString);
    ```

  * DDL (data definition) always returns 0
  * Insert/update/delete returns the number of affected tuples

* Queries with several results tuples

  * There is a mismatch between standard SQL queries and programming languages

    * Result of a query is usually a set of tuples with no bound a priori on the number of tuples
    * No support for such a data type in conventional programming languages

  * Solution: get result tuples step by step

  * Cursor concept:

    * Think of a **cursor** as a pointer to successive tuples in the result relation
    * At a given moment the cursor can be a) before the first tuple b) on a tuple c) after the last tuple

  * Example:

    ```java
    ResultSet rs = stmt.executeQuery("SELECT * FROM skaters");
    while (rs.next()) {
    	int sid = rs.getInt("sid");
    	String sname = rs.getString("name");
    	int age = rs.getInt(3);
    	int rating = rs.getInt(4);
    }
    rs.close();
    ```

    * Cursor initially set just before first row
    * `rs.next()` makes cursor point to the next row
    * Methods to find out where you are in the ResultSet:
      * `getRow`, `isFirst`, `isBeforeFirst`, `isLast`, `isAfterLast`

* JDBC statements
  * Statement: includes methods for executing text querries
  * ResultSet: 
    * Contains the results of the execution of a query
    * We can receive all result rows iteratively
    * For each row we can receive each column explicitly

* Dynamic vs. Prepared statements

  * Dynamic execution at DBMS

    * Parses statement (1)
    * Builds execution plan (2)
    * Optimizes execution plan (3)
    * Executes and returns (4-5)
    * If a statement is called many times (e.g. in a loop) then steps 1-3 are executed over and over again

  * PreparedStatement

    * Represents precompiled and stored queries
    * Can contain parameters, i.e. values that are determined at runtime

  * PreparedStatement example:

    ```java
    // statement can have input parameters; indicated with ?
    PreparedStatement prepareCid = 
    	conn.prepareStatement("SELECT cid FROM participates WHERE sid = ?");
    
    while (true) {
        // get cid input from user into variable x
        ...
        // provide values for input parameters
        prepareCid.setInt(1, x);
        ResultSet rs = prepareCid.executeQuery();
        while (rs.next()) {
            // get information from ResultSet
            ...
        }
    }
    ```

* Error handling
  * Two levels of error conditions: `SQLException` and `SQLWarning`
  * Can catch both in try-catch blocks

### Database-Specific Programming Languages

* Certain database systems allow the use of standard programming languages (with extensions)
* Some specialized programming languages are specifically designed for DB access
  * e.g. PostgreSQL: PL/pgSQL—SQL procedural language
  * Has constructs of a standard procedural programming language
    * i.e. variables, assignments, flow control constructs
  * Can have input and output parameters

* Execution
  * Stored procedure stored as executable at DBMS
  * Client makes one call to call stored procedure
  * Stored procedure executed within DBMS
    * Less context switches
    * Less calls from client to server, less network traffic

### Application Architecture

* Applications can be built using one of two architectures
  * Two tier model
    * Application program running at user site directly uses JDBC/embedded SQL to communicate with the database
    * Flexible, no restrictions
    * Security problems (passwords)
    * Code shipping problematic in case of upgrades
    * Not appropriate across organizations
  * Three tier model
    * User program only provides presentation logic (browser)
    * Business semantic (JDBC programs) in application server: middle tier
    * Application server communicates with database: backend tier
* Three tier model
  * Web server (top level)
    * Receives and unmarshals requests
    * Sends responses in message format back to browser
    * Generates the pages from result set
  * Application server
    * Implements the business logic (application programs)
* Example: Minerva
  * Client program (browser)
    * HTML pages
    * Might use JavaScript and cookies
  * Web server
    * Gets requests from browser
    * Analyzes them and calls application server methods
    * Gets responses from application server and generates dynamic webpages
    * Users Servlets/JSP
  * Application servers
    * Implements methods
    * Retrieve data from database
    * Modify database
    * Uses servlets/beans/general programs
  * Database systems
    * Students, courses, grant information, etc.
* Application design choices
  * Should we check if date of birth is valid at the webpage or at the database?
  * If we have to increment the rating of all skaters, where can it efficiently be executed?
    * Cursor at the application code vs. stored procedure vs. a single update query in the DB)
  * Rules of thumb
    * If the objective is to trap user errors, it is more efficient to do on the client side
    * Complex iterative processing over large data that requires procedural logic will benefit from storeed procedures, as data does not "leave" the datavase server and therefore does not incur network overhead
    * For updates on large amounts of data where an update to each tuple is independent of other tuples, it is best to do a regular SQL update—modern DBMS systems can process tuples in multiple parallel batches, thus providing much better performance

### Connection Pooling

* In practice, client side database connections are not always active
  * e.g. when browsing Amazon to make purchases
* Establishing a database connection takes time and costs resources
* Application server can establish a set number of database connections in advance
* When a client request arrives that needs database processing, an idle DB connection is selected from the pool to perform it
* All users share the same database user ID
* Database authentication is stored in application server, agnostic to end users

