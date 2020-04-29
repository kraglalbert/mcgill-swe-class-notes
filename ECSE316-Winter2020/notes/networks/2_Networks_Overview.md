# Networks Overview

1. [Circuit vs. Packet-Switching](#circuit-vs-packet-switching)
2. [Delay, Loss, and Thoughput](#delay-loss-and-throughput)

### Circuit vs. Packet-Switching

* The railroad network
  * Used as a model for phone networks
  * Centralized: network components are connected to a centralized point which handles the switching and routing
  * Customer loses some control; congestion is possible at the network hubs
* How is data transferred through a network?
  * **Circuit switching:** dedicated circuit per call, e.g. telephone network
  * **Packet-switching:** data is sent through the network in discrete chunks
* Circuit switching
  * End-to-end resources are reserved for each "call"
  * Example: telephone network
    * Call setup required
    * Dedicated resources (no sharing)
    * Link bandwidth, switch capacity
    * Circuit-like (guaranteed) performance
  * Network resouruces are divided into fixed "pieces" (e.g. chunks of time or bandwidth)
  * Pieces are allocated to calls as requested
  * Advantage: Guaranteed level of service for duration of call
  * Disadvantage: resources go idle if not used by call (no sharing)
* Packet-switching
  * Data streams are divided into packets
    * Each packet is transmitted independently
  * Resources are used **as needed**
    * Resource contention; statistical multiplexing
  * No call setup
  * No resource starvation
  * No guaranteed service
* Sharing resources
  * Two approaches: frequency-division multiplexing (FDM) and time-division multiplexing (TDM)
* Statistical multiplexing
  * Say two hosts A and B are sending packets through a link
  * The sequence of A and B packets does not have a fixed pattern; bandwidth is shared on demand
* Resource contention (packet-switching)
  * The total resources demanded can exceed the amount available
  * **Congestion:** packets queue and wait for link use
  * Store and forward: packets move one hop at a time
    * If link is busy, store packet in a queue
    * Entire packet must arrive at router before it is transmitted on next link
    * Packets are processed independently at each router/switch

* Telephone edge-core comparison
  * Telephones and faxes used to be simple devices
  * The intelligence/sophistication resided in the telephone network core
    * Hundreds of switches
    * Densely interconnected
* Internet edge-core comparison
  * Routers are not simple devices, but they are focused on one thing (routing packets)
  * Internet philosophy: *push intelligence out to the end-hosts*
  * Changing somewhat nowadays as software-defined networking exploits the capabilities of modern routers
* Example
  * 1 Mb/s link
  * Each user: 100 kb/s when active; active 10% of the time
  * Circuit switching: only up to 10 users supported
  * Packet-switching allows more users to use the network
* Is packet-switching a slam-dunk winner?
  * Great for bursty data
    * Resource sharing and simpler design
  * Excessive congestion: packet delay and loss
    * Protocols needed for reliable data transfer and congestion control
    * More intelligence required at network edges
* Review: two differences between circuit-switching and packet-switching:
  * Dedicated resources vs. resource sharing
  * Intelligence at core vs. at network edges

### Delay, Loss, and Throughput

* Questions to consider:
  * How do we measure the performance of a network?
  * Why does delay happen? Why do packets get lost?
* How does loss and delay occur?
  * Packets queue in router buffers
  * Packet arrival rate to link exceeds output link capacity
  * Packets queue and wait for their turn

* Packets are lost if there is no space in the router's buffer when a packet arrives
* Four sources of packet delay
  * Processing delay
    * Check bit errors
    * Determine output link
    * Typically a few micoseconds or less
  * Queueing delay
    * Time waiting at output link for transmission
    * Depends on congestion level of router
  * Transmission delay
    * R = link bandwidth (bps)
    * L = packet length
    * Time to send bits to link: L/R
    * Can be significant for low-speed links
  * Propagation delay
    * d = length of physical link
    * s = propagation speed in medium (~$2 \times 10^8$ m/s)
    * Propagation delay: d/s
* Per hop delay: $d_{hop} = d_{proc}+d_{queue}+d_{trans}+d_{prop}$
* Queueing relay (revisited)
  * $R$ = link bandwidth (bps)
  * $L$ = packet length (bits/packet)
  * $a$ = average packet arrival rate (packets/sec)
  * Traffic intensity: $\frac{La}{R}$
  * $\frac{La}{R} \approx 0$: average queueing delay is small
  * $\frac{La}{R} \approx 1$: delays become large
  * $\frac{La}{R} > 1$: more work arriving than can be serviced, average delay is infinite

* Throughput
  * **Throughput** is the rate (bits/time unit) at which bits are transferred between the sender and receiver
    * Instantaneous: rate at a given point in time
    * Average: rate over a long(er) period of time
  * Bottleneck link
    * Link on end-to-end path that constrains end-to-end throughput

