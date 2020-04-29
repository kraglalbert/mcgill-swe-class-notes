# Requirements Negotiation and Prioritization

* There may be possible requirements conflicts between stakeholders

  * Conflicts on subjects, interests, values...

* Potential resolution strategies

  * Agreement
  * Compromise
  * Voting
  * Mediation
  * Goal analysis
  * Arbitration

* Conflict resolution often involves negotiation

* First, detect when requirements are inconsistent

  * Then find a solution and document it
    * Stakeholders, alternatives considered, and final decision

* Some difficulties

  * There are too many requirements from many different sources
  * All requirements can be deemed essential
  * Resources are limited
  * Establishing priorities is important, but how can this be done?
  * Different stakeholders have different goals and priorities
    * Priorities can also change
    * Some stakeholders' decisions may carry more weight

* Requirements prioritization is referred to as **triage**

* **Minimum viable product (MVP):** must decide which requirements really matter or on those that need to be implemented in the current release

* Prioritization is always needed

* 80-20 rule

  * 20% of functionalities give 80% of the revenue
  * The remaining 80% have a lower ROI while also adding delays, development costs, etc.

* Most companies prioritize requirements by their value and cost

  * Can prioritize according to cost/value ratio 

* Difficulties

  * Hard to calculate absolute value/cost
  * Relative values are easier to obtain
  * Interdependent requirements are difficult to treat individually

* Triage technique #1: priority ranking

  * Each requirement is assigned a unique rank
  * Not possible to see the relative difference between ranked requirements

* Triage technique #2: 100 dollar test

  * In cumulative voting, or the 100 dollar test, stakeholders are given 100 prioritization points to distribute amongst the requirements

* Triage technique #3: prioritization scales

  * Determine criteria, scale dimensions
  * Frequently used: urgency and importance

* Triage technique #4: Kano surveys

  * Group requirements based on customer perception
  * Basic: requirements the customer takes for granted
  * Performance: requirements the customer specifically asked for
  * Excitement: things the customer may not expect
  * Indifferent
  * Reverse: things the customer hates

* Questions for Kano survey

  * Ask a customer how they would feel if the requirement *was* included in the product
  * Also ask how they would feel if it was not included
  * Functional vs. disfunctional questions

* Triage technique #5: Wieger's Prioritization

  * Relies on estimation of relative priorities of requirements
  * Relative benefit (if requirement is included)
  * Relative penalty to stakeholder (if requirement is not included)
  * Relative cost
  * Relative risk
  * Still limited by the ability to properly estimate

* AHP comparison in pairs

  * Compare 2 requirements; which is more important?
  * Rating of 1: requirements are of equal value; rating of 9: one is significantly preferred over the other
    * Range is integers from 1-9
  * If a pair (A, B) has relative value *n*, then (B, A) has the reciprocal value $\frac{1}{n}$
  * Represented in matrix form

* What if there are too many pairs?

  * Dependencies can be used to group requirements, use cases, etc.

* Adjustments may be needed based on stakeholder importance

* Benefits of requirement prioritization

  * Improves customer satisfaction: most important requirements are implemented first

  * Encourages stakeholders to consider *all* requirements