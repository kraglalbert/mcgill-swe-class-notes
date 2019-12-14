# Final Review Session Notes

1. List 3 defects that can be identified through data flow analysis.

   * Dead code
   * Using a variable before it's initialized
   * Resource type checking (e.g. opening a file without checking if it is available)

2. Give the cloud types of the technologies below:

   * Heroku: *Platform as a Service*
   * Amazon EC2: *Infrastructure as a Service*
   * Firebase: *(Mobile) Backend as a Service*
   * Linode: *Infrastructure as a Service*

3. What are the server-side hooks available in Git? When should a server-side hook be used?

   * Pre-receive, update, and post-receive
   * Server-side hooks should be used when we want to enforce policies at the server level (e.g. prevent people from pushing to the master branch, enforce a commit message style for all users)

4. What is the key difference between a Type 1 and a Type 2 Hypervisor?

   * Type 1 Hypervisor: VMM sits directly on top of the hardware
   * Type 2 Hypervisor: OS separates VMM and the hardware

5. Give 3 examples of client-side hooks and use cases for them.

   * Examples: pre-commit, post-commit, commit-msg
   * Use cases: enforce a certain style of commit message
   * Cannot use a client-side hook to enforce a policy since they can be overriden by a client

6. Give 5 examples of code smells.

   * Long parameter list
   * God class
   * Duplicate code
   * Shotgun surgery

   * Feature envy

7. MagicalReads is a mobile application for readers of fantasy novels. The app recommends books and allows users to rate books they have read. Which cloud solution is best suited for this app and why?

   * Mobile Backend as a Service
   * Provides authentication and other features necessary to create this type of application

8. What does the Docker daemon do?

   * Interfaces with the host OS (client) to execute Docker commands (e.g. creating containers)

9. List the pros and cons of running a nightly build setup.

   * Pros:
     * No time wasted during the day
     * Can run longer-running regression tests without using computational resources during the day
   * Cons:
     * Difficult to figure out which commit may have caused a bug
     * Developers may not remember the design decisions they made

10. What is configuration drift? How can it be mitigated?

    * Configuration drift occurs when multiple systems have different configurations (due to not tracking dependencies, for example)
    * Mitigated through tools such as Puppet, which allows configuration to be written in code-like syntax

11. Which command creates the red star? How do we avoid it?

    * `git merge` creates it (diagram shows two branches converging into a single commit)
    * Using `git rebase` would avoid this issue

12. Write a Dockerfile that pulls from `mvn:alpine`, copies to a working directory and runs Maven tests.

    ```dockerfile
    FROM mvn:alpine
    WORKDIR test
    COPY . /test
    RUN mvn test
    ```

13. Name 3 Git subcommands which only read from the local repository

    * `add`
    * `status`
    * `log`

14. What are the three levels of abstraction at which Maven build jobs operate

    * Lifecycle, Phase, Goal

15. What are the 3 Maven build lifecycles?

    * Default, Clean, Site

16. What is the purpose of virtualization? What are the two key types?

    * Purpose: emulate a computer system (gives process isolation, flexibility, scalability, makes entire machines "shippable")
    * Types: system and process-level virtualization

17. Which Docker command do we use to transform a Dockerfile into its image?

    * `docker build`

18. What are the 3 tiers in a 3-tier architecture?

    * Presentation layer
    * Logic layer
    * Data layer

19. What went wrong in this command?

    ```
    [INFO] Scanning for projects... 
    [INFO] 
    [INFO] -------------------------< ecse437:helloworld >------------------------- 
    [INFO] Building helloworld 1.0-SNAPSHOT 
    [INFO] --------------------------------[ jar ]--------------------------------- 
    [INFO] 
    [INFO] --- maven-resources-plugin:3.0.2:resources (default-resources) @ helloworld --- 
    [INFO] Using 'UTF-8' encoding to copy filtered resources. 
    [INFO] skip non existing resourceDirectory /Users/shanemcintosh/mcgill/teaching/ECSE437/f18/exercise/helloworld/src/main/resources         [INFO] 
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

    * Missing semicolon, compilation error

20. In Google's CAS, what are the two API calls that are available?

    * `Evaluate()`
    * `GetResults()`

21. N/A

22. Explain how Netflix's Chaos Monkey enables high avalability.

    * Runs throughout the workday and randomly causes virtual machines to turn off
    * Developers are exposed to things failing, and must write code that is able to deal with these failures
    * Teams are always prepared for outages

23. Where does the Docker daemon look for images?

    * It checks if the image exists locally first (i.e. if it has already been built); if not it then checks the Docker registry (Docker Hub)

24. How does Docker differentiate itself from Puppet?

    * Docker is a virtualization took for managing different containers
    * Puppet is a tool for managing infrastructure 

25. What does this Puppet manifest file do?

    ```
    class httpd {
      package { 'cowsay':
    	  ensure   => present,
    	  provider => "gem",
      }
    }
    ```

    * It downloads the `cowsay` package using `gem`

26. How is the canary-in-the-coal-mine approach adopted to software delivery? What are the 3 steps that we need?

    * Partial, time-limited deployment of the change
    * Evaluate how the change performed
    * Either roll the change forward, undo it, or alert people

27. List the high-level components of Google's CAS

    * CAS = Canary Analysis Service

28. If Blue-Green deployment is unsuccessful and a rollback occurs, what potential issues can arise and how can they be addressed?

    * Backwards *incompatible* deployments could fail
      * This can be address by ensuring backwards compatability
    * Put one environment in read-only mode first to avoid having lost transactions

29. Fischer's exact test

    ```
    A startup wants to decide between variant A and variant B of their “Get Started” button on their landing page. 100 people are shown variant A and 50 clicks are recorded. 75 people are shown variant B and 40 clicks are recorded.  
    ```

    * The p-value is 0.7601, which is not significant since it is greater than 0.05.

30. What is the purpose of the staging area in Git? Which subcommand removes files from the staging area?
    * The staging area allows a user to choose which files they would like to commit
    * `git reset` removes files from the staging area
