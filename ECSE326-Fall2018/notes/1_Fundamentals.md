# Fundamentals

1. [Introduction](#introduction)
2. [Requirements Levels](#requirements-levels)
3. [Requirements Engineering Activities](#requirements-engineering-activities)

### Introduction

* A **requirement** is:
  * Capturing the purpose of a system
  * A statement all stakeholders agree must be made true in order for the client's needs to be adequately met
    * Short and concise
    * Says something about the system
  * A statement that expresses a need and its associated constraints and conditions
* **Requirements engineering** is:
  * the activity of development, elicitation, specification, analysis and management of the stakeholder requirements, which are to be met by a new or evolving system
* Problems with the requirements process
  * Lack of expertise 
  * Initial ideas are often incomplete and unrealistic
* How to deal with changing requirements
  * Requirements baseline: good enough to proceed with an acceptable level of risk
  * Business requirements: the primary benefits the system will provide for sponsors, buyers and users
  * Project scope (make it well-defined!)

### Requirements Levels

* An **application-domain** requirement (or business rule) is a requirement derived from business practices within a given industrial sector, or in a given company, or defined by government regulations or standards
* A **user requirement** is a desired goal or function that a user and other stakeholders expect the system to achieve
* A **system requirement** is a requirement for the system as a whole
  * Software requirements are derived from system requirements
* A business requirement is typically something that a company wants to achieve
  * Can be financial or non-financial
  * Same as a business objective
* Application-domain requirements
  * Derived from application domains
  * Describe system characteristics and features that reflect the domain
  * Examples:
    * Library: "the system interface to the database must comply with standard Z39.50"
    * Health: "the system shall comply with Ontario's Personal Health Information Protection Act"
  * Can be difficult to understand since there is domain-specific knowledge
* A **goal** is an objective or concern that guides the requirements engineering process
  * Not yet a requirement—a requirement must be verifiable
  * Conveys the intention of one or more stakeholders
* A **functional requirement** describes what the system should do
* A **non-functional requirement (NFR)** describes a quality rather than a function of the system
* Functional requirements
  * Inputs/outputs the system should accept
  * What data should be stored
  * Which computation should be performed
  * Should describe a system service in detail
* Examples of functional requirements
  * "The user shall be able to search a subset of the initial set of databases"
  * "The system shall provide access to a PDF viewer for the user to read documents in the document store"
* NFRs
  * Sometimes called quality requirements
  * Three main categories:
    * Performance requirements
    * Design constraints
    * Commercial constraints
  * Used to judge the operation of a system, rather than specific behaviours

### Requirements Engineering Activities

* Main activities:
  * Requirements elicitation 
  * Requirements validation
  * Requirements verification
  * Requirements management
  * Software requirements specification

* Requirements inception
  * The start of the process
    * Identify a business need/market opportunity/great idea
  * Involves building a business case, preliminary feasibility assessment and preliminary definition of project scope
* Requirements elicitation
  * The gathering of information
    * Problem domain
    * Problems requiring a solution
    * Evoke and provoke
  * Questions that need to be answered:
    * What is the system?
    * What are the goals of the system?
    * How is the work done now? What are the problems?
    * How will the system solve these problems?
* Requirements analysis
  * The process of analyzing the needs of stakeholders to come up with solutions
  * Objectives:
    * Detect and resolve conflicts between requirements 
    * Discover boundaries of the system and how it must interact with the environment
  * Often linked to modelling
* Requirements specification
  * Specification document
    * Clearly describes all essential requirements
* Requirements verification and validation
  * Ensure delivery of what the client wants
  * Need to be performed at every stage during the process
  * Validation: check that the right product is being built
  * Verification: check that the product is being built right
* Traceability is very important for requirements management
* Requirements documents for a large system are normally arranged in a hierarchy