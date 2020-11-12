# Expectation Maximization

1. [Introduction](#introduction)
2. [Latent Variable Models](#latent-variable-models)
3. [Mixture of Gaussians](#mixture-of-gaussians)
4. [Summary](#summary)

### Introduction

* Learning objectives
  * What is a latent variable model?
  * Gaussian mixture model
  * The intuition behind the expectation maximization algorithm
  * Relationship to K-means
* Probabilistic modelling so far
  * Given data $\mathcal{D} = \{x^{(1)}...,x^{(n)} \}$, model $p(x; \theta)$ (e.g. multivariate Gaussian, Bernoulli)
  * Or if we have labels $\mathcal{D} = \{ (x^{(1)}, y^{(1)})...,(x^{(n)}, y^{(n)}) \}$ we saw generative models for classification $p(x, y; \theta) \propto p(y; \theta)p(x|y; \theta)$
  * Learning
    * Used maximum likelihood or Bayesian inference to fit the data 
    * $\hat \theta = \text{argmax}_{\theta} \sum_n \text{log}p(x^{(n)},y^{(n)}; \theta)$
      * e.g. we used this to fit Naive Bayes
    * $p(\theta | \mathcal{D}) = p(\theta)p(\mathcal{D} | \theta)$

### Latent Variable Models

* Sometimes we do not directly observe all the variables that we wish to model

  * These are called hidden or **latent** variables

* The data $\mathcal{D} = \{x^{(n)}..., x^{(n)}\}$ is partial or incomplete

* The model should account for both observed ($x$) and latent variables ($z$): $p(x, z; \theta)$

* Examples:

  * Bias (unobserved) leading to a hiring practice (observed)
  * 3D scene (unobserved) leading to a 2D photograph (observed)
  * Gravity (unobserved) leading to an apple falling (observed)
  * Input features (observed) having some unobserved class labels

* This gives us a lot of flexibility in modelling the data

  * We can find hidden factors and learn about how they lead to our observations
  * Both a natural and powerful way to model complex observations
  * However, it can be difficult to "learn" the model from partial observations

* Often we model $p(x,z; \theta) = p(z; \theta)p(x|z; \theta)$

  * If the latent variable is the class label, then this resembles generative classification:

    $p(x,y; \theta) = p(y; \theta)p(x|y; \theta)$

  * However here we don't observe the labels
  * We saw clustering performs classification without having the labels—can we use latent variable models for clustering?

* Mixture models

  * Suppose the latent variable has a categorical distribution (unobserved class label)

    $p(x,z;\theta, \pi) = \text{Categorical}(z;\pi)p(x|z; \theta)$

  * We only observe $x$

  * We can marginalize out $z$ to get the data distribution

    $p(x; \theta, \pi) = \sum_k \text{Categorical}(z=k; \pi)p(x|z=k; \theta) = \sum_k \pi_k p(x|z=k; \theta_k)$

  * Each datapoint $x$ with probability $\pi_k$ comes from the distribution $p(x|z=k;\theta_k)$
  * The marginal over the observed variables is a mixture of K distributions

### Mixture of Gaussians

* Model the data as a mixture of K Gaussian distributions

  $p(x; \pi, \{\mu_k, \Sigma_k\}) = \sum_k \pi_k \mathcal{N}(x;\mu_k, \Sigma_k)$

* We can calculate the probability of each datapoint belonging to a cluster $k$

  * This is called the **responsibility** of cluster $k$ for datapoint $n$

    $p(z=k|x^{(n)}) = \frac{\pi_k \mathcal{N}(x^{(n)}; \mu_k, \Sigma_k )}{\sum_c \pi_c \mathcal{N}(x^{(n)}; \mu_c, \Sigma_c) }$

  * This is the weighted density of k-th Gaussian at $x^{(n)}$ over the density of the whole mixture at that point

* Clustering with Gaussian mixture

  * This gives us a probabilistic alternative to K-means
  * Soft cluster membership: $r_{n,k} = p(z=k | x^{(n)})$
    * Instead of $r_{n,k} \in \{0,1\}$
  * We have a cluster mean $\mu_k$ and cluster covariance matrix $\Sigma_k$

* Model parameters for Gaussian mixture

  * $\mu_k = \frac{1}{\sum_n r_{n,k}} \sum_n r_{n,k} x^{(n)}$
    * Weighted mean (weight is the responsibility)
  * $\Sigma_k = \frac{1}{\sum_{n'}r_{n', k}} \sum_n r_{n,k}(x^{(n)} - \mu_k)(x^{(n)} -\mu_k)^T$
    * Weighted covariance
  * $\pi_k = \frac{\sum_n r_{n,k}}{N}$
    * The total amount of responsibilities accepted by cluster $k$

* Expectation maximization algorithm for Gaussian mixture

  * Iteratively update both parameters and responsibilities until convergence

  * Pseudo-implementation:

    > Start from some initial model $\{\mu_k, \Sigma_k\}, \pi$
    >
    > Repeat until convergence:
    >
    > ​	Update the responsibilities given the model $\forall n,k$ (expectation step)
    >
    > ​	Update the model ($\mu_k, \Sigma_k, \pi_k$) given the responsibilities $\forall k$ (maximization step)

* Comparison with K-means

  |                      | K-Means                                                      | EM for Gaussian Mixture Model                                |
  | -------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | **Objective**        | Minimize the sum of squared Euclidean distance to cluster centers | Minimize the negative log-marginal likelihood                |
  | **Parameters**       | Cluster centers                                              | Means, covariances and mixture weights                       |
  | **Responsibilities** | Hard cluster memberships                                     | Soft cluster memberships                                     |
  | **Algorithm**        | Alternating minimization with respect to parameters and responsibility | Alternating minimization with respect to parameters and responsibility |
  | **Feature Scaling**  | Sensitive                                                    | Robust due to learning the covariance                        |
  | **Efficiency**       | Faster convergence                                           | Slower convergence                                           |
  | **Optimality**       | Converges to a local optima; swapping cluster indices makes no difference in the objective | Converges to a local optima; swapping cluster indices makes no difference in the objective |

* We saw the application of expectation maximization to Gaussian mixture

  * Expectation is a general algorithm for learning latent variable models

  * There is also a simple variation called **hard expectation maximization/hard EM**

    > Start from some initial model $\theta$
    >
    > Repeat until convergence:
    >
    > ​	E-step: do a deterministic completion $z^{(n)} = \text{argmax}_z(z| x^{(n)}; \theta) \forall n$
    >
    > ​	M-step: fit the model $\theta$ to the completed data using max-likelihood

  * K-means is performing hard EM using a fixed covariance and mixture weights
    * Find the closest center (finding the Gaussian with highest probability)
    * Fit Gaussians to completed data $(x, z)$

### Summary

* Latent variable models:
  * A general and powerful type of probabilistic model
  * We have only partial observations
  * Can use EM to learn the parameters and infer hidden values
* Expectation maximization (EM)
  * Useful when we have hidden variables or missing values
  * Tries to maximize log-likelihood of observations
  * Iterates between learning model parameters and inferring the latents
  * Converges to a local optima, i.e. performance depends on initialization
* The only concrete example that we saw:
  * Gaussian mixture model
  * EM in Gaussian mixture model for soft clustering
  * The relationship to K-means