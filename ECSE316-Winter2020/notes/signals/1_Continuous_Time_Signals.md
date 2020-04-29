# Continuous Time Signals

1. [Introduction](#introduction)
2. [Signal Energy](#signal-energy)
3. [Important Signals](#important-signals)
4. [Fourier Analysis](#fourier-analysis)
5. [Fourier Transform](#fourier-transform)

### Introduction

* Discrete vs. continuous

  * Determined by the values along the x-axis (time)
  * If continuous, then the signal is continuous (i.e. there is a value for all real numbers along the time axis)
  * If discrete, then the signal is discrete (values occur at equally-spaced intervals along the time axis)

* Analog vs. digital

  * Determined by the values along the y-axis
  * Analog: continuous values along y-axis
  * Digital: discrete values along y-axis

* Periodic signals

  * Signal repeats with some period $T$

  * Must satisfy the property:

    $f(t) = f(t+T)$

  * **Fundamental period:** smallest value of $T$ where the above is true
  * If not period, a signal is said to be **aperiodic**

* Causal signals

  * The signal has only zero values for all negative time

* Anticausal signals

  * The signal has only zero values for all positive time

* Noncausal signals

  * Nonzero values in both positive and negative time

* Odd and even signals

  * Odd:
    * Satisfy $f(t) = -f(-t)$
  * Even:
    * Symmetrical around the y-axis
    * Satisfy $f(t) = f(-t)$

* Any signal can be written as a combination of an odd and even signal

  $f(t) = \frac{1}{2}(f(t) + f(-t)) + \frac{1}{2}(f(t) - f(-t))$

### Signal Energy

* Most common definition of the energy on a continuous-time signal defined on $\R$ is the $L_2$ norm

* Defined as:

  $||f||_2 = (\int_{-\infin}^{\infin} |f(t)|^2 dt)^{1/2}$

* An **energy signal** is a signal with finite energy

* Example

  * Consider the signal

    $f(t) = \begin{cases} 
    \frac{1}{t} & t\geq 1 \\
    0 & t < 1
    \end{cases}$

  * The $L_2$ energy is:

    $||f||_2 = (\int_{-\infin}^{\infin} |f(t)|^2 dt)^{1/2}$

    $= (\int_{1}^{\infin} \frac{1}{t^2} dt)^{1/2}$

    $=[-\frac{1}{t}]^{\infin}_{1} = 1$

* For a discrete-time signal, the energy is:

  $||x[n]||_2 = (\sum_{n=-\infin}^{\infin} |x[n]|^2)^{1/2}$

### Important Signals

* Sinusoid

  * Real-valued: $Acos(\omega t + \phi)$
  * $A$ is the amplitude, $\omega$ is the frequency, and $\phi$ is the phase
  * The period of the signal is $T = \frac{2\pi}{\omega}$

* Complex exponential

  * General (continuous) form:

    $Ae^{st} = Ae^{\sigma t + j\omega t} = Ae^{\sigma t}(cos(\omega t)+jsin(\omega t))$

  * Euler's formula:

    $e^{jt} = cos(t) + jsin(t)$

    $cos(\omega t) = \frac{1}{2}e^{jwt} + \frac{1}{2}e^{-jwt}$

    $sin(\omega t) = \frac{1}{2j}e^{jwt} - \frac{1}{2j}e^{-jwt}$

* Dirac delta function

  * Unit impulse function: $\delta(t)$

  * Infinite height but infinitesimal width; integrates to 1

  * Properties:

    * $\delta(at) = \frac{1}{a}\delta(t)$
    * $\delta(t) = \delta(-t)$
    * $f(t)\delta(t) = f(0)\delta(t)$

  * Sifting property

    $\int^{\infin}_{-\infin} f(t)\delta(t)dt$ 

    $= \int^{\infin}_{-\infin} f(0)\delta(t)dt$ 

    $= f(0) \int^{\infin}_{-\infin}\delta(t)dt = f(0)$

* Unit step function

  * Defined as follows:

    $u(t) = \begin{cases} 
    1 & t\geq 0 \\
    0 & t < 0
    \end{cases}$

  * Useful for extracting portions of other functions

  * Another property:

    $u(t) = \int_{-\infin}^{t} \delta(\tau) d\tau$

### Fourier Analysis

* Fourier postulated that any periodic signal can be represented as an infinite linear combination of harmonic sinusoids

* Trigonometric Fourier series:

  $x(t) = a_0 + \sum_{n=1}^{\infin} a_n cos(n \omega_0 t) + b_n sin(n \omega_0 t)$

* The coefficients can be calculated as follows:

  $a_0 = \frac{1}{T_0} \int_{T_0} x(t)dt$

  $a_n = \frac{2}{T_0}  \int_{T_0} x(t)cos(n \omega_0 t) dt$

  $b_n = \frac{2}{T_0}  \int_{T_0} x(t)sin(n \omega_0 t) dt$

  * $\int_{T_0}$ represents integration over any interval of duration $T_0$

* Fourier series: compact form

  * The expressions above apply for real or complex-valued functions

  * If we focus on real-valued functions, we can write

    $x(t) = C_0 + \sum_{n=1}^{\infin} C_ncos(n\omega_0t + \theta_n)$

  * Then:

    $a_n = C_n cos(\theta_n)$

    $b_n = -C_n sin(\theta_n)$

    $C_0 = a_0$

    $C_n = \sqrt{a_n^2 + b_n^2}$

    $\theta_n = tan^{-1}(\frac{-b_n}{a_n})$

* Exponential Fourier series

  $x(t) = \sum_{-\infin}^{\infin} D_n e^{jn \omega_0 t}$

  $D_n = \frac{1}{T_0} \int_{T_0} x(t)e^{-jn \omega_0 t}dt$

  * When $x(t)$ is real, $a_n$ and $b_n$ are real, so $D_n$ and $D_{-n}$ are conjugates

  * $D_0 = C_0 = a_0$

  * $D_n = \frac{1}{2}C_n e^{j\theta_n}$

  * $D_{-n} = \frac{1}{2}C_n e^{-j\theta_n}$

  * Thus:

    $|D_n| = |D_{-n}| = \frac{1}{2}|C_n|$ for $n > 0$

    $\angle D_n = \theta_n$ and $\angle D_{-n} = -\theta_n$

  * Magnitude and phase are plotted separately when visualizing signals in this form

* Aperiodic signals
  * How can we represent aperiodic signals in the Fourier domain?
    * Think of an aperiodic signal as a periodic signal with *infinite period*
  * This leads us to the Fourier transform

### Fourier Transform

* Denoted as $X(\omega)$ for a signal $x(t)$

  $X(\omega) = \int_{-\infin}^{\infin}x(t)e^{-j\omega t}dt$

* Inverse Fourier transform:

  $x(t) = \frac{1}{2\pi} \int_{\infin}^{\infin} X(\omega) e^{j\omega t} d\omega$

* $x(t)$ and $X(\omega)$ are a Fourier transform pair, denoted as $x(t) \Longleftrightarrow X(\omega)$
* For real $x(t)$:
  * The amplitude spectrum $|X(\omega)|$ is even
  * The phase spectrum $\angle X(\omega)$ is odd

* Fourier transform properties

  * Linearity

    $a_1x_1(t) + a_2x_2(t) \Longleftrightarrow a_1X_1(\omega) + a_2X_2(\omega)$

* Example

  * The unit rectangular pulse function is defined as:

    $rect(t) = \begin{cases} 
    0 & |t| > \frac{1}{2} \\
    \frac{1}{2} & |t| = \frac{1}{2} \\
    1 & |t| < \frac{1}{2} \end{cases}$

  * Then, $rect(\frac{t}{\tau})$ is a scaled rectangular pulse with width $\tau$

  * The Fourier transform is:

    $X(\omega) = \int_{-\infin}^{\infin} rect(\frac{t}{\tau})e^{-j\omega t}dt$

    $= \int_{-\tau/2}^{\tau/2}e^{-j\omega t}dt$

    $= -\frac{1}{j\omega}(e^{-j\omega\tau/2} - e^{j\omega \tau/2})$

    $= \frac{2}{\omega}sin(\frac{\omega \tau}{2})$

  * Using the definition $sinc(t) = \frac{sin(t)}{t}$:

    $rect(\frac{t}{\tau}) \Longleftrightarrow \tau sinc(\frac{\omega \tau}{2})$

    