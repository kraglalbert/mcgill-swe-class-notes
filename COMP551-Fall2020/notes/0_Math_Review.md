# Math Review

1. [Probability](#probability)
2. [Linear Algebra](#linear-algebra)

### Probability

* Conditional probability and Bayes' rule

  $p(A|B) = \frac{p(A \cap B)}{p(B)}$

  $p(B|A) = \frac{p(B \cap A)}{p(A)} = \frac{p(A \cap B)}{p(A)} = \frac{p(B)p(A|B)}{p(A)}$

  $p(A|B,C) = \frac{p(B|A,C)p(A|C)}{p(B|C)}$

* Law of total probability

  * Let $B_1..., B_n$ be $n$ disjoint events whose union is the entire sample space. Then for any event $A$:

    $p(A) = \sum_{i=1}^n p(A \cap B_i) = \sum_{i=1}^n p(A|B_i)P(B_i)$

* Chain rule

  * For any $n$ events $A_1..., A_n$ the joint probability can be expressed as a product of conditionals:

    $p(A_1 \cap A_2 ... \cap A_n) = p(A_1)p(A_2 | A_1)p(A_3|A_2 \cap A_1)...$

* Independence

  * Two events are independent if $p(A \cap B) = p(A)p(B)$
  * Denoted as $A \perp B$
  * If  $A \perp B$, then $p(A|B) = p(A)$
  * If two events are independent, observing one events does **not** change the probability that the other event occurs

* Random variables

  * A random variable $X$ maps outcomes to real values
  * We can represent the possible values of $X$ as $\text{Val}(X) \sube \mathbb{R}$
  * $X = k$ is the event that random variable $X$ takes on value $k$

* Discrete random variables

  * $\text{Val}(X)$ is a set
  * $p(X=k)$ can be non-zero

* Continuous random variables

  * $\text{Val}(X)$ is a range
  * $p(X=k) = 0$ for all $k$
  * $p(a < X < b)$ can be non-zero

* Probability mass function (PMF)

  * Given a discrete random variable $X$, a PMF maps values of $X$ to probabilities
  * $p_X(k) = p(X = k)$

* Cumulative distribution function (CDF)

  * A CDF maps a continuous random variable to a probability $\mathbb{R} \rightarrow [0, 1]$
  * $F_X(k) = p(X < k)$
  * A CDF must fulfill the following:
    * $lim_{k \rightarrow -\infin} F_X(k) = 0$
    * $lim_{k \rightarrow \infin} F_X(k) = 1$
  * A CDF must be non-decreasing: if $a < b$ then $F_X(a) < F_X(b)$

  * $p(a < X < b) = F_X(b) - F_X(a)$

* Probability density function (PDF)

  * The PDF of a continuous random variable is the derivative of the CDF
    * $f_X(x) = \frac{d}{dx}F_X(x)$
    * $p(a < X < b) = F_X(b) - F_X(a) = \int_a^b f_X(x) dx$
  * For all real numbers $x$, $f_X(x) \geq 0$

* Expectation

  * Let $g$ be an abritary real-valued function

  * If $X$ is a discrete random variable with PMF $p_X$:

    $\mathbb{E}[g(x)] = \sum_{x \in \text{Val}(X)} g(x)p_X(x)$

  * If $X$ is a continuous random variable with PDF $f_X$:

    $\mathbb{E}[g(x)] = \int_{-\infin}^{\infin} g(x)f_X(x)dx$

  * Intuitively the expected value is a weighted average of the values of $g(x)$, weighted by the probability of $x$

* Properties of expectation

  * For any constant $a \in \mathbb{R}$ and arbitrary real function $f$:
    * $\mathbb{E}[a] = a$
    * $\mathbb{E}[af(X)] = a \mathbb{E}[f(X)]$
  * Linearity of expectation
    * $\mathbb{E}[\sum_{i=1}^n f_i(X)] = \sum_{i=1}^n \mathbb{E}[f_i(X)]$
  * Law of total expectation
    * $\mathbb{E}[\mathbb{E}[X|Y]] = \mathbb{E}[X]$

* Variance

  * The variance of a random variable $X$ measures how concentrated the distribution of $X$ is around its mean
    * $\text{Var}(X) = \mathbb{E}[(X-\mathbb{E}[X])^2] = \mathbb{E}[X^2] - \mathbb{E}[X]^2$
  * For a constant $a \in \mathbb{R}$:
    * $\text{Var}(a) = 0$
    * $\text{Var}(af(x)) = a^2\text{Var}(f(x))$

* Joint and marginal distributions

  * Joint PMF for discrete random variables $X, Y$:

    $p_{XY}(x, y) = p(X=x, Y=y)$

  * Marginal PMF of $X$, given the joint PMF:

    $p_X(x) = \sum_y p_{XY}(x, y)$

  * Joint PDF for continuous $X, Y$:

    $f_XY(x, y) = \frac{\delta^2F_{XY}(x, y)}{\delta_X \delta_Y}$

  * Marginal PDF of $X$:

    $f_X(x) = \int_{-\infin}^{\infin} f_{XY}(x,y)dy$

* Covariance

  * Measures how much one random variable's value tends to move with another random variable's value

    $\text{Cov}(X,Y) = \mathbb{E}[XY] - \mathbb{E}[X]\mathbb{E}[Y]$

* Random vectors (vs. random variables)

  * Given $n$ random variables $X_1...,X_n$ we can define a random vector $X$ such that:

    $X = \begin{bmatrix} X_1 \\ X_2 \\ ... \\ X_n\end{bmatrix}$

* Covariance matrices

  * For a random vector $X \in \mathbb{R}^n$, we define its covariance matrix $\Sigma$ as the $n \times n$ matrix whose $i, j$ entry contains the covariance between $X_i$ and $X_j$

  * $\Sigma = \mathbb{E}[(X - \mathbb{E}[X])(X-\mathbb{E}[X])^T]$

  * If $X_i \perp X_j$ for all $i,j$ then:

    $\Sigma = \text{diag}(\text{Var}(X_1)..., \text{Var}(X_n))$

### Linear Algebra

