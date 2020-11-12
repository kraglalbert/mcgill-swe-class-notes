# Gaussian Distributions

1. [Univariate Gaussian](#univariate-gaussian)
2. [Multivariate Gaussian](#multivariate-gaussian)
3. [Chain Rule](#chain-rule)
4. [Summary](#summary)

### Univariate Gaussian

* Learning objectives

  * Gaussian distribution
  * Motivation and the functional form of its density
  * Covariance matrix
  * Correlation and dependence
  * Linear transformations of Guassian
  * Marginalization, chain rule and conditioning for Gaussian

* Univariate Gaussian density

  * Gaussian probability density function (PDF)

    $\mathcal{N}(x; \mu, \sigma) = \frac{1}{\sqrt{2\pi \sigma^2}}e^{-\frac{(x-\mu)^2}{2\sigma^2}}$

  * The two parameters are $\mu, \sigma$
    
    * These are the mean and variance, respectively
  * Given a dataset $\mathcal{D}$, the maximum likelihood estimate of $\mu, \sigma^2$ are the empirical (observed) mean and variance
    * $\mu^{MLE} = \frac{1}{N}\sum_n x^{(n)}$
    * $\sigma^{2^{MLE}} = \frac{1}{N} \sum_n (x^{(n)} - \mu^{MLE})^2$

* Why is the Gaussian distribution important?

  * The average (and sum) of IID random variables has a Gaussian distribution
  * This justifies the use of a Gaussian distribution for observations that are the mean or sum of some random values

### Multivariate Gaussian

* Now instead of $x \in \mathbb{R}$ we have a column vector $x \in \mathbb{R}^D$

* The density function is now:

  $\mathcal{N}(x; \mu, \Sigma) = \frac{1}{|2\pi\Sigma|}\text{exp}()-\frac{1}{2} (x-\mu)^T\Sigma^{-1}(x-\mu)$

  * $\mu$ is now $D$ dimensional 
  * $\Sigma$ is a $D \times D$ matrix

* Covariance matrix

  * Recall the variance of a random variable: $\text{Var}(x) = \mathbb{E}[x^2] - \mathbb{E}[x]^2$
  * Covariance of two random variables: $\text{Cov}(x,y) = \mathbb{E}[xy] - \mathbb{E}[x]\mathbb{E}[y]$
  * Now for  $x \in \mathbb{R}^D$ we have a covariance matrix $\Sigma$
    * $\Sigma_{i,j} = \text{Cov}(x_i, x_j)$
  * $\Sigma = \mathbb{E}[xx^T] - \mathbb{E}[x]\mathbb{E}[x]^T$

* Given a dataset $\mathcal{D}$ we can sample the covariance matrix

  * $\Sigma^{MLE} = \mathbb{E}_{\mathcal{D}}[(x-\mathbb{E}_{\mathcal{D}}[x])(x-\mathbb{E}_{\mathcal{D}}[x])^T]$
  * The empirical estimate: $x-\mathbb{E}_{\mathcal{D}}[x] = x - (\frac{1}{N}\sum_{x\in \mathcal{D}} x)$

* **Isotropic Gaussian**: covariance matrix is diagonal and all values on the diagonal are the same

* **Axis-aligned Gaussian**: covariance matrix is diagonal but values on the diagonal are not all the same

* Decomposing the covariance matrix

  * The covariance matrix is *symmetric positive semi-definite*

  * Symmetric because $\Sigma_{i,j} = \Sigma_{j, i}$

  * Positive semi-definite because $y^T\Sigma y = \text{Var}(y^Tx) \geq 0$

  * Any symmetric positive semi-definite matrix can be decomposed as:

    $\Sigma = Q\Lambda Q^T$ 

    * $QQ^T = Q^T Q = I$ (rotation and reflection)
    * $\Lambda$ is a diagonal $D \times D$ matrix

  * This means **we can produce any Gaussian by rotation and reflection of an axis-aligned Gaussian**

* Marginalization

  * For Gaussian distributions, the marginal is also Gaussian
  * Marginalization corresponds to a linear transformation

* Correlation and dependence
  * Correlation is normalized covariance
  * $\text{Corr}(x_i, x_j) = \frac{\text{Cov}(x_i, x_j)}{\sqrt{\text{Var}(x_i)\text{Var}(x_j)}} \in [-1, 1]$
  * Two variables that are independent are uncorrelated as well
    * The inverse is generally not true (zero correlation does not mean independence)
    * However, the inverse *is* true for Gaussian distributions

### Chain Rule

* Example
  * A dragon's lifespan is approximately normally distributed with $\mu_A = 1000, \sigma_A = 100$
  * The average heat of a dragon's breath is normal with $\mu_b = 2x_A - 273, \sigma_{B|A} = 30$
  * What is the probability that a random dragon can melt stainless steel?
* For this we need to use the chain rule
  * Given $p(x_A) = \mathcal{N}(\mu_A, \Sigma_A)$, $p(x_B|x_A) = \mathcal{N}(Qx_A + c, \Sigma_{B|A})$
  * The joint distribution $p(x_B, x_A)$ is also normal

### Summary

* Gaussian distribution is motivated by the central limit theorem
* The expression for the multivariate Gaussian
* The maximum-likelihood estimate of parameters
* The covariance matrix and its decomposition
* Zero covariance means independence when working with Gaussian distributions
* Linear transformations of Gaussians produces Gaussian distributions
* Marginalization and conditioning produces Gaussian
* Sum of independent Gaussian random variables is Gaussian