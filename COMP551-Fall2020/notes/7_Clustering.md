# Clustering

1. [Introduction](#introduction)
2. [K-Means](#k-means)
3. [K-Medoids](#k-medoids)
4. [DB-SCAN](#db-scan)
5. [Hierarchical Clustering](#hierarchical-clustering)
6. [Summary](#summary)

### Introduction

* Learning objectives
  * What is clustering and when is it useful?
  * What are the different types of clustering?
  * Some clustering methods:
    * K-means, K-medoids, DB-SCAN, hierarchical clustering
* Motivation
  * For many applications we want to classify the data without having any labels (unsupervised learning)
    * e.g. categories of shoppers or items based on shopping patterns
    * e.g. communities in a social network
* What is a cluster?
  * A subset of entities that are similar to each other and different from other entities
  * We can try and organize clustering methods based on:
    * Form of input data
    * Types of cluster/task
    * General methodology
* Types of input
  * Features $X \in \mathbb{R}^{N \times D}$
  * Pairwise distances of similarities $D \in \mathbb{R}^{N \times N}$
    * We can often produce similarities from features
    * Infeasible for large values of $D$
  * Attributed graphs
    * Node attribute is similar to feature in the first family
    * Edge attribute can represent similarity or distance
* Types of cluster/task
  * Partitioning/hard clusters
  * Soft/fuzzy membership
  * Overlapping
  * Hierarchical clustering with hard, soft or overlapping membership
    * It is customary to use a dendogram to represent hierarchical clustering
* Centroid methods
  * Identify centers, prototypes or exemplars of each cluster
  * K-means is an example of a centroid method

### K-Means

* Objective

  * Partition the data into $K$ clusters to minimize the sum of distance to the cluster mean/center

    * Equivalent to minimizing the within-cluster distances

  * Cost function:

    $J(\{r_{n,k}\}, \{\mu_k\}) = \sum_{n=1}^N \sum_{k=1}^K r_{n,k}||x^{(n)} - \mu_k ||^2_2$

  * $r_{n,k}$ represents cluster membership

    $ r_{n,k} = \begin{cases} 
        1 & \text{point n belongs to cluster k} \\
        0 & \text{otherwise}
     \end{cases}$

  * $N$ is the number of points and $K$ is the number of clusters

  * $\mu_k$ is the mean/center for cluster $k$

    $\mu_k = \frac{\sum_n x^{(n)} r_{n,k}}{\sum_n r_{n,k}}$

* We need to find cluster memberships and cluster centers

  * How can we minimize the cost?

* K-means clustering: algorithm

  * Idea: iteratively update cluster memberships and cluster centers

  * Pseudocode:

    > Start with some cluster centers $\{ \mu_k \}$
    >
    > Repeat until convergence:
    >
    > ​	Assign each point to the closest center: 
    >
    > ​		$r_{n,k} = 1 \text{ if } k = \text{argmin}_c ||x^{(n)} - \mu_c^t||^2_2, 0 \text{ otherwise}$
    >
    > ​	Re-calculate the center of the cluster:
    >
    > ​		$\mu_k = \frac{\sum_n x^{(n)} r_{n,k}}{\sum_n r_{n,k}}$

  * Since each iteration can only reduce the cost, the algorithm has to stop

* K-means clustering: complexity
  * Calculating the means of all clusters: $O(ND)$
  * Calculating distance of a node to center: $O(D)$
  * We do this for each point ($n$) and each center ($k$) so the total cost is $O(NKD)$
  
* K-means clustering: performance

  * K-means' alternating minimization finds a **local** minimum
  * Different initialization of cluster centers can give different clustering!
  * Even if the clusters are the same, we can swap cluster labels to get a different result

* K-means clustering: initialization

  * We could run K-means many times and pick the clustering with the lowest cost
  * We could use good heuristics for initialization (K-means++):
    * Pick a random datapoint to be the first center
    * Calculate the distance of each point to the nearest center $d_n$
    * Pick a new point as a new center with probability $p(n) = \frac{d_n^2}{\sum_i d_i^2}$
  * This often gives faster convergence to better solutions

* Application of K-means: vector quantization

  * Given a dataset $\mathcal{D}$ of vectors $x^{(n)} \in \mathbb{R}^D$
  * The required storage space is $O(NDC)$, where $C$ is the number of bits for a scalar
  * We can compress the data using K-means
    * Replace each data point with its cluster center
    * Store only cluster centers and indices: $O(KDC + N\text{log(K)})$
  * As we reduce the number of clusters, the data (e.g. image) gets more compressed

### K-Medoids

* K-means objective minimizes the squared Euclidean distance
* If we use Manhattan distance the minimizer is the median (K-medians)
* Problem: for general distance functions the minimizer doesn't have a closed form (computationally expensive)
* Solution: pick the cluster center from the points themselves (medoids)
* K-medoids objective:
  * $J(\{ r_{n,k} \}, \{ \mu_k \}) = \sum_{n=1}^N \sum_{k=1}^K r_{n,k} dist(x^{(n)}, \mu_k)$
  * $\mu_k \in \{ x^{(1)} ..., x^{(n)} \}$
* Algorithm:
  * Assign each point to the closest center
  * Set the point with the minimum overall distance to other points as the center of the cluster

* Example: finding key air-travel hubs (as medeoids)
* K-medoids also makes sense when the input is a graph (nodes become centers)

### DB-SCAN

* Density-based methods
  * Dense regions define clusters
  * A notable method is density-based spatial clustering of applications with noise (DB-SCAN)
* Points that have more than C neighbours in $\epsilon$-neighbourhood are called **core** points
* If we connect nearby core points we get a graph
* Connected components of the graph give us clusters
* All the other points are either:
  * $\epsilon$-close to the core, so belong to that cluster
  * Labelled as noise

### Hierarchical Clustering

* Hierarchical clustering heuristics

  * Bottom-up hierarchical clustering (**agglomerative clustering**)
    * Start from each item as its own cluster
    * Merge most similar clusters
  * Top-down hierarchical clustering (**divisive clustering**)
    * Start from having one bug cluster
    * At each iteration pick the "widest" cluster and split it (e.g. using K-means)
  * These methods often to not optimize a specific objective function (hence heuristics)
  * They are often too expensive for very large datasets

* Agglomerative clustering

  * Start from each item as its own cluster and merge most similar clusters

  * Pseudo-implementation:

    > Initialize cluster $C_n = \{ n\}, n \in \{1..., N\}$
    >
    > Initialize set of clusters available for merging $\mathcal{A} = \{1..., N \}$
    >
    > for $t=1...,$
    >
    > ​	Pick two clusters that are most similar $i,j \leftarrow \text{argmin}_{c,c'\in \mathcal{A}} \text{distance}(c, c')$
    >
    > ​	Merge them to get a new cluster $C_{t+N} \leftarrow C_i \cup C_j$
    >
    > ​	If $C_{t+N}$ contains all nodes, then we're done
    >
    > ​	Update clusters available for merging $\mathcal{A} \leftarrow \mathcal{A} \cup \{ t+N \}$
    >
    > ​	Calculate dissimilarities for the new cluster $\text{distance(t+N, n)} \forall n \in \mathcal{A}$

* How can we define the dissimilarity of two clusters?

  * **Single linkage**: distance between 2 closest members
    * $\text{distance}(c,c') = \text{min}_{i\in C_c, j \in C_{c'}}\text{distance} (i,j)$
    * Clusters can have members that are very far apart
  * **Complete linkage**: distance between furthest members
    * $\text{distance}(c,c') = \text{max}_{i\in C_c, j \in C_{c'}}\text{distance} (i,j)$
    * Clusters are more compact (members should all be close together)
  * **Average linkage**: average pairwise distance
    * $\text{distance}(c, c') = \frac{1}{|C_c||c_{c'}|}\sum_{i \in C_c, j \in C_{c'}} \text{distance}(i,j)$
    * A compromise of the two above

### Summary

* Clustering can help us explore and understand our data
* Input to clustering methods can be features, similarities or graphs
* Clusters can be flat, hierarchical, overlapping, fuzzy, etc.
* We saw several clustering methods
  * K-means and K-medoids define clusters using centers and distance to these centers
    * Optimization objective
    * Algorithm to perform the optimization
  * DB-SCAN as an example of density-based methods
  * Some heuristic hierarchical clustering methods
* Some notable methods not discussed here:
  * Popular community-ming methods such as modularity-optimizing methods
  * Spectral clustering
  * Probabilistic generative models of clusters

