# Logistic and Softmax Regression

1. [Introduction](#introduction)
2. [Probabilistic View](#probabilistic-view)
3. [Multiclass Classification](#multiclass-classification)
4. [Summary](#summary)

### Introduction

* Learning objectives

  * What are linear classifiers?
  * Logistic regression
    * Model, loss function
  * Maximum likelihood view
  * Multi-class classification

* Motivation

  * Logistic regression is the most commonly used data science method at work

* Classification problem

  * Dataset of inputs $x^{(n)} \in \mathbb{R}^D$
  * And discrete targets $y^{(n)} \in \{0,...C \}$
  * For binary classification: $y^{(n)} \in \{ 0,1 \}$

* Linear classification

  * Linear decision boundary $w^T x + b$
  * How do we find these boundaries?
    * Different approaches give different linear classifiers

* First idea: linear regression

  * Fit a linear model to predict the label $y \in \{ -1, 1 \}$

  * Set the decision boundary as $w^T x = 0$

  * Given a new instance, predict the label accordingly:

    $\hat y = \begin{cases} 
        1 & w^T x > 0 \\
        -1 & w^T x < 0
     \end{cases}$

  * However there is a problem: **correct predictions can have higher loss than incorrect ones**
    * e.g. correctly classifying $w^T x  = 100 > 0$; the loss is $(100-1)^2$
  * Solution: we should try squashing all positive instances together and all the negative ones together

* Logistic function

  * Idea: apply a squashing function to $w^T x \rightarrow \sigma(w^T x)$

  * The logistic function gives us the squashing properties we want:

    $\sigma(w^T x) = \frac{1}{1+ e^{-w^T x}}$

  * The decision boundary is $\frac{1}{2}$ instead of 0
    * Still a linear decision boundary

* Logistic regression: loss function

  * We could use L2 loss since the previous problem is not resolved; however the L2 loss is hard to optimize because it is non-convex in $w$

  * We can use the **cross-entropy loss** instead 

    $L_{CE}(\hat y, y) = -y \text{log}(\hat y) - (1-y)\text{log}(1- \hat y)$

    $\hat y = \sigma(w^T x)$

  * This is convex in $w$, and there is also a probabilistic interpretation

### Probabilistic View

* We can interpret the class prediction as a probability

  * $\hat y = p_w(y=1| x) = \sigma(w^T x)$

  * The log-ratio of class probabilities is linear:

    $\text{log}\frac{\hat y}{1 - \hat y} = \text{log}\frac{\sigma(w^T x)}{1 - \sigma(w^T x)} = \text{log}\frac{1}{e ^{-w^T x}} = w^T x$

  * So we have a Bernoulli likelihood:

    $p(y^{(n)} | x^{(n)}; w) = \text{Bernoulli}(y^{(n)}; \sigma(w^T x^{(n)}))$

* Maximum likelihood and logistic regression

  * We should find parameters $w^*$ that maximize the log-likelihood

    $w^* = \text{max} \sum_{n=1}^N \text{log}p_w(y^{(n)} | x^{(n)}; w) = \text{min}_w J(w)$

  * Using cross-entropy loss in logistic regression is also maximizing the conditional likelihood
    * We saw a similar interpretation for linear regression—L2 loss maximizes the conditional Gaussian likelihood

### Multiclass Classification

* Using the probabilistic view we can extend logistic regression to a multiclass setting
* With C classes we have a categorical likelihood:
  * $\text{Categorical}(y | \hat y) = \prod_{c=1}^C \hat y_c^{\mathbb{I}(y=c)}$
  * Subject to $\sum_c \hat y_c = 1$
    * This is achieved using the **softmax** function

* Softmax

  * Generalization of logistic to 2+ classes:

    * Logistic $\sigma: \mathbb{R \rightarrow \{ 0,1 \}}$ 
    * Produces a single probability

  * Softmax: $\mathbb{R}^C \rightarrow \Delta_C$

    $\hat y_c = \text{softmax}_c = \frac{e^{z_c}}{\sum_{c'=1}^C e^{z_c'}}$

  * If input values are large, softmax becomes similar to argmax

  * Softmax is similar to the logistic function since it is also a squashing function

* Multiclass classification

  * We have one parameter vector for each class
  * To simplify equations we write $z_c = w_c^T x$

* Discriminative vs. generative classification

  | Naive Bayes                                                  | Logistic Regression                                          |
  | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | Learns the joint distribution $p(x,y) = p(y)p(x|y)$          | Learns the conditional distribution $p(y|x)$                 |
  | The max-likelihood estimate of prior and likelihood had a closed-form solution (using emprical frequencies) | No closed-form solution (use numerical optimization)         |
  | Makes stronger assumptions                                   | Weaker assumptions since it doesn't model the distribution of input |
  | Usually works better with smaller datasets                   | Usually works better with larger datasets                    |
  | Linear decision boundary for Gaussian naive Bayes *if* the variance is fixed | Linear decision boundary                                     |

### Summary

* Logistic regression: logistic activation function + cross-entropy loss
  * Cost function
  * Probabilistic interpretation
  * Using maximum likelihood to derive the cost function
    * Gaussian likelihood $\rightarrow$ L2 loss
    * Bernoulli likelihood $\rightarrow$ cross-entropy loss
  * Multi-class classification: softmax + cross-entropy
    * Cost function
    * One-hot encoding
    * Gradient calculation

