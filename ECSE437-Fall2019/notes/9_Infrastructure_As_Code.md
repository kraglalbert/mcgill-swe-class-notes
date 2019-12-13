# Infrastructure as Code

* All layers require configuration
  * Operating systems
  * Middleware
  * Application servers
  * Applications
* Why is infrastructure configuration a problem for release and ops teams?
  * Automation is hindered!
  * If configuration must be done by hand, then life becomes difficult
  * Release engineers and operators can spend their lives putting out fires with configuration details
  * This wastes precious company resources (i.e. person-hours)
* A solution: Infrastructure as Code (IaC)
  * The idea: write your configurations as code!
  * Manage and provision machines using a code-like syntax rather than interactive configuration tools
  * This methodology has all of the advantages of regular code:
    * Can be stored and versioned using version control
    * Can be automatically executed to perform (previously manual) tasks