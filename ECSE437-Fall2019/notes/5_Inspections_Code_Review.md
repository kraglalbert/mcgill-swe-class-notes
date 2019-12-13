# Inspections and Code Review

* Software inspections and reviews use knowledge of the system, its domain and the technologies used to discover problems
* Advantages over dynamic QA:
  * Cascading errors can obfuscate test results
    * Once an error occurs, later errors may be new or they may have been caused by the previous error
  * Incomplete work can still be inspected
    * Tests require an executable version of the system, while inspections do not
    * Useful for getting feedback early on
  * Good inspections are more than just "bug hunts"
    * Inspections can uncover inefficiencies and style issues
    * Inspections are a form of *knowledge sharing* and *collaborative problem solving*
* Inspections *complement* testing, they **do not** replace it!
  * Why can't inspections replace tests?
    * People make mistakes and cannot catch everything
    * We still need tests to ensure that the code does what it's supposed to do
* Inspection approaches
  * Structured inspections
  * Modern reviews

#### Structured Inspections

- Rigid, heavyweight process
- Involves in-person meetings and review checklists
- Roles:
  - Moderator
    - Ensures the artifact is ready for review
    - Ensures inspection procedure is followed
    - Assembles an effective inspection team
    - Keeps inspection meeting on track
  - Recorder/scribe
    - Documents problems that are identified during the inspection meeting
    - Not just procedural; requires technical expertise as well
  - Reviewer
    - Analyzes and detects problems in the artifacts
    - All participants in the inspection play this role
    - Participants from different teams may bring important perspectives
  - Reader
    - Leads the inspection team through the meeting by reading aloud small, logical units and paraphrasing where appropriate 
  - Producer
    - Author of the artifact
    - Responsible for correcting problems that are identified during the inspection

* Pre-review tasks
  * Planning
    * Set up a review team
    * Schedule a time and place
    * Distribute artifacts for the review
  * Group preparation (optional)
    * Meet to provide context, orientation for team members unfamiliar with product
  * Individual preparation
    * Review the artifacts and other relevant material
  * Checklists ensure that review quality is attained
    * Data reference errors, data declaration errors, computation errors, etc.
* Review meeting tasks
  * Reader walks review team through the material
  * Two hour meeting at most
  * Moderator ensures discussion stays on track
  * Recorder documents, producer listens
* Post-review tasks
  * Error correction
    * Bugs identified during the inspection are fixed
  * Improvement
    * Ideas for improving the artifact are evaluated 
  * Follow-up checks
    * After making changes, the moderator may re-evaluate the artifact

#### Modern Reviews

* Lightweight, tool-supported, flexible, asynchronous (people can review when they want to)
* Tips for a productive review process
  * Do:
    * Critique the artifact
    * Keep review chunks short and succinct
    * Plan time for reviews
    * Prioritize reviews of important issues
    * Keep comments lighthearted
  * Don't:
    * Attack the author of the changes
    * Submit fixes for multiple issues at the same time
    * Skip reviews
    * FIFO queue reviews
    * Use sarcasm or exaggeration
* Microsoft code review reading: [link](https://sback.it/publications/icse2013.pdf)
* Desirable features of a code review system
  * **Sharing:** developers must invite others to discuss
  * **Conversations:** discussions organized in threads
  * **Distributed:** globally distributed development teams should be able to adpot it
  * **Workflows:** RTC (Review then Commit) and CTR (Commit then Review)
  * **Roles:** authors, reviewers, integrators, verifiers
  * **Tool Integration:** interfaces nicely with version control and other QA tools
  * **Inline discussion:** allow discussions to take place within the context of a patch/commit
* Roles
  * Author
    * Responsible for correcting problems that are identified during the review
  * Reviewer
    * Analyzes and detects problems and opportunities for improvement in the artifacts
    * An engineer with expertise in the context that the artifact operates within
  * Integrator
    * A core team member who can make final approval or rejection decisions
  * Verifier
    * A team mmber who is only responsible for checking the functional correctness of the commit (i.e. if it does what it is supposed to do)