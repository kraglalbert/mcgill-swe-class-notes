# Nearest Neighbours Classification and Regression

1. [Terminology and Notation](#terminology-and-notation)
2. [K-Nearest Neighbours Classification](#k-nearest-neighbours-classification)
3. [K-Nearest Neighbours Regression](#k-nearest-neighbours-regression)
4. [Summary](#summary)

### Terminology and Notation

* Input is equivalent to saying *features, predictors*
* Output is equivalent to saying *targets, labels, predictions*
* $x^{(N)}$: N instances in the dataset
  * $x^{(3)}$: 3rd instance/sample in the dataset
* Supervised learning (labelled data)
  * **Classification:** categorical/discrete output
  * **Regression:** continuous output
  * Example applications
    * Translations: dataset could be pairs of sentences <x, y> in different languages
    * Generating descriptions from images
    * Determining geo location from an image
* Unsupervised learning (unlabelled data)
  * Clustering
    * Similar to classification but labels/classes should be inferred (not given to the algorithm)
* Reinforcement learning
  * Weak supervision through reward signals
  * Sequential decision-making; biologically motivated

### K-Nearest Neighbours Classification

* Learning objectives:
  * Variations of K-nearest neighbours (KNN) for classification and regression
  * Computational complexity
  * Some pros and cons of KNN
  * What is a hyper-parameter?
* Nearest neighbours classifier
  * Training: do nothing (lazy learner, also a non-parametric model)
  * Test: predict labels by finding the most similar example(s) in the training set
* How to measure similarity?
  * Need a measure of distance (e.g. a metric)
  * For real-valued feature vectors:
    * Euclidean distance: $D_{Euclidean}(x, y) = \sqrt{\sum^D_{d=1} (x_d - y_d)^2}$
    * Manhattan distance: $D_{Manhattan}(x, y) = \sum^D_{d=1} | x_d - y_d |$
  * For discrete feature vectors:
    * Hamming distance: $D_{Hamming}(x, y) = \sum^D_{d=1} \mathbb{I} (x_d \neq y_d)$
* $\mathbb{I}$ is called the **indicator function**
  * $ \mathbb{I}_A(x) = \begin{cases} 
         1 & x \in A \\
         0 & x \notin A
      \end{cases}$
* Iris dataset
  * One of the most famous datasets in statistics
    * N = 150 instances of flowers
    * D = 4 feature
    * C = 3 classes
* Decision boundary
  * A classifier defines a decision boundary in the input space
    * All points in a region will be classified as the same class
* Higher dimensions: digits dataset
  * Input: $x^{(n)} \in \{ 0, ... 255 \}^{28 \times 28}$
    * $28 \times 28$ is the size of the input image in pixels
  * Label: $y^{(n)} \in \{ 0, ... 9\}$
    * Each image is a digit from 0 to 9
  * How do we classify a $28 \times 28$ matrix?
    * Convert to a vector of length $28 \times 28 = 784$ 
    * Each image is now a point in 784-dimensional space
    * Now it's easy to calculate the distance between images
* Can we make predictions more robust?
  * Find K-nearest neighbours instead of just the 1 nearest
    * Make prediction based on the majority
* Choosing a value for K
  * K is a **hyper-parameter** of the model
  * In constrast to normal parameters, hyper-parameters are not learning during the usual training procedure
* Computational complexity
  * For a single test query: $O(ND + NK)$
  * For each point in the training set calculate the distance in $O(D)$ for a total of $O(ND)$
  * Find the K points with smallest distances in $O(NK)$
  * In practice efficient implementations exist using KD-tree/ball tree
    * Partition the space based on a tree structure; for a query point only search the relevant part of the space
* Scaling and importance of features
  * Scaling of features affects distances and nearest neighbours
  * We want important features to maximally affect our classification
    * These features should have larger scale
  * Noisy and irrelevant features should have a small scale
  * **KNN is not adaptive to feature scaling and it is sensitive to noisy features**

### K-Nearest Neighbours Regression

* So far our task was *classification*
  * Use majority vote of neighbours for prediction at test time
* Switching to regression requires minimal changes
  * Use the mean (or median) of K-nearest neighbours' targets
* Some variations
  * In **weighted-KNN** the neighbours are weighted inversely proportional to their distance
    * For classification the votes are weighted
    * For regression calculate the weighted average
  * In **fixed-radius nearest neighbours** all neighbours in a fixed radius are considered
    * In dense neighbourhoods we get more neighbours

### Summary

* KNN performs classification/regression by finding similar instances in the training set
  * Need a notion of distance
  * How many neighbours to consider (i.e. fixed K or fixed radius)
  * How to weigh the neighbours
* KNN is a non-parametric method and a lazy learner
  * Non-parametric: our model has no parameters 
    * The training data points are model parameters
  * Lazy because we don't do anything during the training 
    * Test-time complexity grows with the size of the data
* KNN is sensitive to feature scaling and noise