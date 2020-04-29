# Systems

1. [Introduction](#introduction)
2. [Differential Equations Representation](#differential-equations-representation)
3. [Impulse Response](#impulse-response)
4. [Laplace Transform](#laplace-transform)
5. [Discrete-Time Systems](#discrete-time-systems)

### Introduction

* A system receives input signals, processes them and then produces an output signal(s)

* Systems are made from a combination of hardware and software algorithms

* Continuous-time system: the input signal and output signal are both continuous time

* Discrete-time system: the input signal and output signal are both discrete time

* Some systems belong to neither category

* A linear system satisfies both of the following properties:

  * $H(k(f(t))) = k(H(f(t)))$ for real or complex $k$
  * $H(f_1(t) + f_2(t)) = H(f_1(t)) + H(f_2(t))$

* The above two properties can be combined into a single superposition property:

  * A system if linear if:

    $a_1H(x_1(t)) + a_2H(x_2(t)) = H(a_1x_1(t) + a_2x_2(t))$

* **Time-invariant system:** any time-shift of an input will produce an identical output in every way except that it is shifted in time

  * e.g. $H(x(t)) = tx(t)$ is not time invariant because $H(x(t-T)) = tx(t-T) \neq (t-T)x(t-T)$

* Causal vs. noncausal

  * Casual system: the output $y(t) = H(f(t))$ at time $t_0$ only depends on values of $f(t)$ for $t \leq t_0$

* Stable system

  * Several definitions, but we focus on BIBO (Bounded Input Bounded Output) stability

  * A system is stable if the output is always bounded for any bounded input

  * For example:

    $|x(t)| \leq M_x < \infin \rightarrow |y(t)| \leq M_y < \infin$

* Cascaded systems (systems in series):
  * If the systems are LTI (Linear Time-Invariant), then their order can be exchanged without affecting the output

### Differential Equations Representation

* We are often interested in systems that can be expressed as a differential equation

  * Example:

    $\frac{dy(t)}{dt} + 3y(t) = x(t)$

* We focus on linear systems that can be described by constant coefficient linear differential equations

  $a_N\frac{d^Ny(t)}{dt^N} + a_{N-1}\frac{d^{N-1}y(t)}{dt^{N-1}} + ... a_0y(t) = b_M\frac{d^{M}x(t)}{dt^M} + b_{M-1}\frac{d^{M-1}x(t)}{dt^{M-1}} + ... b_0x(t)$

* Is this system linear? 
  * $\frac{dy}{dt} + 3y(t) = x(t)$

* A system's output for $t \geq 0$ is the result of two independent causes:
  * Initial conditions of the system (or the system state) at $t = 0$
  * Input $x(t)$ for $t \geq 0$
* In a linear system, the output is the sum of the two components
  * **Zero-input response** results only from initial conditions at $t = 0$. We have $x(t) = 0$ for $t \geq 0$
  * **Zero-state response** results only from input $x(t)$ for $t \geq 0$ when initial conditions are assumed to be zero

* When all appropriate initial conditions are zero, the system is said to be in zero state
  * The system output is zero when the input is zero only when the system is in zero state

### Impulse Response

* What is the output when $x(t)$ is applied to an LTI system $H$?

* The **impulse response** $h(t, \tau)$ of the system is the response when an impulse is applied at time $\tau,$ i.e. the input is $\delta(t-\tau)$

* System the system is time-invariant, we have $h(t, \tau) = h(t - \tau)$ where $h(t) = h(t, 0)$ is the response to a delta input at time zero

* Casual systems

  * The response of a causal system cannot begin before its input begins
  * Therefore, the causal's system's input response $h(t)$ has to be zero for $t < 0$
  * Thus for a causal system $h(t)$ is a causal signal

* BIBO stability

  * An input $x(t)$ is bounded if there is a constant $K$ such that $|x(t)| < K < \infin$ for all $t$

  * The output can be expressed as:

    $y(t) = h(t) \ast x(t) = \int_{-\infin}^{\infin} h(\tau)x(t - \tau)d\tau$

  * The system is BIBO stable if:

    $\int_{-\infin}^{\infin} |h(\tau)|d\tau < \infin$

### Laplace Transform

* The Laplace is a generalization of the continuous-time Fourier transform

* Why is this used?

  * The CTFT does not converge nor exist for many important signals
  * The Laplace transform is notationally cleaner

* CTFT uses $e^{j\omega t}$ with purely imaginary parameters

* The Laplace transform uses $e^{st}$ when $s = \sigma j\omega$

* Signals are analyzed in terms of exponentially-weighted sinusoids

* Laplace transform pairs:

  * Laplace transform:

    $X(s) = \int_{-\infin}^{\infin} x(t)e^{-st}dt$

  * Inverse Laplace transform:

    $x(t) = \frac{1}{2\pi j} \int^{c+j\infin}_{c-j\infin} X(s)e^{st} ds$

* We write $X(s) = L[x(t)]$ and $L^{-1}[X(s)]$

  * $x(t) \Leftrightarrow X(s)$

* Region of convergence

  * The set of values of $s$ for which $\int^{\infin}_{-\infin} x(t)e^{-st}dt$ converges
  * Defines a region in the complex plane

  * Example:

    * Let $x(t) = e^{-at}u(t)$. Then the Laplace transform is:

      $X(s) = \int^{\infin}_{0} e^{-at}e^{-st}dt$

      $= -\frac{1}{s+a}e^{-(s+a)t}$

    * $s$ is complex, so as $t \rightarrow \infin$, the term $e^{-(s+a)t}$ may not vanish
    * The ROC for this is $real(s) > -a$ (since this makes the exponent negative, making the exponential converge to zero)
    * The Laplace transform does not exist outside of this region

* Unilateral Laplace transform

  * Restricted to causal signals, but has a unique inverse

    $X(s) = \int^{\infin}_{0^{-}} x(t)e^{-st}dt$

* We can use Laplace transform tables and properties of the Laplace transform to find inverse transforms

### Discrete-Time Systems

* DT systems take a DT signal as input and produce a DT signal as output