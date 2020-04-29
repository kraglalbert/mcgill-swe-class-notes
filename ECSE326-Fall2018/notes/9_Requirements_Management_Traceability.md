# Requirements Management and Traceability

* Requirements change is inevitable
* Requirements volatility
  * The number of requirements changes in a specified time interval
  * Stable requirements are the core of the system and its application domain
  * Volatile requirements are specific to the instantiation of the system
    * In a particular env for a particular customer at a particular time
* Requirements management includes all activities intended to maintain the integrity and accuracy of expected requirements
  * Manage versions
  * Manage changes to baseline + agreed requirements
  * Keep project plans synchronized with requirements
* There is no management without:
  * Traceability
  * Baselines enabling comparisons
* Expectation of requirements management
  * Identification of individual requirements 
  * Traceability from high-level requirements to implementation
  * Impact assessments of proposed changes
* Requirements should have attributes, e.g.:
  * Creation date, last update
  * Author(s), stakeholder(s)
  * Version number
  * And much more
* Requirements should have a confidence/uncertainty attribute
  * Low-confidence requirements may be delayed until there is more certainty
* Requirements should have a status (e.g. proposed, approved, rejected, implemented, verified, deleted, etc.)
* Version control is essential to requirements engineering
  * Last version of a requirement must be available to all team members
  * Requirements documents should have a revision history
* Project challenge #1: accessing and integrating data
  * Consider using a tool or service which provides data connectivity between software artifacts
* Challenge #2: knowing what you want
* Traceability links can be modelled with a UML class diagram
* Traceability refers to the ability to describe and follow the life of a requirement, in both forwards and backwards direction
* Traceability is particularly important in safety and mission-critical systems
* Manual link creation is very demanding
* Baseline
  * Non-modifiable version of a document that describes a moment in time
  * Enables document comparison and management
* A baseline for requirements represents the set of functional and non-functional requirements that the dev team has committed to implementing in a specific release
* Changes typically must be requested and a form must be filled out
* The choice is usually to use documents or a database
* Types of analysis
  * Impact analysis: look at out-links
    * Which objects in othe rmodules are affected when this module is changed?
  * Traceability analysis: in-links
    * Changes in these objects will affect this module
* A suspect link indicates that an element may have been affected by a change
  * Proactively know when changes affect your requirements