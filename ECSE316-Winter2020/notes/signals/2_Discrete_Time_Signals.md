# Discrete Time Signals

### Energy and Power

* Energy of a discrete-time signal $x$ is defined as:

  $E_x = lim_{N \rightarrow \infin} \sum_{n=-N}^N |x[n]|^2$

* Power is defined as:

  $P_x = lim_{N \rightarrow \infin} \frac{1}{2N+1} \sum_{n=-N}^N |x[n]|^2$

* Recall that an energy signal has finite energy, and a power signal has finite, non-zero power
* From the above definitions we can observe that a signal cannot be a energy and power signal at the same time
  * A signal can be neither an energy nor a power signal

* Downsampling

  * Construct a new signal by taking every $M^{th}$ sample from the original signal

    $x_d[n] = x[Mn]$ for integer $M$

### Sampling

* Consider the continuous sinusoid $x(t) = sin(4\pi t)$

* The frequency of $x(t)$ is $\omega_x = 4\pi$ rad/s

  * To plot $x(t)$ we sample at discrete time intervals starting at $t=0$
  * Suppose we sample at 100 samples per second, i.e. every $T=0.01s$
  * So $t$ takes values at $t=0.01m$ where $m = 0,1,2...$

* If we increase the frequency of $x(t)$ t0 $\omega_x = 4\pi + 200 \pi = 204\pi$ rad/s

  * The sampled signal is the same!

  * DT sinusoids separated by values of $\Omega$ that are integer multiples of $2\pi$ have identical waveforms

    $e^{(j\omega + 2\pi m)n} = e^{j\omega n}$

  * By increasing the sampling rate we can distinguish between the two DT sinusoids from before

### Periodic Signals

* A DT signal $x[n]$ is periodic with period $N$ if: $x[n] = x[n+N]$ for all $n$, where $N$ is a positive integer
  * Smallest $N = N_0$ value for which this is true: fundamental period

* The DT sinusoid is periodic with integer period $N_0$ if $cos(\Omega n) = cos(\Omega (n+N_0))$

  * This happens only if $\Omega N_0 = 2\pi m$, where $m$ is an integer
  * Implies that $\frac{\Omega}{2\pi} = \frac{m}{N_0}$ is a rational number
  * The period is $N_0 = \frac{2\pi m}{\Omega}$, where $m$ is the smallest integer that makes this fraction an integer

* Example: if $cos(\frac{4\pi}{17}n)$ is periodic find its period $N_0$

  $N_0 = \frac{2\pi m}{\Omega} = \frac{2\pi m}{\frac{4\pi}{17}} = \frac{17m}{2}$

  * We select $m=2$ which gives us period $N_0 = 17$

### Fourier Transform

* The discrete-time Fourier transform is given by:

  $X(\Omega) = \sum_{n=-\infin}^{\infin} x[n]e^{-j\Omega n}$

* The inverse Fourier transform is given by:

  $x[n] = \frac{1}{2\pi} \int_{2\pi} X(\Omega)e^{jn\Omega}d\Omega$

  



