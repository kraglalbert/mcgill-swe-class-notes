# NFRs and Quality Measures

* **Measures** are needed to explicitly quantify requirements and verify that a particular solution meets them
* A **fit criterion** quantifies the extent to which a quality requirement must be met
  * Can be categorized into Outstanding, Target and Minimum
* Reliability can be measured with:
  * Defect rate
  * Degree of precision for computations
* Examples:
  * The precision of calculations shall be at least $\frac{1}{10^6}$
  * The system defect rate shall be less than 1 failure per 1000 hours of operation
* Availability measures: percentage of time that system is up and running correctly
  * Mean time between failures (MTBF)
  * Mean time to repair (MTTR)
  * Availability = $\frac{MTBF}{MTBF+MTTR}$
* Security measures
  * Success rate in authentication
  * Resistance to known attacks
  * Probability of finding a key, etc.
* Usability measures
  * Learnability
  * Efficiency
  * Memorability
  * Error avoidance/error handling
  * User satisfaction
  * Examples:
    * "The system shall enable novice users to perform tasks X and Y in less than 15 minutes"
* Maintainability measurements
  * Measures ability to make changes quickly and cost-effectively
  * Mean time to fix a defect, mean time to add new functionality
  * Quality/quantity of documentation
* Testability measures
  * Measures ability to detect, isolate and fix defects
    * Time to run tests
    * Time to setup testing environment
    * Test coverage
* Portability measures
  * Can the system run in different computing environments?
  * Number of targeted platforms
  * Proportion of platform-specific components
* **Integrability** measures the ability to make separated components work together
* **Reusability** measures the extent to which existing components can be reused in new applications
  * Percentage of reused requirements, design elements, code, tests, etc.
  * Degree of use of frameworks
* **Robustness** measures ability to cope with the unexpected
* There are also domain-specific measures
  * Performance
    * Website: # requests processed per second
    * Video game: # of 3D images per second
  * Accessibility
* Recall: NFRs describe a *quality* of the system rather than specific functionality
* Summary of some quality requirements:
  * Performance
    * Execution speed, response time, throughput
  * Reliability
    * Fault-tolerant, mean-time to failure
  * Robustness
    * Tolerates invalid input, fail-safe/secure, degrades gracefully under stress
  * Adaptability
    * Ease of adding new functionality, reusable in other environments, self-optimizing, self-healing
  * Security
    * Controlled access to system/data, protection against theft
  * Usability
    * Ease of learning/use, user productivity
  * Scalability
  * Efficiency (capacity)
  * Accuracy/precision