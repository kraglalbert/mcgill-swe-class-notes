# Bias-Variance Tradeoff

1. [Introduction](#introduction)
2. [Random Forests](#random-forests)

### Introduction

* Learning objectives

  * Bias and variance tradeoff
  * Bootstrap aggregation (bagging) for variance reduction
    * Decision forests

* Generalization and model complexity

  * Simple models cannot fit the data (large training error due to underfitting)
    * Overly **biased**
  * Complex/expressive models can overfit the data
    * Small training error but high test error
    * **Variance** is higher; may not generalize well for this reason

* Regularization can help us trade-off between bias and variance

* We want to see how these two terms contribute to the generalization error

* Bias-variance decomposition

  * The expected loss is decomposed to:

    * Bias: how the average over all datasets differs from the regression function

      $\mathbb{E}[(f(x) - \mathbb{E}_{\mathcal{D}}[\hat f_{\mathcal{D}}(x)])^2]$

    * Variance: how a change in the dataset affects the prediction

      $\mathbb{E}[(\hat f_{\mathcal{D}}(x) - \mathbb{E}_{\mathcal{D}}[\hat f_{\mathcal{D}}(x)])^2]$

    * Noise error: the error even if we used the true mode $f(x)$

      $\mathbb{E}(\epsilon^2)$

* Using a larger regularization penalty results in higher bias and lower variance
* Reducing bias and variance
  * How to reduce the variance of a model without increasing its bias?
    * Bootstrap aggregation: average multiple models trained on subsets of the data
  * How to reduce the bias of a model without increasing its variance?
    * Reduce the bias of (simple models) by adding them in steps

* Bootstrap aggregation (bagging)

  * Given a dataset $\mathcal{D} = \{(x^{(n)}, y^{(n)})\}_{n=1}^N$

  * Subsample with replacement $B$ datasets of size N (non-parametric bootstrapping)

    * Uniformly sample from $\mathcal{D}$ with replacement

  * Train a model $\hat f_b$ on each of these bootstrap datasets (called bootstrap samples)

  * Aggregate the predictions of these models (bootstrap aggregation)

    $\hat f(x) = \frac{1}{B} \sum_b \hat f_b (x)$

  * Can also use this to produce a measure of uncertainty in predictions

* Bagging

  * Why does using average predictions reduce the variance?

    $\text{Var}(z_1 + z_2) = \text{Var}(z_1) + \text{Var}(z_2) + 2\text{Cov}(z_1, z_2)$

    * For uncorrelated variables the covariance term is zero

  * How about the average (for regression)?

    * $z_1, z_2..., z_B$ are uncorrelated random variables with variance $\sigma^2$

    * Variance of the average is:

      $\text{Var}(\frac{1}{B} \sum_b z_b) = \frac{1}{B^2} \text{Var}(\sum_b z_b) = \frac{1}{B^2}B \sigma^2 = \frac{1}{B} \sigma^2$

    * So the bagging reduces variance

* Bagging for classification

  * For classification we cannot use the mean of classifiers; we must use voting (i.e. the mode)
  * Bagging produces a better classifier
  * Crowds are wise when:
    * Individuals are better than random
    * Votes are uncorrelated

### Random Forests

* An ensemble learning method for classification, regression and other tasks that operate by constructing a multitude of decision trees at training time and outputting the class that is the mode of the classes (classification) or mean/average prediction (regression) of the individual trees
  * Random forests correct for decision trees' habit of overfitting
  * Random forests generally outperform a single decision tree
* Can further reduce the correlation between decision trees
* Feature sub-sampling
  * Only a random subset of features are available for split at each step
    * Further reduce the dependence between decision trees
    * Magic number: around $\sqrt{D}$
    * This is a hyper-parameter; can be optimized using cross-validation
* Out-of-bag (OOB) samples:
  * The instances not included in a bootstrap dataset can be used for validation
  * Simultaneous validation of decision trees in a forest
  * No need to set aside data for cross validation
  * The OOB error can be used for parameter tuning (e.g. size of the forest)

### Summary

* Bias-variance tradeoff
  * Formalizes the relation between training error (bias), complexity (variance), and the test error (bias + variance)
  * Not so elegant beyond L2 loss
* Bootstrap aggregation (bagging) can reduce the variance of unstable models
* Random forests
  * Bagging + further de-correlation of features at each split
  * OOB validation instead of cross-validation
  * Destroys interpretability of decision trees
  * Perform well in practice
  * Can fail if only a few relevant features exist (due to feature sampling)







 