# Important Concepts

1. [Introduction](#introduction)
2. [Cross Validation](#cross-validation)
3. [Evaluation Metrics](#evaluation-metrics)
4. [Inductive Bias](#inductive-bias)
5. [Summary](#summary)

### Introduction

* Learning objectives
  * Overfitting and generalization
  * Validation and cross-validation
  * Curse of dimensionality
  * No free lunch
  * Inductive bias of a learning algorithm
* Model selection
  * Many ML algorithms have hyper-parameters
    * e.g. K in KNN, maximum depth of decision tree
  * How should we select the best hyper-parameter?
  * What if unseen data is completely different from training data?
    * Then there is no point in learning!
  * Assumption: training data points are samples from some unknown distribution
    * We assume data points are independent identically distributed (IID)
    * Unseen data comes from the same distribution
* Loss, cost and generalization
  * Assume we have a model $f(x)$ which takes an input $x$ and outputs a prediction $y$
  * We also have a loss function $\ell(y, f(x) )$ which measures the error in our prediction
  * We train our models to minimize the cost function, but what we really care about is the **generalization error**: $\mathbb{E}_{x,y \sim p} \ell(y, f(x) )$
    * We can set aside part of the training data and use it to estimate the generalization error
    * Pick hyper-parameters that give us the best **validation error**
    * Note that validation and test error could be different because we are using a limited amount of data to estimate the generalization error

### Cross Validation

* How can we get a better estimate of the generalization error?
  * We could increase the size of the validation set, but this reduces the training set
* Cross validation helps us get better estimates + uncertainty measures
  * Idea: divide the data into L parts
  * Use one part for validation and L-1 for training
  * Do L "runs", using a different part each time for validation
  * Use the average validation error and its variance (uncertainty) to pick the best model
  * This is called **L-fold** cross validation
  * In **leave-one-out** cross validation L=N, i.e. only one instance is used for validation each time
* A rule of thumb: pick the simplest model with one standard deviation of the model with lowest validation error

### Evaluation Metrics

* When evaluating a classifier it is useful to look at the **confusion matrix**
  * A CxC table that shows how many samples of each class are classified as belonging to another class
  * A classifier's accuracy is the sum of the diagonal divided by the total sum of the matrix
* For binary classification the elements of the confusion matrix are True Positive, True Negative, False Positive and False Negative
  * Some other evaluation metrics based on the confusion table:
    * $Accuracy = \frac{TP+TN}{P+N}$
    * $Error Rate = \frac{FP+FN}{P+N}$
    * $Precision = \frac{TP}{TP + FP}$
    * $Recall = \frac{TP}{P}$

### Curse of Dimensionality

* Learning in high dimensions can be difficult
  * Suppose our data is uniformly distributed in some range, e.g. $x \in [0, 3]^D$
  * Predict the label by counting labels in the same unit of the grid (similar to KNN)
  * To have at least one example per unit, we need $3^D$ training examples
    * For even medium $D$, this is infeasible
* In high dimensions, most points have similar distances to each other
  * As we increase dimension, distances become "similar"
  * Why?
    * In high dimensions most of the volume is close to the corners
* However, real-world data is often far from uniformly random
  * Manifold hypothesis: real data lies close to the surface of a manifold

### Inductive Bias

* No free lunch
  * Consider the binary classification task
  * A learning algorithm can produce one of many possible functions as the classifier
  * The same algorithm cannot perform well for all possible classes of problems $f$
  * Each ML algorithm is biased to perform well on some class of problems
* Inductive bias
  * Learning algorithms make implicit assumptions (learning or inductive bias)
    * e.g. we are often biased towards the simplest explanations for our data
    * Occam's razor: between two explanations (models) we should prefer the simpler one
    * **For KNN, larger K creates a simpler model**
  * Why does it make sense for learning algorithms to be biased?
    * The world is not random
    * There are regularities and induction is possible
  * For example, KNN assumes nearby instances will have similar labels

### Summary

* Curse of dimensionality: exponentially more data needed in higher dimensions
  * Manifold hypothesis to the rescue!
* What we care about is the generalization of ML algorithms
  * Overfitting: good performance on the training set but poor performance on test data
  * Underfitting: poor performance even on the training set
* Estimate generalization error using a validation set or cross-validation (even better)
* No algorithm can perform well on all problems (no free lunch)
* Learning algorithms make assumptions about the data (inductive biases)
* Strength and correctness of those assumptions about the data affects the performance of the algorithms

