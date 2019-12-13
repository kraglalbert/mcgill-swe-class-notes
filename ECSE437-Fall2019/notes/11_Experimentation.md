# Experimentation

* What is an experiment?
  * In a typical experimental design, *subjects* are split into two types of groups:
    * **Control** groups do not receive any treatment and are indlucded to measure baseline behaviour
    * **Treatment** groups receive an isolated set of changes for which a hypothesis has been formulated
* Assigning subjects to groups
  * Randomized experiments:
    * Subjects are randomly assigned to the control group or one of (potentially several) treatment groups
    * Each subject should have the same likelihood of appearing in the control or treatment groups
    * Randomization yields a strong foundation for making statistical inferences

#### A/B Testing

* A/B testing is the application of randomized experiments to software testing
  * Subjects are (often) end-users
  * Treatments are software changes
  * A software change can, at its core, be formulated as a hypothesis
* What makes a good hypothesis?
  * Something a stakeholder would care about
  * Is *falsifiable* through experimentation, i.e. possible to concretely prove it false
* Why care about hypotheses?
  * Without hypotheses, experiments can only demonstrate the presence (or lack thereof) of correlations
  * Correlations can be spurious (i.e. non-causal)
  * We often care about **causation**

* Hypothesis formulation

  * In the context of software, hypotheses can be formulated about changing/improving some "bottom line"-related measurements
    * For example, click through rate or performance measurements
  * Example: "Changing the button colour from blue to green will increase the click through rate"

* Group assignment

  * Randomly split a sample of subjects into two groups of equal size (group A and group B)
  * Each group is shown a variant of the system, where A is usually shown the control and B the treatment version
  * Each user has an equal probability of being assigned to group A or B

* Statistical hypothesis testing

  * used to check if two samples are theoretically derived from the same distribution
  * The output of a statistical test can usually be represented using a p-value
    * This is the likelihood of the two samples coming from the same population
    * Low p-values often indicate that an experiment was successful
    * Often compared to an $\alpha$ value of 0.05
    * If $p < \alpha$, then we can *reject the null hypothesis*, i.e. there is reason to believe that the hypoithesis is true

* How to calculate the p-value:

  |                   | Desired Event (e.g. Sessions with clicks) | Non-Desired Event (e.g. Sessions without clicks) | Row Total   |
  | ----------------- | ----------------------------------------- | ------------------------------------------------ | ----------- |
  | **Control (A)**   | $a$                                       | $b$                                              | $a+b$       |
  | **Treatment (B)** | $c$                                       | $d$                                              | $c+d$       |
  | **Column Total**  | $a+c$                                     | $b+d$                                            | $a+b+c+d=N$ |

  $p = \frac{\binom{a+b}{a}\binom{c+d}{c}}{\binom{N}{a+c}}$

#### Canary Releases

* The name comes from the canary-in-the-coal-mine approach
  * Coal mines oftain contain toxic gases, but miners still need to extract the coal
  * Physiologist J. S. Haldane proposed the idea of bringing a caged bird into the mine
  * Should the mine contain poisonous fumes, the bird will die, giving the miners some time to escape
* Canarying in the context of software
  * The problem
    * Each software releases introduces some risk
    * How can we minimize the risk of deploying broken releases to a global userbase?
  * The solution
    * Canary releases
    * If the canary dies, flee the scene!
* Canary release definition
  * Partial, time-limited deployment of a change in a service
  * Followed by an evaluation of the safety of the changed service
  * Production may then:
    * Roll forward (to a bigger population)
    * Roll backwards (undo the change)
    * Alert certain parties (e.g. via email, Slack)
* Effecive canarying involves decisions
  * How to deploy partial service changes?
  * Which safety metrics should be collected?
  * How should said metrics be compared?
  * What constitutes a "dead canary"?
* Google Canary reading: [link]([http://delivery.acm.org/10.1145/3200000/3190566/p54-davidovic.pdf?ip=147.253.128.205&id=3190566&acc=ACTIVE%20SERVICE&key=A79D83B43E50B5B8%2EA57DBD06F752F5AF%2E4D4702B0C3E38B35%2E4D4702B0C3E38B35&__acm__=1576278883_bc562dd4739b69aa377710d099d9dff0](http://delivery.acm.org/10.1145/3200000/3190566/p54-davidovic.pdf?ip=147.253.128.205&id=3190566&acc=ACTIVE SERVICE&key=A79D83B43E50B5B8.A57DBD06F752F5AF.4D4702B0C3E38B35.4D4702B0C3E38B35&__acm__=1576278883_bc562dd4739b69aa377710d099d9dff0))