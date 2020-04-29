# Link Layer

1. [Introduction](#introduction)
2. [Cyclic Redundancy Check](#cyclic-redundancy-check)
3. [Multiple Access Protocols](#multiple-access-protocols)

### Introduction

* Terminology:
  * Hosts and routers are nodes
  * Communication channels that connect adjacent nodes along communication paths are links
    * Wired links, wireless links, LANs
  * Layer-2 packet is a frame; encapsulates datagram
* The data-link layer has the responsibility of transferring datagram from one node to an adjacent node over *a single link*
  * Conversely, the network layer has the responsibility of transferring a datagram from one network interface to another (across the entire network)
* Link layer services
  * Framing, link access:
    * Encapsulates datagram into a frame, adding header and trailer
    * Channel access if shared medium
    * MAC addresses used in frame headers to identify source + destination
      * Different from IP addresses
  * Reliable delivery between adjacent nodes
    * Techniques are rarey used on links with low bit error rate (e.g. fiber connections)
    * Wireless links have high error rates
  * Flow control
    * Pacing between adjacent sending and receiving nodes
  * Error detection
    * Errors are caused by signal attenuation and noise
    * It the receiver detects presence of errors, it can then signal the sender for retransmission or it can just drop the frame
  * Error correction
    * Receiver identifies and corrects bit errors without resorting to retransmission
* Sender side
  * Encapsulates datagram in a frame
  * Adds error checking bits, reliable data transfer, flow control, etc.
* Receiver side
  * Looks for errors, reliable data transfer, flow control, etc.
  * Extracts datagram and passes it to the receiving node

* Error detection is common in link-layer protocols; error correction is less common but still provided in a few protocols

### Cyclic Redundancy Check

* Suppose we have *D* bits that we want to send as our data
* We can choose a generator *G* that has *r+1* bits
* Goal: choose *r* CRC bits *R* such that:
  * <D, R> are exactly divisible by *G* (modulo 2)
  * The receiver knows *G*, and divides <D, R> by *G*; if the remainder is non-zero then we know an error has occurred
  * Can detect all burst errors less than *r+1* bits in length
* How to calculate the bits to transmit:
  * Multiply the data polynomial D by the degree of the generator polynomial G
  * Divide this new data polynomial D' by G to get the remainder R
  * The final transmitted data will be D' XOR R, or [D bits] | [R bits]
* CRC codes with more than one non-zero bit are capable of detecting all single bit errors
* Example:
  * $D$: 10101010 -> $X^7 + X^5 + X^3 + X$
  * $G$: 1001 -> $X^3 + 1$
  * $D' = D\times 2^3 = X^{10} + X^8 + X^6 + X^4$
  * Divide the $D'$ by the $G$ polynomial to get a remainder of 101 ($X^2 + 1$)
  *  The transmitted bits are 10101010**101**

### Multiple Access Protocols

* There are two types of links
  * Point-to-point, e.g.  wired connection such as a link between an Ethernet switch and a host
  * Broadcast, e.g. Ethernet bus or wireless LAN (shared wire or medium)
* Multiple access protocol
  * Distributed algorithm that determines how nodes share a channel (i.e. determine when a node can transmit)
  * Communication about channel sharing must use the channel itself
  * The goal is to avoid collisions that are caused by simultaneous transmissions

* Ideal multiple access protocol (broadcast channel of rate R bps)
  1. When one node wants to transmit, it can send at rate R
  2. When M nodes want to transmit, each can send at an average rate R/M
  3. Fully decentralized: no special node to coordinate transmissions, and no synchronization of clocks/slots
  4. Simple
* Taxonomy of multiple access protocols
  * Channel partitioning
    * Divide channel into smaller "pieces" (e.g. time slots, frequency, code)
    * Allocate piece to node for exclusive use
  * Random access
    * Channel not divided; allow collisions
    * "Recover" from collisions
  * Taking turns
    * Nodes take turns but nodes with more to send can take longer turns
* TDMA: Time Division Multiple Access
  * Access to channel in "rounds"
  * Each station gets fixed length slot (length = packet transmission time)
  * If a host has nothing to send then the slot goes idle
  * Works best when hosts almost always have data to send (i.e. the network has very high and even load)
* FDMA: Frequency Division Multiple Access
  * Channel spectrum is divided into frequency bands
* CDMA: Code Division Multiple Access
  * Used in several wireless broadcast channels (cellular, satellite, etc.) and various standards
  * All users share the same frequency
    * Each user has their own chipping sequence (i.e. code) to encode data
  * Encoded signal: (original data) $\times$ (chipping sequence)
  * Decoding: inner (dot) product of encoded signal and chipping sequence
  * Allows multiple users to transmit simultaneously with minimal interference (if codes are orthogonal!)
    * Orthogonal means that the dot product of two vectors is zero
    * If there are 4 users, then we would need chipping sequences that consist of at least 4 bits in order to have 4 orthogonal codes
  * 