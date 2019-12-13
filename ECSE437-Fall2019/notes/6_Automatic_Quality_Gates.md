# Automatic Quality Gates

* Software defects are costly
  * One report found that $312 billion in revenue is lost every year due to debugging efforts globally
* Ways to find defects
  * Testing
  * Static and dynamic analysis tools
  * Statistical defect prediction
  * Inspections (in combination with other techniques)

#### Automatic Defect Finding

* Goal: perform an automated discovery of *likely* locations of defects
* Types of analysis:
  * Static analysis
    * Can find code smells, bad patterns, and definite errors
  * Bug prediction
    * Uses machine learning and algorithms
  * Dynamic analysis
    * Taint analysis, performance, memory usage
* Static Analysis
  * Examples: PyLint, JSLint
* Dynamic analysis
  * Example: Valgrind
    * Automatically detects many memory management and threading bugs
    * Profiles C programs in detail
  * Provides coverage, telemetry
* Statistical Defect Prediction
  * It is practically impossible to test for all possible inputs in a large software system
  * Uses historical defect data to predict where *future* bugs will most likely be
  * Gives QA teams an area of the codebase to focus on
* Bug localization
  * A hybrid of dynamic and statistical methods
  * Uses statistical properties to calculate the suspiciousness of a line location

#### Code Smells

* These are often bad design choices
* Common smells:
  * Duplicated code
  * Long method/function
  * God classes
  * Long parameter list (in a function)
  * Primitive obsession
  * Data clumps
  * Shotgun surgery
    * Modifying a system without fully understanding it
  * Feature envy
    * Too many (unnecessary) features

#### Data Flow Analysis

* A framework for proving *facts* about a program
  * e.g. are all variables defined before use? Are any null pointers being dereferenced? Are all open files closed? etc.
* Examines how information propagates through blocks and paths of a program
* Reaching definitions
  * A **definition** of a variable `x` is a statement that may modify the value of `x`
  * A **use** of a variable `x` is a statement that reads from `x`
  * A definition at node *k* reaches node *n* if there is a definition-free path from *k* to *n*

* DU-chain: links each definition to uses it reaches

* UD-chain: links each use to reaching definitions

* Example:

  ```c
  1 foo(b) {
  2 	x = 1;
  3 	y = b+x;
  4 	x = b*b;
  5 	x = y*b;
  6	y = 3/x+z;
  7	z = 3;
  8 }
  
  b: 1 -> 3, 1 -> 4, 1 -> 5
  x: 2 -> 3, 5 -> 6
  y: 3 -> 5
  z: 7 -> 6 // error!    
  ```

  

