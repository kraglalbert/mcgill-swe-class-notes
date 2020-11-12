# Naive Bayes

1. [Bayes' Rule for Classification](#bayes-rule-for-classification)
2. [Generative Classification](#generative-classification)
3. [Summary](#summary)

### Bayes' Rule for Classification

* Learning objectives

  * How to use Bayes' rule for classification
  * The assumption of Naive Bayes classifier
  * What do the learning and prediction steps involve?
  * Different likelihood functions
  * Bayesian parameter learning in Naive Bayes
  * Practical considerations

* Bayes' rule for classification

  * Given:

    * The prior probability of each class
    * Likelihood of observations for each class

  * Use Bayes' rule for classification

    $p(y=c | x) = \frac{p(c )p(x|c)}{p(x)}$

    * $p(y=c|x)$ is the posterior class probability
    * $p(c )$ is the prior
    * $p(x|c)$ is the likelihood of input features given the class label

* Example: Bayes' rule for classification

  * Input: $x \in \{ -, +\}$, test results (a single binary feature)
  * Label: $y \in \{ yes, no\}$, whether the patient has cancer
  * Prior: 1% of the population has cancer so $p(yes) = 0.01$
  * Likelihood: true positive rate of the test is 90% so $p(+|yes) = 0.9$
  * $p(x) = p(+) = p(yes)p(+|yes) + p(no)p(+|no)$

### Generative Classification

* Given a dataset $\mathcal{D}$, we would like to learn the following distributions:

  * Prior probability of each class: $p(y = c) \forall c \in \{ 1, ...C\}$
  * Likelihood of data for each class: $p(x|y=c)$
  * Prediction: use Bayes' rule to get the posterior class probability

* This is a generative classifier because we are learning the joint data distribution $p(x,y) = p(y)p(x|y)$

  * We can generate new data from this joint distribution
  * In a **discriminative classifier** we directly learn $p(y|x)$

* Naive Bayes model

  * We make an assumption about the likelihood $p(x|y) = \prod_{d=1}^D p(x_d|y)$
  * When is this assumption correct?
    * When features are **conditionally independent** given the label, i.e. when knowing the label, the value of one input feature gives us no information about the other input features
    * Write this as $x_i \perp x_j | y$
  * Conditional independence assumption
    * Since $x_1, x_2$ give no extra information, $p(x_3|y, x_1, x_2) = p(x_3|y)$

* Naive Bayes objective

  * A generative classifier maximizes the joint likelihood (or log-likelihood)
  * $\ell(\pi, \theta) = \sum_n \text{log}p(y^{(n)}; \pi) + \sum_d \sum_n \text{log}p(x_d^{(n)} | y^{(n)}; \theta_d)$
    * Separate max-likelihood problems for prior and each feature $x_d$ given the label

* Prior class probabilities

  * For binary classification, class probability is given by Bernoulli $p(y; \pi) = \pi^y (1-\pi)^{1-y}$
  * Max-likelihood estimate is again given by empirical frequencies
  * So we know how to maximize the prior

* Likelihood terms $p(x_d|y; \theta_d)$

  * Encode our assumption about the generative process
  * Different types of features require different forms of likelihood
    * Bernoulli for binary features
    * Categorical for categorical (multi-class) features
    * Multinomial for "count" features
    * Gaussian is one option for continuous features
  * Each feature $x_d$ may use a different likelihood form
  * We do a separate maximum conditional likelihood estimate for each feature
    * $\text{argmax}_{\theta_d} \sum_{n=1}^N \text{log} p(x_d^{(n)} | y^{(n)}; \theta_d)$

* Bernoulli Naive Bayes

  * For a binary feature the likelihood is Bernoulli
    * $p(x_d | y=0; \theta_d) = \text{Bernoulli}(x_d; \theta_{d,0})$
    * $p(x_d | y=1; \theta_d) = \text{Bernoulli}(x_d; \theta_{d,1})$
    * Short form: $p(x_d | y; \theta_d) = \text{Bernoulli}(x_d; \theta_{d,y})$
  * Max-likelihood estimation is similar to what we saw for the prior
    * $\theta^{MLE}_{d,c} = \frac{N(y=c, x_d = 1)}{N(y=c)}$

* Example: COVID-19 classification

  * Each patient has 7 binary features (symptoms), i.e. $x \in \{0,1 \}^7$
  * We have a dataset of $N=1000$ patients where 200 had COVID-19
  * Learn the prior: $\pi = \frac{N(y=1)}{N} = 0.2$
  * For each symptom $d$:
    * $p(x_d = 1 | y = 1) = \frac{N(y=1, x_d=1)}{N(y=1)}$
    * $p(x_d = 1 | y = 0) = \frac{N(y=0, x_d=1)}{N(y=0)}$
  * Prediction:
    * For a new patient $x$ calculate the unnormalized posterior
    * $\tilde p(y=0 | x) = \text{Bernoulli}(0; \pi) \prod_d \text{Bernoulli}(x_d; \theta_{d,0})$
    * $\tilde p(y=1 | x) = \text{Bernoulli}(1; \pi) \prod_d \text{Bernoulli}(x_d; \theta_{d,1})$
    * Normalize: $p(y=1 | x) = \frac{\tilde p(y=1|x)}{\tilde p(y=0|x) + \tilde p(y=1|x)}$

* What changes in a multi-class setting?

  * Learn the prior: $\pi_c = \frac{N(y=c)}{N}$
  * For each symptom $d$ learn the conditional likelihood
    * Probability of symptom $x_d = 1$ given $y = 0, 1 ..., C$
  * How many parameters in our model?
    * Binary classification, binary features has $1+2D$ parameters
    * Multi-class classification, binary features has $C+CD$ parameters

* Example: document classification (e.g. spam filtering)

  * Each document (email) is one instance; $x^{(n)} \in \{0,1\}^D$
    * $D$ is the number of words in the vocabulary
  * $x_d^{(n)} = 1$ if the word $d$ appears in document $n$
  * We classify the documents based on this bag of words representation
  * MLE for the prior is $\text{Bernoulli}(y; \pi)$, i.e. spam frequency in the dataset
  * MLE for likelihood terms is $\text{Bernoulli}(x; \theta_{d,y})$
    * Frequency of word $d$ in spam/non-spam documents
  * Prediction: calculate the posterior

* Why do we make the Naive Bayes assumption?

  * What if we did not make this assumption? Consider the spam filtering example
    * $D$ can be very large
    * With the Naive Bayes assumption: learn the frequency of each word $d$ in spam/non-spam documents
    * Without the assumption: learn the frequency of each possible subset of words in spam/non-spam documents
  * Problems:
    * Many combinations of words may not appear in even one document
    * We need exponentially more parameters as $D$ increases
    * Even for large datasets, this could lead to overfitting

* Bayesian Naive Bayes

  * Using MLE in Naive Bayes can lead to overfitting
    * Example: a certain word could appear in all documents
  * We can solve this by being Bayesian in parameter learning
  * Instead of maintaining $\pi, \theta_{d,y}$ we maintain distributions $p(\pi), p(\theta_{d,y})$ for $y \in \{0,1\}, d$
    * Start from a separate prior for each parameter
      * $p(\pi) = \text{Beta}(\pi; \alpha^{\pi}, \beta^{\pi})$
      * $p(\theta_{d,y}) = \text{Beta}(\theta; \alpha^{\theta}, \beta^{\theta})$
    * Calculate the likelihood $\prod_n p(y^{(n)}| \pi)$
    * Update with observed frequencies in the dataset

* Log-Sum-Exp trick

  * If $D$ is large we have to calculate the product of many terms, which can lead to numerical problems (underflow)

  * When calculating the posterior for new instances, we work in the log-domain:

    $\text{log} \tilde p(y | x; \pi, \theta) = \text{log}p(y; \pi) + \sum_d \text{log}p(x_d|y; \theta_d)$

  * To get the final probabilities we need to normalize $\tilde p$

  * We can do this normalization in the log domain as well:

    $\text{log} p(y | x; \pi, \theta) = \text{log} \tilde p(y|x; \pi, \theta - \text{log} \sum_{c=1}^C \text{exp}(\text{log} \tilde p(c|x; \pi, \theta))$

  * We could still run into very large or very small numbers within the exponential
  * Observation: $\text{log} \sum_c \text{exp}(a_c) = a_0 + \text{log}\sum_c\text{exp}(a_c - a_0)$
  * To make log-sum-exp numerically stable, bring the numbers $a_c$ close to zero
    * For example, choose $a_0 = \text{max}_ca_c$
  * If all the numbers inside the exponential are too large or too small theu are shifted towards zero

* Multinomial likelihood

  * What if we wanted to use word frequencies in document classification?

  * $x^{(n)}_d$ is the number of times word $d$ appears in document $n$

  * $p(x|y) = \text{Mult}(x; \theta_y)$

  * The max-likelihood estimate is again given by the relative frequency:

    $\theta^{MLE}_{d,c} = \frac{\sum_n x^{(n)}_d \mathbb{I}(y^{(n)}=c)}{\sum_n \sum_{d'} x^{(n)}_{d'} \mathbb{I}(y^{(n)} = c)}$
    * i.e. the counts of word $d$ in all documents labelled $c$ over the total word count in all documents labelled $c$

* Gaussian Naive Bayes

  * For continuous features one option is the Gaussian conditional likelihood

    $p(x_d | y) = \mathcal{N}(x_d; \mu_{d,y}, \sigma^2_{d,y})$

  * Maximum likelihood estimates:
    * Empirical mean and variance of feature $x_d$ across instances with label $y$
    * $\mu_{d,c} = \frac{1}{N(y=c)}\sum_{n=1}^N x^{(n)}_d \mathbb{I}(y^{(n)} = c)$
    * $\sigma^2_{d,c} = \frac{1}{N(y=c)} \sum_{n=1}^N \mathbb{I}(y^{(n)} = c)(x_d^{(n)} - \mu_{d,y})^2$

### Summary

* Generative classification
  * Learn the class prior and likelihood
  * Bayes' rule for conditional class probability
* Naive Bayes
  * Assumes conditional independence between features
  * Class prior: Bernoulli or Categorical
  * Likelihood: Bernoulli, Gaussian, Multinomial...
  * MLE has closed-form, estimated separately for each feature and each label
  * Bayesian Naive Bayes helps with overfitting (with frequent or rare feature values)

