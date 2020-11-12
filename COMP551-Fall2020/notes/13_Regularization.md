# Regularization

1. [Introduction](#introduction)
2. [Ridge Regression](#ridge-regression)
3. [L1 Regularization](#l1-regularization)

### Introduction

* Learning objectives
  * Intuition for model complexity and overfitting
  * Regularization penalty (L1 and L2)
  * Probabilistic interpretation
* An observation
  * When overfitting, we sometimes see large weights
  * Idea: penalize large parameter values

### Ridge Regression

* Also known as **L2 regularized linear least squares regression**

* $J(w) = \frac{1}{2} ||Xw - y||_2^2 + \frac{\lambda}{2}||w||_2^2$

  * The first term is the sum of squared error: $\frac{1}{2} \sum_n (y^{(n)} - w^T x)^2$
  * The second term is the squared L2 norm of $w$: $w^T w = \sum_d w^2$

* Regularization parameter $\lambda > 0$ controls the strength of regularization

* A good practice is not to penalize the intercept: $\lambda( ||w||_2^2 - w_0^2)$

  * $\lambda$ is a hyper-parameter (use a validation set or cross-validation to pick the best value)

* As before, to solve we can set the derivative to zero

  $\nabla J(w) = \sum_{x,y \in \mathcal{D}} x(w^T x - y) + \lambda w$

  $= X^T (Xw - y) + \lambda w = 0$

*  We get a linear system of equations $(X^T X + \lambda I)w = x^T y$

  * $w = (X^T X + \lambda I)^{-1} X^T y$
    * The only part that's different due to regularization is the added $\lambda I$ term
  * $\lambda I$ makes it invertible, so we can have linearly dependent features + a unique solution

* Example: polynomial bases for linear reggresion

  * Without regularization: using $D=10$ we can perfectly fit the training data, but have high test error
  * With regularization: we can fix $D=10$ and change the amount of regularization

* Probabilistic regression

  * Recall: linear and logistic regression maximize the log-likelihood

  * Can we do Bayesian inference instead of maximum likelihood? Yes, but in general this is expensive

  * Maximum a posteriori (MAP) is a cheap compromise

    $w^{MAP} = \text{argmax}_w p(w)p(y|X, w)$

    $= \text{argmax}_w \text{log}p(y|X,w) + \text{log}p(w)$

  * All that's changing is the additional penalty on $w$

* Gaussian prior

  * Assume independent zero-mean Gaussians
  * **L2 regularization assumes the prior is Gaussian**

### L1 Regularization

* Another notable choice of prior is the Laplace distribution
* L1 regularization: $J(w) \leftarrow J(w) + \lambda ||w||_1$
  * Also called **lasso **(least absolute shrinkage and selection operator)
* L1 vs. L2 regularization
  * Lasso produces sparse weights (many are zero rather than small; L2 is the opposite)
  * The optimal solution with L1 regularization is more likely to have zero components
* L0 norm: the number of non-zero elements in a vector
  * Penalizes the number of non-zero features when used for regularization
  * A penalty of $\lambda$ for each feature
  * Performs feature selection
* L1-regularized linear regression has efficient solvers
  * Do not penalize the bias $w_0$
  * Use diminishing learning rate
  * Note that the optimal $w_1$ becomes zero

### Summary

* Complex models can have very different training and test error (generalization gap)
* Regularization bounds this gap by penalizing model complexity
  * L1 and L2 regularization
  * Probabilistic interpretation: different priors on weights
  * L1 produces sparse solutions (useful for feature selection)

