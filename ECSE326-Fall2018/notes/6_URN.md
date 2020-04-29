# URN

1. [Use Case Maps](#use-case-maps)

2. [Goal-Relational Language](#goal-relational-language)

### Use Case Maps

* Use case maps

  * Mode scenario concepts
  * Mainly for operational requirements, functional requirements, and business processes

  * Help to reason about scenario interactions, performance, and architecture

* An AND-join waits for all incoming paths before continuing

* Static stubs have only 1 option

* Explicit approach with failure points

  * Indicates location of failure on path

* An abort start point is activated if guard evaluates to true (same as failures), and aborts all other paths in same scope

  * Abort scope: all concurrent branches that are active on maps at the same or lower level

* Why use case maps?

  * Integrate many scenarios, allow reasoning about potential undesirable interactions
  * Can model dynamic systems where scenarios may change at runtime

### Goal-Relational Language

* Recurring pattern with GRL
  * The system (actor) has several functional goals, with various alternative ways of performing them (tasks)
  * There are several stakeholders involved, with their own concerns (softgoals)
* The achievement of a softgoal is qualifiable but not measureable
* A **scenario** describes one path through the model
  * Only 1 alternative at any choice point is taken
* **Strategies** (GRL) are used for ranking trade-offs and for providing arguments for decisions
* GRL propagation: decompositions
  * AND: minimum of child satisfaction values goes up to the parent
  * OR and XOR: maximum of child values goes up
* Bottom-up analysis
  * Propagates satiesfaction values of low-level tasks (i.e. selected solutions) to those of the high-level stakeholder goals
* Top-down analysis
  * Searches for the optimal result by taking the structure of the goal model and the relationships between nodes in the goal model into account
* A feature model describes the commonalities and variablities of members in a SPL
  * Also defines which products are possible (the product space)
* In optional links, one child can fully contribute (i.e. OR, XOR decompositions)
* Feature models can be viewed as a subset of goal models
* Feature models are created in domain engineering and used in application engineering
* Goal model is an AND/OR graph; feature model is an AND/OR tree
* Goals are not enough—need a way to measure functional and non-functional properties in terms of domain units
* **KPIs** represent real-world values and their impact on stakeholder goals
  * Examples:
    * Average work time (minutes)
    * Average travel time (minutes)
    * Monthly infrastructure cost ($)
    * Average ongoing cost ($)
* Linear interpolation converts a real-world value to a GRL contribution
  * Modelled with target, threshold and worst values
    * e.g. target: +100, threshold: 0, and worst: -100
* KPIs behave similarly to tasks/features in terms of goal contributions
* Conversion function #2: mapping of qualitative real-world values to GRL contribution values

