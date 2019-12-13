# Resiliency

* The problem:
  * Production conditions are difficult to predict
  * Rarely occurring field conditions may lead to catastrophic failures in the field
* Examples of failures that are difficult to test for:
  * Improper fallback settings when a service is unavailable
  * Retry storms from improperly tuned timeouts
  * Outages when a downstream dependency receives too much traffic
  * Cascading failures when a single point of failure crashes
* What we would like to do:
  * Early identification
    * We should test systems for sensitivity to change in deployment environments
    * Passing such tests would increase team confidence in releasing products to chaotic deployment environments
  * Understanding chaos
    * Testing all possible environment conditions suffers from a combinatorial explosion (i.e. it's practically impossible to do)
    * Instead, we aim to mimic the chaos of a real world deployment environment

#### Chaos Engineering

* Since realistic scenarios are hard to imagine, one can think of deployment environments as chaotic
* The degree to which a system can withstand chaotic deployment changes can help us gain confidence on the resiliency of a release
* Checking for resiliency to chaos
  * We can apply controlled experiments!
* Formulate the experiment
  * Define the "steady state" deployment setting
    * Express how steady state execution and output can be checked using metrics
  * Introduce variables to express real-world events
    * e.g. server crashes, hard disk failures
* Null hypothesis: a real-world event has no impact on execution or output metrics
* The experiment:
  * Characterize the steady state by taking measurements in production
  * Simulate a real-world event (apply the treatment)
  * Characterize the post-treatment environment by taking measurements in production
  * Restore the previous production setting (remove the treatment)
* Once this is done, apply a statistical hypothesis test or a heuristic-based analysis to check if the null hypothesis should be rejected
* Chaos engineering at Netflix
  * Chaos monkey random shuts of VM instances (during work hours)
  * Chaos kong simulates entire AWS EC2 regions getting knocked out