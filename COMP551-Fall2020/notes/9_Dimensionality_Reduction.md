# Dimensionality Reduction Using PCA

1. [Introduction](#introduction)
2. [Principal Component Analysis](#principal-component-analysis)
3. [Singular Value Decomposition](#singular-value-decomposition)
4. [Summary](#summary)

### Introduction

* Learning objectives
  * What is dimensionality reduction? What is it useful for?
  * Linear dimensionality reduction:
    * Principal Component Analysis (PCA)
    * Relation to Singular Value Decomposition
* Motivation
  * Scenario: we are given high-dimensional data and asked to make sense of it
  * Real-world data is often high-dimensional
    * We can't visualize beyond 3D
    * Features may not have any semantics (e.g. value of the pixel vs. happy/sad)
    * Processing and storage is costly
    * Many features may not vary much in our dataset (e.g. background pixels in face images)
  * Dimensionality reduction: faithfully represent the data in low dimensions
    * We can often do this with real-world data (manifold hypothesis)
* How can we represent the data in low dimensions?
  * Learn a mapping between coordinates at low-dimension and high-dimension data
  * Some methods give this mapping in both directions and some only in one direction

### Principal Component Analysis

* PCA is a linear dimensionality reduction method
* Say the original data is $x^{(n)} \in \mathbb{R}^3$ and we want to map it to $z^{(n)} \in \mathbb{R}^2$
  * Then we say $Q \in \mathbb{R}^{3 \times 2}$ going from 3D to 2D
  * $Q^T$ describes the opposite direction
  * $Q^T Q = I$

* The **faithfullness** of the low-dimension representation is measured by the reconstruction error

  * $\text{min}_Q \sum_n ||x^{(n)} -x^{(n)^T} QQ^T ||^2_2$
  * $z^{(n)} = x^{(n)^T} Q$

* Strategy: find a $D \times D$ matrix $Q$ and only use $D'$ columns

  * Since $Q$ is orthogonal we can think of it as a change of coordinates
  * We want to change coordinates such that coordinates $1,2...,D'$ best explain the data for any given $D'$
  * In other words, find a change of coordinates using the orthonormal matrix $Q$
  * The **first new coordinate has maximum variance** (lowest reconstruction error/describes the data best)
  * Second new coordinates has the next largest variance, etc.

* Covariance matrix

  * Find a change of coordinate using orthonormal matrix

  * Projection of the whole dataset is $z_1 = Xq_1$

  * Assuming features have zero mean, maximize the variance of the projection $\frac{1}{N}z_1^T z_1$

    $\text{max}_{q_1} \frac{1}{N}z_1^T z_1 = \text{max}_{q_1} q_1 \Sigma q_1^T$

  * Recall: $\Sigma = \frac{1}{N} X^T X = \frac{1}{N} \sum_n (x^{(n)} - 0)(x^{(n)} - 0)^T$
    * Because the mean is zero

* Eigenvalue decomposition

  * The covariance matrix is symmetric and positive semi-definite
  * Any symmetric matrix has the following decomposition: $\Sigma = Q \Lambda Q^T$
  * Each column in $Q$ is an eigenvector since it's orthonormal
  * $\Lambda$ is diagonal and the corresponding eigenvalues are on the diagonal; positive semi-definiteness means the eigenvalues are non-negative

* Principal directions

  * The first new coordinate has maximum variance, so:

    $q_1^* = \text{argmax}_{q_1} q_1^T \Sigma q_1, s.t. ||q_1|| = 1$

    $\text{max}_{q_1} q_1^T Q \Lambda Q^T q_1 = \lambda_1$

  * The maximizing direction is the eigenvector with the largest eigenvalue (first column of $Q$)
  * Therefore for PCA we need to find the eigenvectors of the covariance matrix

* Reducing dimensionality

  * Projection into the principal direction $q_i$ is given by $Xq_i$

### Singular Value Decomposition

* An alternative way to do PCA without using the covariance matrix
* Any $N \times D$ real matrix has the following decomposition:
  * $X = USV^T$
  * $X$ is a $N \times D$ matrix
  * $U$ is a $N \times N$ orthogonal matrix consisting of left singular vectors
  * $S$ is a $N \times D$ rectangular diagonal matrix, where each diagonal value is $\geq 0$
  * $V^T$ is a $D \times D$ orthogonal matrix consisting of right singular vectors
* Eigenvalues of $\Sigma$ are the right singular vectors of $X$
* Picking the number of principal components
  * This is a hyper-parameter
  * Each new principal direction explains some variance in the data

### Summary

* Dimensionality reduction helps us:
  * Visualize our data
  * Compress it
  * Simplify the computational need of further analysis
  * Can also be used for anomaly detection, though this was not discussed
* PCA is a linear dimensionality reduction method
  * Projects the data to a linear space (spanned by $D'$ principal directions)
    * The directions are the eigenvectors of the covariance matrix
    * The projection has maximum variance/minimum reconsutrction error
    * Eigenvalues tell us about the contribution of each new principal direction
  * PCA using SVD

