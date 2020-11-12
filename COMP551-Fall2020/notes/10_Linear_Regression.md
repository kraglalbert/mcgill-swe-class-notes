# Linear Regression

1. [Introduction](#introduction)
2. [Feature Engineering](#feature-engineering)
3. [Probabilistic Interpretation](#probabilistic-interpretation)
4. [Summary](#summary)

### Introduction

* Learning objectives 

  * The linear model
  * Evaluation criteria
  * How to find the best fit
  * Geometric interpretation
  * Maximum likelihood interpretation

* Notation: design matrix

  * Concatenate all instances such that each row in the matrix is a datapoint and each column is a feature
  * Denoted as $X \in \mathbb{R}^{N \times D}$

* Linear model

  * Assuming a scalar output, i.e. $f_w: \mathbb{R}^D \rightarrow \mathbb{R}$
    * Will generalize to vector output later
  * A linear model has the form $f_w(x) = w_0 + w_1x_1 + ... w_Dx_D$
    * $w_0$ is the **bias** or intercept
    * $w$ are the model parameters or weights
  * Simplification: concatenate a 1 to $x$ so that $x = [1, x_1, x_2..., x_D]^T$
  * $f_w(x) = w^T x$

* Loss function

  * Objective: find parameters to fit the data $x^{(n)}, y^{(n)} \forall n$

  * We want to minimize a measure of difference between $\hat y^{(n)} = f_w(x^{(n)})$ and $y^{(n)}$

  * We can use square error loss aka **L2 loss**:

    $L(y, \hat y) = \frac{1}{2}(y-\hat y)^2$
    * This is the loss for a *single instance*

  * Cost function for the entire dataset (sum of squared errors):

    $J(w) = \frac{1}{2} \sum_{n=1}^N (y^{(n)} - w^T x^{(n)})^2$

  * Keep in mind that the bias "adds" another dimension to the data!

* Linear least squares

  * $w^* = \text{argmin}_w \sum_n (y^{(n)} - w^Tx^{(n)})^2$

* Matrix form

  * Instead of $\hat y^{(n)} = x^T x^{(n)}$, use the design matrix to write $\hat y = Xw$

    * $\hat y$ is a $N \times 1$ column vector

  * Linear least squares is now:

    $\text{argmin}_w \frac{1}{2}||y-Xw||_2^2 = \frac{1}{2} (y-Xw)^T(y-Xw)$

* Minimizing the cost

  * The cost function is a smooth function of $w$
  * We can find the minimum by setting partial derivatives to zero

* Gradient

  * For a multivariate function $J(w_0, w_1)$ we have partial derivatives instead of a single derivative
  * Critical point: all partial derivatives are zero
  * Gradient: vector of all partial derivatives

* Normal equation

  * The optimal weights $w^*$ satisfy $\sum_n (y^{(n)} - w^Tx^{(n)})x_d^{(n)} = 0, \forall d$

  * Matrix form (using design matrix)

    $X^T(y-X2) = \vec 0$

  * Called the normal equation because for optimal $w$, the residual vector is normal to the column space of the design matrix
  * System of $D$ linear equations: $X^T Xw = X^T y$ or $Aw = b$

* Direct solution

  * We can get a closed-form solution to the problem above

    $w^* = (X^TX)^{-1}X^T y$

* Uniqueness of the solution

  * What if the covariance matrix is not invertible?
  * $\Lambda^{-1}$ does not exist if any eigenvalues are zero (since each diagonal value is $\frac{1}{\lambda_i}$)
  * Therefore if features are not linearly independent there exists some $\{ \alpha_d \}$ such that $\sum_d \alpha_d x_d = 0$
  * Alternatively, if $w^*$ satisfies the normal equation $X^T(y-Xw^*) = 0$, then the following are also solutions: $w^* + c \alpha, \forall c$

* Time complexity

  * $w^* = (X^T X)^{-1} X^T y$
  * $X^T X$ is $D \times D$ elements, each requiring $N$ multiplications, so $O(D^2 N)$
  * Inverting $X^T X$ is $O(D^3)$
  * $X^T y$ is $D$ elements, each using $N$ operations, so $O(ND)$

  * The total complexity for $N > D$ is $O(ND^2 + D^3)$
  * In practice we don't directly use matrix inversion since it's unstable
  * However, other stable solutions (e.g. Gaussian elimination) have similar complexity

### Feature Engineering

* So far we learned a linear function $f_w = \sum_d w_d x_d$
* However, this can sometimes be too simplistic
  * Idea: create new, more useful features out of an initial set of given features
  * e.g $x_1^2, x_1 x_2, \text{log}(x)$
    * These are all **non-linear**
* Non-linear basis functions
  * Let's denote the set of all features by $\phi_d(x) \forall d$
  * The problem of linear regression doesn't change: $f_w = \sum_d w_d \phi_d$
  * We replace $X$ with $\Phi$ so that the solution becomes $(\Phi^T \Phi)w^* = \Phi^T y$
  * Some options for $\phi_k$ include:
    * Polynomial bases: $\phi_k(x) = x^k$
    * Gaussian bases: $\phi_k(x) = e^{-\frac{(x-\mu_k)^2}{s^2}}$
    * Sigmoid bases: $\phi_k(x) = \frac{1}{1+e^{-\frac{x-\mu_k}{s}}}$

### Probabilistic Interpretation

* Given the dataset $\mathcal{D} = \{ (x^{(1)}, y^{(1)})..., (x^{(n)}, y^{(n)})\}$

  * Learn a probabilistic model $p(y | x; w)$

  * Consider $p(y | x; w)$ with the following form:

    $p_w(y | x) = \mathcal{N}(y | w^T x, \sigma^2) = \frac{1}{\sqrt{2\pi\sigma^2}} e^{-\frac{(y-w^Tx)^2}{2\sigma^2}}$

    * This is the conditional probability

  * To fit this model we maximize the conditional likelihood
  * Likelihood: $L(w) = \prod_{n=1}^N p(y^{(n)} | x^{(n)}; w)$
  * Log-likelihood: $\ell(w) \sum_n -\frac{1}{2\sigma^2}(y^{(n)} - w^T x^{(n)})^2 + \text{constants}$
  * Max-likelihood params: $w^* = \text{argmax}_w \ell(w) = \text{argmin}_w \frac{1}{2} \sum_n (y^{(n)} - w^Tx^{(n)})^2$
    * Maximizing the log-likelihood is the same as minimizing the linear least squares cost
  * Whenever we use square loss, we are assuming Gaussian noise

### Summary

* Linear regression
  * Models targets as a linear function of features
  * Fit the model by minimizing the sum of squared errors
  * Has a direct solution with $O(ND^2 + D^3)$ complexity
  * Probabilistic interpretation
* We can build more expressive models using any number of non-linear features

