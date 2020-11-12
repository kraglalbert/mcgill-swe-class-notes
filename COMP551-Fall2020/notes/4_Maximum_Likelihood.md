# Maximum Likelihood and Bayesian Reasoning

1. [Maximum Likelihood Estimate](#maximum-likelihood-estimate)
2. [Bayesian Approach](#bayesian-approach)
3. [Posterior Predictive](#posterior-predictive)
4. [MAP Estimate](#map-estimate)
5. [Categorical Distribution](#categorical-distribution)
6. [Summary](#summary)

### Maximum Likelihood Estimate

* Learning objectives

  * Understand what it means to make a probabilistic model of the data
  * Using maximum likelihood principle
  * Using Bayesian inference
  * Prior, posterior, posterior predictive
  * MAP inference
  * Beta-Bernoulli conjugate pairs

* Parameter estimation

  * A thumbtack's head'tail outcome has a **Bernoulli distribution**
    * $Bernoulli(x| \theta) = \theta^x(1-\theta)^{(1-\theta)}$
  * We want to learn the model parameter $\theta$

* Maximum likelihood estimate (MLE)

  * Idea: find the parameter $\theta$ that maximizes the probability of observing a given dataset $\mathcal{D}$

  * Likelihood: $L(\theta ; \mathcal{D}) = \prod_{x \in \mathcal{D}} p(x; \theta)$

    * Using product here creates extremely small values
    * e.g. for 100 samples the likelihood can shrink to below $1 \times 10^{-30}$

  * Log-likelihood has the same maximum but is well-behaved:

    $\ell(\theta; \mathcal{D}) = log(L(\theta ; \mathcal{D})) = \sum_{x \in \mathcal{D}}\text{log}(p(x;\theta))$

  * How do we find the max-likelihood parameter?
    * $\theta^* = \text{argmax}_{\theta} \ell(\theta; \mathcal{D})$
    * For some simple models we can get a closed-form solution
    * For complex models we need to use numerical optimization
  * At the maximum log-likelihood, the derivative of $\ell(\theta; \mathcal{D})$ is zero
    * Set the derivative to zero and solve for $\theta$
    * This gives $\theta^{MLE} = \frac{\sum_{x \in \mathcal{D}} x}{|\mathcal{D}|}$
    * For the thumbtack example, this is simply the portion of heads in the overall dataset

* MLE does not reflect our uncertainty

  * e.g. $\theta^* = 0.2$ for both 1/5 heads and 1000/5000 heads

### Bayesian Approach

* In the Bayesian approach:

  * We maintain a distribution over parameters: $p(\theta)$ 

    * This is the **prior**

  * After observing $\mathcal{D}$ we update the distribution: $p(\theta | \mathcal{D})$

    * This is the **posterior**

  * How do we do this update? Using Bayes' rule:

    $p(\theta | \mathcal{D}) = \frac{p(\theta)p(\mathcal{D} | \theta )}{p(\mathcal{D})}$

* Conjugate priors

  * We can express the likelihood as $p(\mathcal{D} | \theta)$

    * In our running example we know the form of the likelihood:

      $p(\mathcal{D} | \theta) = \prod_{x \in \mathcal{D}} \text{Bernoulli}(x; \theta) = \theta^{N_h}(1 - \theta)^{N_t}$

  * We want the prior and posterior to have the same form so that we can easily update our belief with new observations

  * We say the Beta distribution is a conjugate prior to the Bernoulli likelihood

* The Beta distribution

  * $\text{Beta}(\theta | \alpha = \beta = 1)$ is uniform
  * The mean of the distribution is $\mathbb{E}[\theta] = \frac{\alpha}{\alpha + \beta}$
  * For $\alpha, \beta > 1$ the distribution is unimodal, and the mode is $\frac{\alpha-1}{\alpha+\beta-2}$

* Beta-Bernoulli conjugate pair

  * Prior:  $p(\theta) = \text{Beta}(\theta | \alpha, \beta) \propto \theta^{\alpha-1}(1-\theta^{\beta-1})$
  * Likelihood:  $p(\mathcal{D} | \theta) = \theta^{N_h}(1 - \theta)^{N_t}$
  * Posterior:  $p(\theta | \mathcal{D}) = \text{Beta}(\theta | \alpha + N_h, \beta + N_t) \propto \theta^{\alpha + N_h -1}(1-\theta^{\beta + N_t-1})$

* Effect of more data

  * With few observations, the prior has large influence
  * As we increase the number of observations the effect of the prior diminishes
    * i.e. the likelihood term dominates the posterior

### Posterior Predictive

* Our goal was to estimate the parameters ($\theta$) so that we can make predictions $p(x | \theta)$

* However now we have a posterior distribution over parameters $p(\theta | \mathcal{D})$

* Rather than using a single parameter $p(x | \theta)$ we need to calculate the average prediction

  * $p(x | \mathcal{D}) = \int_{\theta} p(\theta | \mathcal{D})p(x | \theta) d\theta$

* Posterior predictive for Beta-Bernoulli

  * Start from a Beta prior: $p(\theta) = \text{Beta}(\theta | \alpha, \beta)$

  * Observe $N_h$ heads and $N_t$ tails; the posterior is $p(\theta | \mathcal{D}) = \text{Beta}(\theta | \alpha + N_h, \beta + N_t)$

  * What is the probability the next coin flip is a head?

    $p(x=1 | \mathcal{D}) = \int_{\theta} \text{Bernoulli}(x=1 | \theta) \text{Beta}(\theta | \alpha + N_h, \beta + N_t) d\theta$

    $= \int_{\theta} \theta \text{Beta}(\theta | \alpha + N_h, \beta + N_t) = \frac{\alpha + N_h}{\alpha + \beta + N}$

  * Compare this with the prediction from maximum likelihood: $\frac{N_h}{N}$
  * If we assume a uniform prior ($\alpha = \beta = 1$) then the posterior predictive is $p(x=1 | \mathcal{D}) = \frac{N_h + 1}{N+2}$

* Strength of the prior
  * With a strong prior we need many samples to really change the posterior
  * For a Beta distribution $\alpha + \beta$ decides how strong the prior is
  * As our dataset grows, our estimate becomes more accurate

### MAP Estimate

* Maximum a Posteriori (MAP)
  * Sometimes it's difficult to work with the posterior distribution over parameters $p(\theta | \mathcal{D})$
  * Alternative: use the parameter with the highest posterior probability
    * $\theta^{MAP} = \text{argmax}_{\theta} p(\theta | \mathcal{D}) = \text{argmax}_{\theta}p(\theta)p(\mathcal{D} | \theta)$
    * $\theta^{MLE} = \text{argmax}_{\theta}(\mathcal{D} | \theta)$
* For the posterior $p(\theta | \mathcal{D}) = \text{Beta}(\theta | \alpha + N_h, \beta + N_t)$
  * The MAP estimate is the mode of the posterior: $\theta^{MAP} = \frac{\alpha + N_h - 1}{\alpha + \beta + N_h + N_t - 2}$
  * Compare with MLE: $\theta^{MLE} = \frac{N_h}{N_h + N_t}$
  * The two are equal for uniform prior $\alpha = \beta = 1$

### Categorical Distribution

* What if we have more than two categories?
* Instead of Bernoulli we have the multinoulli or **categorical** distribution
  * $\text{Cat}(x | \theta) = \prod^K_{k=1} \theta_k^{\mathbb{I}(x=k)}$
  * Where $\sum_k \theta_k = 1$
* Maximum-likelihood for categorical distributions
  * Likelihood:  $p(\mathcal{D} | \theta) = \prod_{x \in \mathcal{D}} \text{Cat}(x|\theta)$
  * Log-likelihood:  $\ell(\theta, \mathcal{D}) = \sum_{x \in \mathcal{D}} \sum_k \mathbb{I}(x=k)\text{log}(\theta_k)$
  * Similar to the binary case, max-likelihood estimate is given by data frequencies
    * $\theta_k^{MLE} = \frac{N_k}{N}$

### Summary

* In ML we often build a probabilistic model of the data

  * Learning a good model could mean maximizing the likelihood of the data

* An alternative is the Bayesian approach

  * Maintain a distribution over model parameters
  * We can specify our prior knowledge

  * We can use Bayes' rule to update our belief after a new observation
  * We can make predictions using posterior predictive
    * This can be computationally expensive, though not in our examples so far

* A middle path is the MAP estimate

  * Models our prior belief
  * Uses a single point estimate and picks the model with highest posterior probability

