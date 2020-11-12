# Decision Trees

1. [Decision Trees Intro](#decision-trees-intro)
2. [Cost Functions](#cost-functions)
3. [Entropy](#entropy)
4. [Summary](#summary)

### Decision Trees Intro

* Learning objectives:
  * Decision trees
    * How does it model the data?
    * How to specify the best model using a cost function
    * How the cost function is optimized
* Decision trees: motivation
  * Pros
    * Decision trees are interpretable
    * They are not very sensitive to outliers
    * Do not need data normalization
  * Cons
    * They could easily overfit and they are unstable
* Notation overview
  * $x, y$ denote the input and labels
  * $x = [x_1, x_2, ... x_D]$
    * We use $D$ to denote the number of features (dimensionality of the input space)
  * $\mathcal{D} = \{ (x^{(1)}, y^{(1)}), ... (x^{(N)}, y^{(N)})\}$
    * This is our dataset; we use $N$ to denote the size of the dataset and $n$ for indexing
  * For classification problems, we use $C$ for the number of classes $y \in \{1, ... C\}$
* Decision trees: idea
  * Divide the input space into regions $\mathbb{R}_1, ...\mathbb{R}_K$ using a tree structure
  * Assign a prediction label to each region
    * For classification this is the class label
    * For regression this is a real scalar or vector
  * The model: $f(x) = \sum_k w_k \mathbb{I}(x \in \mathbb{R}_k)$
    * $w_k$ is the label
* How do we build the regions and the tree?
  * Split regions successively based on the value of a single variable
  * Each region is a set of conditions, e.g. $\mathbb{R}_2 = \{x_1 \leq t_1, x_2 \leq t_4\}$
* Possible tests
  * What are all the possible tests? Which test do we choose next?
  * Continuous features
    * All the values that appear in the dataset can be used to split
  * Categorical features
    * If a feature can take C values $x_i \in \{ 1, ... C\}$, convert that feature into C binary features (one-hot encoding)
    * Split based on the value of a binary feature
  * Alternatives
    * Multi-way split: can lead to regions with few datapoints
    * Binary splits that produce balanced subsets 

 ### Cost Functions

* ML algorithms usually minimize a cost function or maximize an objective function

* We want to find a decision tree minimizing the following cost function

* Regression cost

  * First calculate the cost per region $\mathbb{R}_k$

  * We predict $w_k = mean(y^{(n)} | x^{(n)} \in \mathbb{R}_k)$ for region $\mathbb{R}_k$

  * $cost(\mathbb{R}_k, \mathcal{D}) = \frac{1}{N_k} \sum_{x^{(n)} \in \mathbb{R_k}}(y^{(n)} - w_k)^2$

    * This is mean squared error (MSE)
    * $y^{(n)}$ is the actual label; $w_k$ is the prediction
    * $N_k$ is the number of instances in region $k$

  * Total cost is the normalized sum over all regions:

    $cost(\mathcal{D}) = \sum_k \frac{N_k}{N} cost(\mathbb{R}_k, \mathcal{D})$

* Classification cost
  * Again, calculate the cost per region $\mathbb{R}_k$
  * For each region we predict the most frequent label $w_k = mode(y^{(n)} | x^{(n)} \in \mathbb{R}_k)$
  * $cost(\mathbb{R}_k, \mathcal{D}) = \frac{1}{N_k} \sum_{x^{(n)} \in \mathbb{R_k}} \mathbb{I}(y^{(n)} \neq w_k)$
    * This is the misclassification rate
  * Total cost is again the normalized sum
* Problem: it is sometimes possible to build a tree with zero cost
  * Build a large tree with each instance having its own region (overfitting)
  * Solution: find a decision tree with a most K tests that minimizes the cost function
    * K tests => K internal nodes in the binary tree => K+1 leaves (regions)
* Search space
  * The number of full binary trees with K+1 leaves is the Catalan number (exponential in K)
  * We also have a choice of feature $x_d$ for each of K internal nodes
    * For each feature there are also different choices of splitting
  * Finding an optimal decision tree is an **NP-hard** combinatorial optimization problem
* Greedy heuristic
  * Finding the optimal tree is too difficult; instead use a greedy heuristic to find a "good" tree
  * Recursively split the regions based on a greedy choice of the next test
  * End the recursion if it is not worth splitting
  * The split is greedy because it looks one step ahead
    * This may not lead to the lowest overall cost

* Stopping the recursion

  * If we stop when $\mathbb{R}_{node}$ has zero cost, we may overfit

  * Possible heuristics for stopping the splitting

    * Reached a desired depth

    * Number of examples in $\mathbb{R}_{left}$ or $\mathbb{R}_{right}$

    * $w_k$ is a good approximation, the cost is small enough

    * Reduction in cost by splitting is below a certain threshold

      $cost(\mathbb{R_{node}}, \mathcal{D}) - (\frac{N_{left}}{N_{node}} cost(\mathbb{R_{left}}, \mathcal{D}) + \frac{N_{right}}{N_{node}} cost(\mathbb{R_{right}}, \mathcal{D}))$

* Revisiting the classification cost
  * Ideally we want to optimize the misclassification rate
    * This may not be the optimal cost for each step of the greedy heuristic

### Entropy

* Entropy is the expected amount of information in observing a random variable $Y$
* $H(Y) = -\sum_{c=1}^C p(Y=c) log(p(Y=c))$
* $log(p(Y=c))$ is the amount of information gained in observing $c$
  * Zero information if $p(c) = 1$
  * Less probable events are more informative
* A uniform distribution has the highest entropy 
  * $H(Y) = -\sum_{c=1}^C \frac{1}{C}log\frac{1}{C} = logC$
* Mutual information
  * For two random variables $T, Y$ mutual information is the amount of information $T$ conveys about $Y$
    * Change in the entropy of $Y$ after observing the value of $T$
  * $I(T, Y) = H(Y) - H(Y|T) = \sum_l \sum_c p(Y = C, T = l)log\frac{l(Y=C, t=l)}{p(Y=c)p(T=l)}$
    * This is symmetric with respect to $T, Y$, i.e. $I(T, Y) = I(Y, T)$
  * Conditional entropy: $H(Y|T) = \sum_{l=1}^L p(T=L)H(X|T=L)$
  * Mutual information is always positive, but zero if and only if $Y$ and $T$ are independent
* Misclassification cost: $cost(\mathbb{R}_k, \mathcal{D}) = 1 - p_k(w_k)$
  * The most probably class $w_k = \text{argmax}_c p_k(c)$
* We can split based on **entropy cost**, i.e. choose the split with lowest entropy
  * By using entropy as our cost, we are choosing the test which is maximally informative about labels
* Gini index
  * Another cost for selecting the *test* in classification
  * It is the expected error rate
  * $cost(\mathbb{R}_k, \mathcal{D}) = \sum_{c=1}^C p(c)(1-p(c) = 1 - \sum_{c=1}^C p(c )^2$
    * $p(c ) $ is the probability of class c
    * $1-p(c )$ is the probability of error   

### Summary

* Model: divide the input into axis-aligned regions
* Cost: for regression and classification
* Optimization:
  * NP-hard, so use greedy heuristic instead
* Adjust the cost for the heuristic
  * Using entropy (relation to mutual information maximization)
  * Using Gini index
* There are variations on decision tree heuristics 
  * What we discussed here is called Classification and Regression Trees (CART)