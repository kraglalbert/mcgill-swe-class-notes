# Elicitation

1. [Introduction](#introduction)
2. [Elicitation Techniques I](#elicitation-techniques-i)
3. [Elicitation Techniques II](#elicitation-techniques-ii)

### Introduction

* Requirements elicitation is the process of discovering the requirements for a system by communication with customers, system users and others who have a stake in the system

* Elicitation produces a rough-draft first document
* Challenges of elicitation:
  * Problems of scope
    * System boundaries can be inadequately defined or defined too soon
    * Unnecessary technical details
  * Problems of understanding
    * Stakeholders are unsure of what's needed
    * Stakeholder has trouble communicating their needs
  * Other typical issues
    * Experts are seldom available
    * Finding an adequate level of precision/detail
* Sources of requirements
  * Stakeholders
  * Existing docs
  * Similar systems, previous systems
  * Requirements checklists
  * Current situation + problems
  * Environment in which the software will run
* The **owner/client** is the person paying for the software to be developed
  * They are the ultimate stakeholder with a final say in what the product does
* A **customer** buys the software after it has been developed
* A **domain expert** knows the problem domain + work involved
  * Familiar with typical users and their expectations
  * Knows the product's environment
* A **persona** is useful when real users are not available or are too numerous to interview them all
  * Personas must represent probable users
  * Describes behaviour patterns, objectives, problems, abilities, attitudes, environment, culture, demographic...
  * Should outline the Drivers, Goals, Pain Points, and Typical Activities of that persona
* Elicitation tasks
  * Planning for the elicitation
    * Who, what, when, why, how, risks
  * During the elicitation
    * Confirm the viability of the project
    * Understand the problem from the perspective of each stakeholder
    * Extract the essence of stakeholder requirements
  * After the elicitation
    * Analyze the results
    * Negotitate a set of requirements that all stakeholders can agree on (and establish priorities)
    * Record results in the requirements specification
* Elicitation is incremental
  * Driven by the information needed
  * Elicitation, analysis, specification and verification can all occur at the same time

### Elicitation Techniques I

* Artifact-based elicitation
  * Learn as much as possible by studying documentation, systems, artifacts, etc. *before* asking for stakeholders' time
* Stakeholder-based elicitation
  * Acquire information about the system-to-be that is either problem-specific or stakeholder-specific
* Model-based elicitation
  * Re-express the requirements in a different language, which can raise new questions
* Creativity-based elicitation, data-based elicitation
* Interviews
  * Useful for asking targeted, stakeholder-specific questions
  * Elicit stakeholder-specific needs and opinions
  * Elicit details that only the stakeholder can answer
  * Questions should be open-ended
  * Interview as many stakeholders as possible
* Objectives and interview process
  * Three main objectives:
    * Record information to be used as input to the requirements analysis and modelling
    * Discover information from interviewee
    * Reassure the interviewee
* Interview advantages
  * Can probe in depth
  * Format can be adjusted based on feedback
  * Allows for follow-up questions
  * Can assess nonverbal cues for opinions, feelings, motives, as well as hard-to-determine facts
* Interview disadvantages
  * Large amounts of qualitative data can be hard to analyze
  * Relies on a skillful interviewer
* Brainstorming roles
  * Scribe
    * Writes down all ideas
  * Moderator/leader
    * Cannot be the scribe
    * Traffic cop: enforces the "rules of order"
    * Agent provocateur: traffic cop plus more of a leadership role, comes prepared with wild ideas to help facilitate discussion
* Why analyze an existing system?
  * Users may not like the new system if it is too different from the old one
  * Takes into account real usage patterns and common activities
  * Catch obvious possible improvements
  * Determine which legacy features can/cannot be left out
* Start by reading available documentation
  * User documents (manuals, guides)
  * Development documents
  * Internal memos
* Next, do some user observation
* Another technique: questionnaires
  * Can reach a large number of people
  * Cheap and easy to distribute
* Questionnaires should be tested on a small group first
* Questions should not be biased
* Prototyping can be effective for eliciting new requirement details; also more clarification
  * Prototypes can be *evolutive* or *throw-away*
  * Fidelity is the extent to which the prototype is real and reactive
  * Prototypes can be high or low fidelity 

### Elicitation Techniques II

* Representation of scenarios

  * Text (i.e. use case)
  * UML diagrams (use case diagrams)

* Start by understanding the work the user does (or wants to do)

  * Goal is to understand the existing work, *not* how it is implemented
  * Focus on externally visible behaviour

* Decompose the work into vertical slices to reduce complexity

  * Each slice is a use case

* Use cases

  * Represent end-to-end functionality
  * Triggered by external events
  * Describe a set of related scenarios

* Scenarios work well for interactive systems

  * Problems dominated by functional requirements
  * Help to define system scope
  * Not great for low-interaction systems

* A use case should be as independent as possible from any particular implmentation/UI design

* Scenarios provide a good enough approximation of the "voice" of the user

* A use case model consists of:

  * A set of use cases
  * An optional description or diagram showing how they are related

* A scenario is an instance of a use case

  * One particular set of actions is followed
  * Same as a scenario model

* Each bullet point in a use case is called an interaction step

  * These can reference other use cases (underlined)
    * e.g. user <u>calls elevator</u>

* The system boundary is represented by the box with use cases in it in a use case model

* **Inclusions** (`<<include>>`) express commonality between different use cases

  * Base use case explicitly references the included use case as needed
  * Base use case cannot exist without the included use case
  * Disadvantage: have to look in multiple places to understand the use case

* **Extensions** (`<<extend>>`) make optional interactions explicit or handle exceptional cases

  * By creating separate use case extensions, the base use case remains simple
  * The base use case *can* still be executed without use case extensions
  * Extension arrows point towards the base use case
  * Control is handed off to another use case when an extension point is reached

* Do not describe the user interface in use cases!

* **User stories** are requirements that are written in the language of an end user

  * Typically formatted as follows:

    > As a _role_, I want *thing* so that *benefit*.

* INVEST user story characteristics:
  * Independent
  * Negotiable
  * Valuable
  * Estimable
  * Small
  * Testable
* Details are added in smaller sub-stories
* If using misuse cases, there are 2 other relationships: Threatens and Mitigates
* Misuse cases are dark instead of white
  * Also have hostile actors
* Benefits of misuse cases
  * Elicitation of safety/security requirements
  * Early identification of things that could cause system failure