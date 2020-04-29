# Information Redundancy & Codes

1. [Intro](#intro)
2. [Hamming Distance](#hamming-distance)
3. [Parity Codes](#parity-codes)
4. [Error-Correcting Parity Codes](#error-correcting-parity-codes)
5. [Improving Detection](#improving-detection)
6. [Checksum](#checksum)
7. [M-of-N Codes](#m-of-n-codes)
8. [Cyclic Codes](#cyclic-codes)
9. [Cyclic Code Examples](#cyclic-code-examples)
10. [Arithmetic Codes](#arithmetic-codes)
11. [Algorithm-Based Fault Tolerance](#algorithm-based-fault-tolerance)

### Intro

* A data word with *d* bits is encoded into a codeword with *c* bits, where *c > d*
  * To extract the original data, the codeword must be decoded
* Not all $2^c$ combinations are valid codewords, however
  * If the *c* bits do not constitute a valid codeword then an **error** is detected
  * Some encoding schemes even allow for errors to be **corrected**
* Key parameters for an encoding:
  * How many erroneous bits can be detected?
  * How many erroneous bits can be corrected?
* Overhead of code:
  * Additional bits are required compared to the original data
  * Time is required to encode and decode the data

### Hamming Distance

* The Hamming distance between two codewords is the number of bit positions in which the two words differ
  * e.g. `101` and `011` differ in two bit positions, so they have a Hamming distance of 2
    * This implies that one bit error will not change one of the codewords into the other
* The distance of a code is the *minimum* Hamming distance between any two valid codewords
* Detection vs. correction
  * To detect up to *k* bit errors, the code distance should be at least *k+1*
  * To correct up to *k* bit errors, the code distance should be at least *2k+1*
* Separability of a code
  * A code is **separable** if it has separate fieelds for the data and code bits
  * Decoding consists of disregarding the code bits
  * The code bits can be processed separately to verify the correctness of the data
  * A **non-separable** code has the data and code bits integrated together—extracting data from the encoded word requires some processing

### Parity Codes

* The simplest separable codes are parity codes
* A parity-coded word includes *d* data bits and an extra bit which holds the parity
* In an even parity code, the extra bit is set so that the total number of 1s in the (*d*+1)-bit word (including the parity bit) is even 
  * Vice versa for an odd parity code
* The overhead fraction of a parity code is 1/*d* (i.e. one parity bit per *d* data bits)
* A parity code has a distance of 2, i.e. it will detect all single-bit errors
  * If one bit flips from 0 to 1 (or vice versa), then the parity of the bits will no longer be the same
* Simple parity codes cannot correct any errors 
* Parity per byte
  * A separate parity bit is assigned to every byte (or any other group of bits)
  * The overhead increases from 1/*d* to *m*/*d*
  * Up to *m* errors can be detected if they occur in different bytes
  * If both all-0s and all-1s failures may happen, then select an odd parity for one byte and an even parity for another

### Error-Correcting Parity Codes

* Simplest scheme: data is organized in a 2D array

* The bits at the end of a row describe the parity over that row

* The bits at the bottom of a column describe the parity over that column

* A single bit error anywhere will cause a row and a column to be erroneous

  * This identifies a unique erroneous bit
  * This is an example of overlapping parity: each bit is covered by more than one parity bit

* Overlapping parity: general model

  * Purpose: identify every single erroneous bit
  * *d* data bits and *r* parity bits (total of *d+r* bits)
  * Assuming single bit errors only, there are *d+r* total error states and one error-free state, so *d+r+1* states total
  * We need *d+r+1* distinct parity signatures (bit configurations) to distinguish between these states

* *r* parity bits generate $2^r$ parity signatures

* Hence, *r* is the smallest integer that satisfies:

  $2^r \geq d+r+1$

* Assigning parity bits example

  * Assume we have $d=4$ data bits
  * $r=3$ is then the minimum number of parity bits that we need
  * There are then $d+r+1 = 8$ total states 
  * Example table:

  | **State**   | Erroneous Parity Checks | Syndrome |
  | ----------- | ----------------------- | -------- |
  | No errors   | None                    | 000      |
  | Bit 0 error | $p_0$                   | 001      |
  | Bit 1 error | $p_1$                   | 010      |
  | Bit 2 error | $p_2$                   | 100      |
  | Bit 3 error | $p_0$, $p_1$            | 011      |
  | Bit 4 error | $p_0$, $p_2$            | 101      |
  | Bit 5 error | $p_1$, $p_2$            | 110      |
  | Bit 6 error | $p_0$, $p_1$, $p_2$     | 111      |

  * The syndrome is the indicator of error—if it is non-zero, then an error must have occured
  * Bits are organized as follows: ($a_3$ $a_2$ $a_1$ $a_0$ $p_2$ $p_1$ $p_0$)
  * A parity bit covers all bits whose error it indicates
    * e.g. p0 covers positions 0, 3, 4 and 6
    * $p_0 = a_0 \oplus a_1 \oplus a_3$
    * $p_1 = a_0 \oplus a_2 \oplus a_3$
    * $p_2 = a_1 \oplus a_2 \oplus a_3$
  * Calculating the syndrome bits (s2 s1 s0)
    * $s_0 = p_0 \oplus a_0 \oplus a_1 \oplus a_3$
    * $s_1 = p_1 \oplus a_0 \oplus a_1 \oplus a_3$
    * $s_2 = p_2 \oplus a_0 \oplus a_1 \oplus a_3$

* Data and parity bits can be reordered so that the calculated syndrome minus 1 will be the index of the erroneous bit (zero-indexed)

  * In general we need to select *d+r+1* out of the $2^r$ binary combinations to be syndromes
  * Combinations with many 1s should be avoided
    * Less 1s in the parity check matrix means simpler circuits for the encoding and decoding operations

### Improving Detection

* The previous code can correct a single bit error but detect a double error
* One way of improving error detection capabilities is to add an extra check bit which is the parity bit of all the other data and parity bits
  * We need a fourth $s_3$ bit
  * Since this bit takes the parity of all data and check bits, a single bit error will change the overall parity and set $s_3 = 1$
  * The last three bits of the syndrome will indicate the bit in error to be corrected (as before) as long as $s_3 = 1$
  * If $s_3 = 0$ and any other syndrome bit is nonzero, then a double error has been detected
    * Can be detected but not corrected
* As *d* increases, the parity overhead *r*/*d* decreases
  * The probability of having more than one bit error in the d+r bits increases
  * If we have a total of *D* data bits, we can reduce the probability of having more than one bit error by partitioning the *D* bits into D/*d* equal slices, with each slice being encoded separately
  * We therefore have a tradeoff between the probability of undetected error and the overhead *r*/*d*

### Checksum

* Primarily used to detect errors in data transmission on communication networks
* Basic idea: add up the block od data being transmitted and transmit this sum as well
* Receiver adds up the data received and compares it with the checksum it received
  * If the two do not match then an error is indicated
* Variations of checksums
  * Assume we *d* data bits
  * **Single-precision** version: checksum is a modulo $2^d$ addition
  * **Double-precision** version: checksum is a modulo $2^{2d}$ addition
  * In general, single-precision catches fewer errors than double-precision
  * **Residue** checksum takes into account the carry out of the d-th bit as an end-around carry
  * The **Honeywell** checksum concatenates words into pairs for the checksum calculation (done modulo $2^{2d}$)—guards against errors in the same position
* All checksum schemes allow error detection but not error location
* The entire block of data must be retransmitted if an error is detected

### M-of-N Codes

* Unidirectional error codes
  * One or more 1s turn to 0s and no 0s turn to 1s (or vice versa)
* Exactly M bits out of N are 1: $\binom{M}{N}$ codewords
  * A single bit errror: (M+1) or (M-1) 1s
  * This is a non-separable code
* To get a separable code:
  * Add M extra bits to the M-bit data word for a total of M 1s
  * This is a M-of-2M separable unidirectional error code
* Berger Code
  * Low overhead unidirectional error code
  * Separable code
    * Counts the number of 1s in the word
    * Expresses it in binary
    * Complements it
    * Appends this quantity to the data
  * Example
    * Encoding is 11101
    * Four 1s, which is 100 in binary
    * 011 after complementing
    * The encoded word is 11101011
*  Overhead of Berger Code
  * *d* data bits—at most *d* 1s—up to $\lceil log_2(d+1) \rceil$ bits to describe
  * Overhead: $\lceil log_2(d+1) \rceil$/$d$
  * $r$: number of check bits
  * If $d = 2^k - 1$, then $r=k$
    * Maximum-length Berger Code
  * Smallest number of check bits out of all separable codes (for unidirectional error detection)

### Cyclic Codes

* Have non-separable as well as separable versions

* Encoding consists of multiplying (mod 2) the data word by a constant number

* The coded word is the product of the multiplication

* Decoding involves dividing by the same constant—if the remainder is non-zero, then we know an error has occurred

* Cyclic codes are widely used in data storage and communication

* Theory

  * *k*-bits of data are encoded

  * Encoded word of length *n* bits is obtained by multiplying the given *k* data bits by a number that is *n-k+1* bits long

  * The multiplier is represented as a polynomial (known as the **generator** polynomial)

  * 1s and 0s in the *n-k+1*-bit multiplier are treated coefficients of a (*n-k*)-degree polynomial

  * Example: multipler is 11001, the generator polynomial is:

    $G(X) = 1 \cdot X^0 + 0 \cdot X^1 + 0 \cdot X^2 + 1 \cdot X^3 + 1 \cdot X^4$

    $= 1 + X^3 + X^4$ 

* A (*n, k*) cyclic code

  * Using a generator polynomial of degree (*n-k*) and total number of encoded bits *n*

  * A (*n, k*) cyclic code can detect all single errors and all runs of adjacent bit errors no longer than (*n-k*)

    * e.g. a (*16+k, k*) cyclic code can detects burst errors of up to length 16

  * Useful in applications such as wireless communications 

    * Channels are frequently noisy and have bursts of interference resulting in runs of adjacent bit errors

  * For a polynomial to be a generator poynomial for a (*n, k*) cyclic code it must be a factor of $X^n-1$

    * e.g. $1 + X^3 + X^4$ is a factor of $X^{15}-1$, so this is a (15, 11) code
    * The polynomial has degree 4, so *k* is equal to 15-11

  * For the 5-bit code:

    $X^5-1 = (X+1)(X^4+X^3+X^2+X+1)$

    * Multiply 0000, ... 1111 by $(X+1)$ to obtain all codewords of the (5, 4) code

* Hardware implementation

  * Multiplication can be implemented with shift registers or XOR gates

* Implementing a divider circuit

  * Encoded word $E(X)$, generator polynomial $G(X)$, and original data word polynomial $D(X)$
  * If no bit errors exist, we receive $E(X)$ and calculate $D(X)$ by $D(X) = E(X)/G(X)$, getting a remainder of zero
  * Note that **addition is the same as subtraction in mod-2 arithmetic**

* Detecting bursty errors

  * Many applications need to make sure that all burst errors of length 16 bits or less will be detected
  * Cyclic codes of type (*16+k, k*) are used
  * The generating polynomial should be selected to allow a large number of data bits
  * Most commonly used: CRC-16 (16-bit cyclic redundandy code)
    * $G(X)=X^{16}+X^{15}+X^2+1$

* Separable cyclic codes

  * Allow use of data before encoding is completed

  * Data word is originally $D(X)=d_{k-1}X^{k-1}+d_{k-2}X^{k-2} + ... d_0$

  * Append (*n-k*) zeroes to $D(X)$ to obtain $\bar D(X)=d_{k-1}X^{n-1}+d_{k-2}X^{n-2} + ... d_0X^{n-k}$

    * i.e. multiply $D(X)$ by $X^{n-k}$

  * If we divide by $G(X)$, we have a quotient $Q(X)$ and a remainder $R(X)$:

    $\bar D(X) = X^{n-k}D(X) = Q(X)G(X)+R(X)$

    This has $G(X)$ as a factor
  
  * $C(X) = \bar D(X) + R(X) = Q(X)G(X)$
  
  * $Q(X) = \frac{X^{n-k}D(X)}{G(X)}$
  
    * Note that there may be a remainder when doing this division!
  
  * In $C(X)$, the first *k* bits are data, and the last *n-k* are check bits
  
  * Note that $G(X)$ will always have degree $n-k$ 

### Cyclic Code Examples

### Arithmetic Codes

* Codes that are preserved under a set of arithmetic operations

* Enable detection of errors occurring during execution of arithmetic operations

* Error detection can be obtained by duplicating the arithmetic processor (too expensive)

* An error code is preserved under an arithmetic operation * if for any two operands X and Y the corresponding encoded entities X' and Y' there is an operation $\circ$ satisfying $X' \circ Y' = (X * Y)'$

* Arithmetic codes should be detect all single-bit errors

* A single-bit error in an operand or an intermediate result may cause a multiple-bit error in the final result

  * Example: when adding two binary numbers, if stage *i* of the adder is faulty, all the remaining (*n-i*) higher order digits may be erroneous

* Non-separable arithmetic codes

  * Simplest: AN-codes
    * Formed by multiplying the operands by a constant A
  * $X' = AX$
  * Example: A = 3
    * Each operand is multiplied by 3
    * The result of the operation is checked to see whether it is an integer multiple of 3
    * All error magnitudes that are multiples of A will not be detected

* AN codes

  * $A$ should not be a power of the radix 2
  * An odd $A$ is best—it will detect every single bit fault (such an error has a magnitude of $2^i$)
  * A = 3 is the least expensive AN code that enables detection of all single-bit errors

* Example:

  * The original number is 0110 = 6
  * Multiplied by A = 3 it becomes 010010 = 18
  * A fault in bit position $2^3$ may give the erroneous result 011010 = 26
  * Easy to detect since 26 is not a multiple of 3

* Separable arithmetic codes

  * Simplest: residue and inverse residue code
  * We attach a separate check symbol $C(X)$ to every operand $X$
  * For the residue code, $C(X)=X mod A=|X|_A$
    * $A$ is called the check modulus
  * For the inverse residue code: $C(X)=A-(X mod A)$
  * For both codes: $C(X) \circ C(Y)=C(X * Y)$
    * $\circ$ equals $*$, either addition or multiplication
  * $|X+Y|_A = ||X|_A + |Y|_A|_A$
  * $|X \cdot T|_A=||X|_A \cdot |Y|_A|_A$
  * Division
    * $X-S = Q \cdot D - X$
    * $X$ is the dividend, $D$ is the divisor, $Q$ is the quotient, $S$ is the remainder
  * The check is:
    * $||X|_A-|S|_A|_A = ||Q|_A \cdot |D|_A|_A$

* Examples

  * $A=3,X=7,Y=5$

    * The residues are $|X|_3=1$, $|Y|_3=2$
    * $|7+5|_3 = ||7|_3 + |5|_3|_3 = 0$
    * $|7 \cdotp 5|_3 = ||7|_3 \cdotp |5|_3|_3 = 2$

  * $A=3,X=7,Y=5,Q=1,S=2$

    * The residue check is $||7|_3-|2|_3|_3 = ||5|_3 \cdot |1|_3|3 = 2$

    * Subtraction is done by adding the complement to the modulus

      $|1-2|_3 = |1+|3-2|_3|_3 = |1+1|_3 = 2$

* Residue mod A vs. AN code

  * Same undetectable errors, i.e. errors that modify the result by a multiple of A will not be detected
  * Same checking algorithm
    * Compute the residue mod A of the result
  * Same increase in word length: $|log_2A|$
  * Most important difference: separability
    * The unit for $C(X)$ in the residue code is separable
    * Single unit for the AN code

* Low cost arithmetic codes

  * AN and residue codes with A=3 are the simplest examples of a class of arithmetic codes with $A = 2^a-1$, where $a$ is an integer

  * Simplifies the calculation of remainder when dividing by A (checking algorithm)

* Example

  * Calculate remainder when dividing $X=11110101011$ by  $A=7=2^3-1$
  * Partition $X$ into groups of size 3, starting with the least significant bit
  * This yields $X=(Z_3,Z_2,Z_1,Z_0)=(11,110,101,011)$
  * Add these groups modulo 7: cast out 7s and add the end-around carry when necessary
    * Add together 3-bit segments
    * Weight of the carry-out is then 8, $|8|_7 = 1$
    * Residue mod 7 of $X$ is 3
    * This is the correct remainder when dividing $X = 1963_{10}$ by 7

### Algorithm-Based Fault Tolerance

* Data redundancy at the application level

* Higher efficiency when applied to large data arrays

  * Examples: matrix-based and signal processing applications

* Given an $n \times m$ matrix $A$, define the column checksum matrix:

  $A_C = \begin{bmatrix}
  A \\
  eA 
  \end{bmatrix}, e = [1111...1]$

  And the row checksum matrix:

  $A_R=[A Af], f=[1111...1]^T$

  $(n+1)\times (m+1)$ full ckecksum matrix:

  $A_F = \begin{bmatrix}
  A & Af\\
  eA & eAf 
  \end{bmatrix}$

* Column and row checksum matrices can detect a single fault 
* Full checksum matrix can locate a fault; if the checksum is accurate then the fault can be corrected
* 