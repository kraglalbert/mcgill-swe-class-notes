# Probability Review

1. [Basic Probability](#basic-probability)
2. [Additional Content](#additional-content)

### Basic Probability

* In general, we perform an experiment which can have a number of outcomes

* The **sample space** is the set of all possible outcomes of the experiment

  * Referred to as $S$

  * Example: a coin tossed three times

    $S = \{HHH, HHT, HTH, THH, HTT, THT, TTH, TTT\}$

* Sometimes all possible outcomes are equally likely

  * Don't always make this assumption!

* An **event** is a subset of the sample space

  * Example: $A$ is the event of more heads than tails

    $A = \{HHH, HHT, HTH, THH\}$

  * An event is **simple** if it only has a single outcome; otherwise it is **compound**

* If $E$ is an event, then $P(E) = \frac{|E|}{|S|}$

* Kolmogorov's Axioms

  * Axiom 1: For any event $A$, $P(A) \geq 0$

  * Axiom 2: $P(S) = 1$

  * Axiom 3: If $A_1, A_2, ...$ are pairwise disjoint, then:

    $P(A_1 \cup A_2 \cup ...) = P(A_1) + P(A_2) +...$

* $P(A') = 1 - P(A)$

* $P(A \cup B) = P(A) + P(B) - P(A \cap B)$

* $P(A \cup B \cup C) = P(A) + P(B) + P(C) - P(A \cap B) - P(A \cap C) - P(B \cap C) + P(A \cap B \cap C)$

* Two events are **independent** if $P(A \cap B) = P(A)P(B)$

* $P(A|B) = \frac{P(A \cap B)}{P(B)}$

* Bayes' Theorem: $P(A|B) = \frac{P(B|A)P(A)}{P(B)}$

### Additional Content

* Random variables

  * A function defined on a sample space

  * Can be continuous or discrete

  * Example (continuous RV):

    > Select a random student from the class and measure his/her height in centimeters.
    >
    > * Sample space: set of students $S$
    >
    > * Random variable: height
    >
    > * Mapping from sample space to real numbers: $h(S)$

* Probability mass function

  * Let $F$ be a discrete RV

  * For a given value $a$, what is the probability that $F$ takes the value $a$?

  * Example:

    > Toss a fair coin 3 times. RV $X$ gives the number of heads. Possible values: 0, 1, 2, or 3.
    >
    > * $P(0) = 1/8$
    > * $P(1) = 3/8$
    > * $P(2) = 3/8$
    > * $P(3) = 1/8$
    >
    > The sum of all probabilities must always be 1!

* Expected value

  * $E(X) = \sum_{i=1}^{n} a_iP(x=a_i)$

  * We multiply each value of $X$ by the probability of $X$ taking on that value, and then sum these terms

  * If each of the values is equally likely, then:

    $E(X) = \frac{1}{n}(a_1 + a_2 + a_3 +... a_n)$

* Variance

  * $Var(X) = E(X^2) - E(X)^2$

  * Example:

    > Take the previous coin tossing example. What is the expected value and variance?
    >
    > Answer:
    >
    > * $E(X) = \frac{3}{2}$
    > * $Var(X) = \frac{3}{4}$

* Cumulative distribution function

  * Let $X$ be a RV taking values $a_1, a_2...$

  * We assume that these are arranged in ascending order

  * The CDF of $X$ is given by:

    $F_X(a_i) = P(X \leq a_i)$

* Bernoulli Random Variable
  * The simplest (discrete) RV of all
  * Can take on two values: 0 and 1
  * $X$ can take on a value of 1 with pobability $p$ and a value of 0 with probability $(1-p)$
  * Expected value: $p$
  * Variance: $p(1-p)$
* Binomial Random Variable
  * With a Bernoulli random variable, we consider a value of 1 a "success"
  * A Binomial RV asks the question: "what is the probability of getting $k$ successes?"
  * $P(X = k) = \binom{n}{k}p^k(1-p)^{n-k}$
  * $E(X) = np$
  * $Var(X) = np(1-p)$

* Poisson Random Variable

  * Written as $Poisson(\lambda)$

  * Suppose "incidents" occurring at random times, but at a steady rate overall; average number $\lambda$ in a given interval is constant

  * Example:

    > The PMF of a $Poisson(\lambda)$ RV $X$ is given by:
    >
    > $P(X=k) = (\frac{\lambda^k}{k!})e^{-\lambda}$

  * $E(X) = Var(X) = \lambda$

* Continuous Random Variable

  * Critical property: $P(X=a) = 0$
  * We cannot use probability mass functions for continuous RVs, since they would give us no information
  * Use cumulative distribution functions instead
    * $F_X(x)$, where $X$ is the random variable
    * $F_Y(x)$ is different than the above, but $F_X(y)$ is the same
  * $P(x < X < y) = F_X(y) - F_X(x)$

* Probability density function

  * Obtained by differentiating the CDF

    $f_X(x) = \frac{d}{dx}F_X(x)$

    $F_X(x) = \int_{-\infin}^{x} f_x(t)dt$

    Note that $t$ is a dummy variable for integration

  * $P(a < X < b) = F_X(b) - F_X(a) = \int_{a}^{b} f_x(t)dt$

  * $E(X) = \int_{-\infin}^{\infin} xf_x(x)dx$

  * $Var(X) = E(X^2) - E(X)^2$

    $E(X^2) = \int_{-\infin}^{\infin}x^2f_x(x)dx$

  * **Support** of a continuous RV is the smallest interval containing all values $x$ where:

    $f_X(x) > 0$

* Uniform Random Variable

  * Let $a, b$ be real numbers with $a<b$

  * A uniform RV in the interval $[a,b]$ is equally likely anywhere in the interval $[a,b]$

  * The probability density function is:

    $f_X(x) = \frac{1}{b-a}$ if $a \leq x \leq b$

    $f_X(x) = 0$ otherwise

* Exponential Random Variable

  * We have events occurring randomly at a constant arrival rate ($\lambda$ per unit time)

  * The Poisson RV, which is discrete, counts how many events will occur in the next unit of time

  * The Exponential RV, which is continuous, measures exactly *how long before the next event occurs*

  * The PDF is:

    $f_X(x) = \lambda e^{-\lambda x}$ if $x \geq 0$ (equal to $0$ otherwise)

  * The CDF is:

    $F_X(x) = 1-e^{-\lambda x}$ if $x \geq 0$ (equal to $0$ otherwise)



