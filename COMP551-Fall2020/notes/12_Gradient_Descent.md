# Gradient Descent Methods

1. [Gradient Descent](#gradient-descent)
2. [Stochastic Gradient Descent](#stochastic-gradient-descent)
3. [Momentum](#momentum)
4. [Summary](#summary)

### Gradient Descent

* Learning objectives

  * Gradient descent
  * Stochastic gradient descent
  * Method of momentum
  * Using an adaptive learning rate
  * Sub-gradient

  * Application to linear regression and classification

* Gradient descent

  * An iterative algorithm for optimization
  * Starts from some $w^{\{0\}}$
  * Update using gradient: $w^{\{t+1\}} \leftarrow \alpha \nabla J(w^{\{t\}})$
    * $\alpha$ is the **learning rate**
    * $J$ is the cost function
  * This converges to a *local* minima

* Convex functions

  * A convex subset of $\mathbb{R}^N$ intersects any line in at most one line segment
    * i.e. if you connect two points in the subset, no part of the line connecting them can leave the space defined by the subset
  * A convex function is a function for which the epigraph is a convex set
    * Epigraph: the set of all points above the graph
  * Convex functions are easier to minimize since they only have one critical point which is the global minimum
  * A **concave** function is the negative of a convex function, i.e. easy to maximize

* Recognizing convex functions

  * A constant function is convex $f(x) = c$
  * A linear function is convex $f(x) = w^T x$
  * A function is convex if the second derivate is positive everywhere
    * Examples:  $x^{2d}, e^x, -\text{log}(x), -\sqrt{x}$
  * The sum of convex functions is also convex
  * The maximum of convex functions is convex
  * The composition of convex functions is generally **not** convex
    * However, if $f, g$ are convex and $g$ is non-decreasing then $g(f(x))$ is convex

* The time complexity of gradient descent is $O(ND)$

  * Compared to $O(ND^2 + D^3)$ for the direct solution for linear regression
  * Gradient descent can be much faster for large $D$

* Possible termination conditions for gradient descent

  * Some maximum number of iterations
  * Small gradient
  * A small enough change in the objective
  * Increasing error on the validation set
    * Early stopping: one way to avoid overfitting

* Learning rate

  * The learning rate has a significant effect on gradient descent
  * Too small: it may take a long time to converge
  * Too large: it can overshoot the critical point and never converge

### Stochastic Gradient Descent

* We can write the cost function as an average over instances

  * $J(w) = \frac{1}{N} \sum_{n=1}^N J_n(w)$

  * $J_n(w)$ is the cost for a single data point, e.g. for linear regression $J_n(w) = \frac{1}{2} (w^T x^{(n)} - y^{(n)})^2$

  * The same is true for the partial derivatives:

    $\nabla J(w) = \mathbb{E}_{\mathcal{D}} [ \nabla J_n(w)]$

* Idea: use stochastic approximations $\nabla J_n(w)$ in gradient descent

  * With a small learning rate we get guaranteed improvement at each step
  * The steps are on average in the right direction

* Stochastic gradients are not zero even at the optimum $w$

  * How can we guarantee convergence?
  * Idea: schedule to have a **smaller learning rate over time**
  * Robbins Monro
    * The sequence we use should satisfy $\sum_{t=0}^{\infin} \alpha^{}\{ t\} = \infin$
    * The steps should go to zero, i.e. $\sum_{t=0}^{\infin} (\alpha^{}\{ t\})^2 < \infin$
    * Examples: $\alpha^{\{t\}} = \frac{10}{t}, \alpha^{\{t\}} = t^{-0.51}$

* Minibatch SGD

  * Use a minibatch to produce gradient estimates

    $\nabla J_{\mathbb{B}} = \sum_{n \in \mathbb{B}} \nabla J_n(w)$

  * $\mathbb{B} \sube \{1,...N\}$ is a subset of the dataset

* Oscillations

  * Gradient descent can oscillate a lot!
  * Each gradient step is perpendicular to isocontours
  * In SGD this is worsened due to noisy gradient estimates

### Momentum

* To help with oscillations, we can use a running average of the gradients

* More recent gradients should have higher weights

* $\Delta w^{\{t\}} \leftarrow \beta \Delta w^{\{t-1\}} + (1-\beta)\nabla J_{\mathbb{B}} (w^{\{t\}})$

* Momentum of $\beta = 0$ reduces to SGD

  * A common value is $\beta > 0.9$

* This is effectively an exponential moving average

  $\Delta w^{\{T\}} = \sum_{t=1}^T \beta^{T-t} (1 - \beta) \nabla J_{\mathbb{B}}(w^{\{t\}})$

### Summary

* Learning: optimizing model parameters (minimizing a cost function)
* Use gradient descent to find a local minimum
  * For convex functions we get a global minimum
  * Easy to implement
* Stochastic gradient descent
  * For large datasets use minibatch for a noisy but fast estimate of the gradient
  * Robbins Monro condition: reduce the learning rate over time to help with noise
* Better SGD optimization
  * Momentum: exponential running average to help with the noise