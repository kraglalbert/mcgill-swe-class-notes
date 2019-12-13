# Continuous Integration and Routine Builds

* The central role of the build system
  * Build systems have a variety of clients
  * Developers execute pesonal builds on their development machines to:
    * Sync their changes into deliverables so that they can perfom basic tests
    * Ensure that their changes have not introduced build problems (e.g. doesn't compile, tests no longer pass)
  * QA teams hook automated tests into the build system
    * Since normal builds should provide quick feedback, only quick tests are run by default
    * Slower tests can be relegated to special build targets that are executed less often (e.g. once per week)
  * Release engineers are concerned with micro and macro aspects of build systems
    * Micro-build: concerns about the behaviour of a build system within a single run
    * Macro-build: concerns about how to best provision a fleet of build resources
  * Static anlysis and builds
    * Static anlysis tools listen to commands that are executed by the build system
    * Use executed commands to put together a graph of how source code files are connected
    * Scan the graph for common bugs like resource leaks and dead code
  * Code review and builds
    * Teams that use modern code review tools often connect build jobs to the code reviewing dashboard
  * Nightly builds
    * Typically, developers work during the day, committing their changes that fix bugs and add new features
    * At night, while developers are asleep, a build is executed to produce deliverables that include the changes of the prior day
    * QA teams can pick up these deliverables in the morning to test the new features and verify bug fixes
* The problem with nightly builds
  * Nightly builds are too infrequent!
  * As the amount of changes per day has grown, nightly builds have become difficult
  * Consider the case when a nightly build does not complete cleanly
    * If hundreds of developers have committed changes, it's difficult to tell exactly which change caused the problem
  * It is necessary to run builds *more frequently* in order to keep up with fast-paced development

#### Continuous Builds

* Recall the CI feedback loop
  * Commit, Build, Test, Report
* What are the benefits of a continuous integration?
  * Each push gets its own build job and its own report
    * It's very clear if that commit broke the build!
  * CI simplifies problem assignment
    * Build problems correspond to a logical set of changes made by one developer
  * CI simplifies problem analysis
    * CI reports errors *quickly*, while design decisions are still fresh in developers' minds
* Keeping build systems robust
  * As source code changes and evolves, the build system needs to be updated as well
  * Neglected build maintenance makes the build produce incorrect results
    * e.g. the build thinks it has finished when it hasn't, weird bugs produced
* **Flaky tests** are tests that run with non-deterministic behaviour, i.e. different runs of the same test can produce different results
  * False positives
    * The test says code has failed the test, but the code is actually fine
    * These are false alarms that waste valuable developer time and cause developers to lose trust in the test
    * The test becomes useless!
  * False negatives
    * The test passes when it should have failed
    * Allows bugs to slip through into production

#### Anatomy of a CI Service

* A **build-triggering event** starts the CI process
  * Examples: developer runs a build locally, a commit is pushed to a project repository
* A **load balancer** will pick a server (build node/worker) to run the build job on
  * The node first downloads the latest source code in production, and then applies the new changes to it
  * General flow:
    1. Download code
    2. Initiate build
    3. Compile (if needed)
    4. Run tests
    5. Deploy
    6. Report
* The job is then added to a **build queue**
* The job is then eventually taken out of the queue and runs on one (or more) machines
* The result of build is then placed into a **result queue**, where it can then be used for reporting (e.g. results are sent via email, a web dashboard, etc.)