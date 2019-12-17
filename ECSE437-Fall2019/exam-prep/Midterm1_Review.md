# Midterm 1 Review Session Notes

1. What is the difference between Git and Apache Subversion?
  
  * Git is a distributed VCS, but Subversion is a centralized VCS
4. What is the Git subcommand to upload changes to the upstream repository?
  
  * `git push`
6. List the roles in traditional code reviews that are applicable to the modern code review process.

   * Author, Reviewer

8. For the following Maven code, which target-based lifecycle was invoked, and what is the group ID of the project?

    ```
    [INFO] Scanning for projects... 
    [INFO]  
    [INFO] ----------------------< com.mycompany.app:my-app >---------------------- 
    [INFO] Building my-app 1.0-SNAPSHOT 
    [INFO] --------------------------------[ jar ]--------------------------------- 
    [INFO]  
    [INFO] --- maven-clean-plugin:3.1.0:clean (default-clean) @ my-app --- 
    [INFO] ------------------------------------------------------------------------ 
    [INFO] BUILD SUCCESS 
    [INFO] ------------------------------------------------------------------------ 
    [INFO] Total time: 0.571 s 
    [INFO] Finished at: 2019-09-30T23:37:25-04:00 
    [INFO] ------------------------------------------------------------------------ 
    ```

	* The Clean lifecycle was invoked

	* The group ID is `com.mycompany.app`

10. What are the three services of a Continuous Integration system?

    * Build Job Creation Service
    * Build Job Processing Service
    * Build Job Reporting Service
11. Which command creates `iss53` shown below?
* `git checkout -b iss53`
  * Creates a new branch

13. Name three Git subcommands that only read from the local repository
    * `git status`
    * `git log`
    * `git add`
14. What are the three levels of abstraction at which Maven jobs operate?
  
    *  Lifecycles, Phases, Goals
15. What are the three Maven build lifecycles?
    * Default: produces the project deliverables
    * Clean: undoes build commands to return the project to its inital state
    * Site: generates the project website materials
16. What is a merge commit in Git? If someone wants to avoid merge commits, what should they do?
    * A merge commit combines changes from two different branches into one branch by creating a new commit
    * Rebasing can be done instead to avoid merging
17. Give examples of build tools that subscribe to a low-level design paradigm.
  
* Make, Apache Ant
  
19. What went wrong with the command below?

    ```
    [INFO] Scanning for projects... 
    [INFO] 
    [INFO] -------------------------< ecse437:helloworld >------------------------- 
    [INFO] Building helloworld 1.0-SNAPSHOT 
    [INFO] --------------------------------[ jar ]--------------------------------- 
    [INFO] 
    [INFO] --- maven-resources-plugin:3.0.2:resources (default-resources) @ helloworld --- 
    [INFO] Using 'UTF-8' encoding to copy filtered resources. 
    [INFO] skip non existing resourceDirectory /Users/shanemcintosh/mcgill/teaching/ECSE437/f18/exercise/helloworld/src/main/resources 
    [INFO] 
    [INFO] --- maven-compiler-plugin:3.7.0:compile (default-compile) @ helloworld --- 
    [INFO] Changes detected - recompiling the module! 
    [INFO] Compiling 1 source file to /Users/shanemcintosh/mcgill/teaching/ECSE437/f18/exercise/helloworld/target/classes 
    [INFO] ------------------------------------------------------------- 
    [ERROR] COMPILATION ERROR : 
    [INFO] ------------------------------------------------------------- 
    [ERROR] /Users/shanemcintosh/mcgill/teaching/ECSE437/f18/exercise/helloworld/src/main/java/ecse437/App.java:[11,45] ';' expected 
    [INFO] 1 error 
    [INFO] ------------------------------------------------------------- 
    ```

	* Compilation error due to a missing semicolon

23. What is the rationale behind an abstraction-based build system?
    * Makes logical build operations easier by providing first-class support for them as basic operations
24. List some outcomes that developers expect out of a code review.
    * Make developers less protective of their own code
    * Give other developers insight into parts of the codebase they would otherwise not be familiar with
    * Better sharing of information across the team
    * Help support consistent coding conventions
    * Help improve overall process and quality of code
25. List five paradigms that are used to implement build system technologies.
    * Low-level build systems
    * Abstraction-based build systems
    * Framework-driven build systems
    * Testing frameworks
    * Dependency management tools
26. What are the advantages of CI over routine builds? If CI is in place, should routine builds be eliminated?
    * CI runs more often and builds/tests runs independently for every commit, which makes it much easier to figure out which change caused the code to break
    * Routine builds can still be run, since they are a good time to run the *entire* test suite, which can include tests that take a long time to run/use a large amount of computational resources
27. Name three reasons why it is important for software teams to use a version control system.
    * Versioning
    * Revert to old versions of the code
    * Enable asynchronous developer collaboration

30. * What is the purpose of the staging area in Git? Which subcommand removes files from the staging area?
      * The staging area allows a user to choose which files they would like to commit
      * `git reset` removes files from the staging area

