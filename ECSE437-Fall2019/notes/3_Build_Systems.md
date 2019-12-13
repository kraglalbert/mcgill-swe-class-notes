# Build Systems

* Build systems describe how sources are translated into deliverables

#### Families of Build Tools

* Low-level
  * Explicitly define dependencies and rules for each input and output file (e.g. Make)
* Abstraction-based
  * Derive low-level build code from high-level data, e.g. maps of source files to executables (e.g. CMake)
* Framework-driven
  * Follows the principle of *convention over configuration*
  * Default behaviour is assumed unless explicitly overridden (e.g. Maven)
* Complementary tool families
  * Dependency Management
    * Resolve and acquire dependent libraries and tools
    * Dependencies are specified and then installed automatically
    * Allows for different environment specifications (e.g. dev and prod)
  * Testing frameworks
    * Manage the execution of automated tests and generate reports

#### Low-Level Build Tools

* Make uses a Makefile 	

  * Step 1: express dependencies
  * Step 2: write recipes

* Makefile syntax looks like `<target>: <dependencies>`

  * e.g. `program: random.o input.o main.o`
  * Dependencies must be built before the target can be built

* A full command in a Makefile looks like this:

  ```makefile
  program: random.o input.o main.o
  	gcc -o program random.o input.o main.o
  ```

* Makefile variables

  ```makefile
  my_var = "gcc -c random.c"
  
  random.o: random.c
  	$(my_var)
  ```

* The goal of Make is to find and build the first target specified in the Makefile

  * This is done if `make` is run with no arguments, otherwise it looks for the specified target

* Make uses **incremental builds**

  * Only out-of-date targets are rebuilt, i.e. the files that have changed since the last build

* Make recursively processes rules and targets

* Problems with low-level build tools

  * Platform-specific scripts
    * Shell scripts are not as portable as one would like
    * Several boilerplate expressions need to be repeated
  * Common recursion-based design is harmful
    * To handle dependencies across directories, recursive calls to `make` are typically used
    * This fractures the global dependency graph
    * Creates reliability issues with the build, since one execution may not comlpetely rebuild all that is necessary
    * Creates distrust of the incremental build process, so developers do weird tricks to get it to work (e.g. run the build several times, rebuild from clean every time)

* The unfortunate case of Java

  * The fine-grained make dependency model presents challenges for Java systems
  * The Java compiler attempts to resolve and update dependent classes on its own

#### Apache Ant

* A Make replacement for Java systems
* Ant uses a task-based execution model
* Tasks are implemented as Java programs, which makes them portable (wherever the JRE is available)
* Ant does not track dependencies at the file level
* Ant's `javac` task is aware of the potential for multiple outputs and updates dependencies accordingly
* Ant programming concepts
  * Properties
    * Used to store and retrieve frequently-used elements (e.g. values, strings)
    * Similar to variables in imperative programming
  * Tasks
    * Correspond to the invocation of commands
    * Instantiated by issuing a call to an API in the Ant library (custom ones can also be implemented)
  * Targets
    * A logical grouping of tasks used to accomplish a broader goal
    * Similar to grouping statements into methods

#### Abstraction-Based Build Systems

* Abstraction addresses shortcoming of low-level specs
* Makes logical build operations easier by providing first-class support for them as basic operations
* Low-level technologies can be used to realize the abstract specs
* Examples of abstraction-based build tools:
  * GNU Autotools
  * CMake
* CMake
  * A toolchain that expands higher-level abstractions to generate lower-level build specs
  * In addition to generating Makefiles, Visual Studio and XCode build files can also be generated

#### Framework-Driven Build Systems

* Maven is a commonly-used framework-driven build tool
* The Maven build process
  * Maven assumes that a build job follows a **lifecycle**
  * A lifecycle is a sequential series of **phases**
  * A phase performs a series of **goals** that are bound by **plugins**
* The Maven build lifecycles
  * Default: produces the project deliverables
  * Clean: undoes build commands to return the project to its inital state
  * Site: generates the project website materials
* Examples of phases in Maven lifecycles
  * Default: `compile`, `test-compile`, `test`, `package`, `verify`, `install`, `deploy`	
  * Clean: `pre-clean`, `clean`, `post-clean`
  * Site: `pre-site`, `site`, `post-site`, `site-deploy`
* Phase-to-goal bindings
  * The actions performed during a phase depends on the goals that are bound to it, and the plugins that are associated with those goals
  * A phase with no goals or plugins bound to it is silently skipped
* The bindings depend on the type of deliverable
  * The deliverable is what Maven is tasked with producing
  * The goals that are bound to lifecycles phases by default depend on the deliverable that we are trying to produce
* Maven goal examples
  * `compiler:compile`, `surefire:test`, `resources:testResources`
* There are default phase-to-goal bindings that come out-of-the-box with Maven
* Maven uses a `pom.xml` file, where everything is declared
* Maven is typically run with a phase as an argument, e.g. `mvn test`
* Maven knows where to look for Java code because it assumes that it will be in a certain (common) location
  * i.e. `src/main` or `src/test`

#### Dependency Management Tools

* Management of external dependencies is a common build task
* Internal dependencies are becoming easier to track
  * The Java compiler resolves internal dependencies through source code analysis
* Projects are becoming more and more connected
* Examples of dedicated dependency managers
  * RubyGems
  * npm
  * pip (PyPI)
* Maven doubles as an external dependency management tool
  * The **Maven central repository** makes this possible
* Maven Central
  * An online resource where packages are published and made available to all Maven users
* Maven dependency resolution
  * Dependencies are expressed in the `pom.xml` file
  * If an appropriate version of a dependency is not available in a user's local repo, it is downloaded from Maven Central
  * Maven installs dependencies *globally*, which is opposite to tools like npm that install packages *locally* (i.e. per project)
  * If no version is specified, Maven will use a default version
* Maven dependency scope
  * Dependencies can be bound to specific phases, so that their impact is scoped down to only the users who run those phases
  * Example: `junit` is needed (at earliest) by the `test` phase, so its resolution can be deferred until then

#### Organizational Scaling

* Problem 1: build execution is computationally expensive
  * Build tools produce an in-memory dependency graph
    * This requires lots of high-speed RAM
  * Compilation, a commonly invoked build task, is a processor-intensive operation
    * This requires a CPU with a fast clock rate
  * Files need to be stat'd (check their last modification time), read, and written
    * This requires large and fast disks
  * If developers must execute builds on their own machines, then they must be powerful (i.e. expensive) machines
* Problem 2: constraints on parallelism
  * Build workloads are often inherently parallelizable
  * Many build tasks do not have dependencies on other build tasks
    * e.g. individual source files rarely need to be compiled in a specific order
  * Even the most powerful developer machines have constraints on how many tasks can be executed in parallel
    * Limited by number of CPUs, cores, hyper-threads
    * Expensive to add more cores!
* Problem 3: commands are repeated across machines
  * In the vast majority of cases, changes to a codebase only impact a few files
  * Therefore in most build executions, most of the codebase remains the same
  * In that situation, most build commands have identical input and identical output, even though they are executed on different developer machines
    * This generates an unnecessary duplicated drain on organizational resource (i.e. power, compute cycles)

#### Microsoft CloudBuild

* Reading: [link](https://www.microsoft.com/en-us/research/wp-content/uploads/2016/06/q_signed-2.pdf)
* Sections 3.6 and 3.7
* The build scheduler uses the "criticality" of a task to determine its priority order
  * Criticality: length of the longest sub-path to any output of the DAG
  * Expressed differently, criticality is the number of tasks that depend on this task
* Scheduler only considers *actionable* tasks in the DAG
  * i.e. tasks that have not yet run, but all of their dependencies have been met
  * These are added to the queue of executable tasks (sorted by criticality)
* Some execution errors are retry-able
* The builder attempts to evaluate the fastest time to completion for each task
  * Inputs need to be coped over if they are not already available on the machine
* CloudBuild uses critical-path scheduling, but with added attention to multi-resource packing of build tasks and eager queuing of tasks so that fetching inputs can overlap the execution of other tasks

#### Google Bazel

* Open-source version of Google's internal build tool called Blaze
* Uses Starlark, a domain-specific build language inspired by Python
* High-level build concepts (e.g. mapping executables to sources) are expressed in a readable format
* Two types of files are needed:
  * `BUILD`: files that specify what and how to build deliverables
  * `WORKSPACE`: defines properties of the area in which Bazel should search for `BUILD` files