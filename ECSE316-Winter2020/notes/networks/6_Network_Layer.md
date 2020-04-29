# Network Layer

1. [IP Addressing](#ip-addressing)
2. [Routing](#routing)
3. [Hierarchical Routing](#hierarchical-routing)
4. [Software-Defined Networking](#software-defined-networking)

### IP Addressing

* An IP address is a 32-bit identifier for a host + router interface
  * Interface: connection between host/router and physical link
  * Routers typically have multiple interfaces and the host typically has one
  * IP addresses are associated with each interface
* Subnets
  * An IP address contains a **subnet** part (high order bits) and a **host** part (lower order bits)
  * What is a subnet?
    * Can identify devices in the same subnet by looking at the subnet portion of their IP addresses (they'll all be the same)
    * The devices can physically reach each other without an intervening router
* CIDR: Classless InterDomain Routing
  * The subnet portion of an IP address can have arbitrary length
  * Address format: `a.b.c.d/x` where `x` is the number of bits in the subnet portion of the address
    * e.g. `200.23.16.0/23`
  * An all-zeroes address is (historically) reserved to identify the subnet itself
* How can you get an IP address?
  * DHCP: Dynamic Host Configuration Protocol
    * Dynamically get address from a server
    * Dynamic assignment is especially important in scenarios where hosts are frequently entering and leaving the network
    * DHCP provides the host with an IP address, indicating the subnet mask
    * It also shares the address of the default gateway (the first hop router to the rest of the internet) and the address of the local DNS server
* DHCP overview
  * Steps:
    * Host broadcasts DHCP discover message
    * DHCP server responds with DHCP offer message
    * Host request IP address with a DHCP request message
    * DHCP server sends address in a DHCP ack message
* How does a network get the subnet part of an IP address?
  * Gets allocated a portion of its ISP's address space
* How does an ISP get a block of addresses?
  * ICANN: Internet Corporation for Assigned Names and Numbers
    * Allocates addresses
    * Manages DNS
    * Assigns domain names and resolves disputes

### Routing

* Goal: determine a good path (sequence of routers) through a network from source to destination

* Graph abstraction for routing algorithms:

  * Graph nodes are routers
  * Graph edges are physical links
    * Link cost: delay, dollar cost, or congestion level
  * Good path typically means the minimum cost path

* Shortest-path routing

  * Each communication link is assigned a positive number for its cost
    * Can have potentially different costs for each direction
  * Route each packet along the shortest (least cost) path between origin and destination nodes
  * If each link has the same cost, then the shortest path is the one with the smallest number of hops
    * This isn't very realistic though

* Routing algorithm classification

  * Global or decentalized information?
  * Global:
    * All routers have complete topology and link cost information
    * Link state algorithms fall under this category
  * Decentralized
    * Router knows physically-connected neighbours + link costs to neighbours
    * Iterative process of computation + exchange of info with neighbours
    * Distance vector algorithms fall under this category
  * Static:
    * Routes change slowly over time
  * Dynamic:
    * Routes change more quickly
    * Requires periodic updates in response to link cost changes

* Link-state routing algorithms

  * Dijkstra's algorithm
    * Global: all nodes have the same information
    * Algorithm computes least cost paths from one node (source) to all other nodes
    * Iterative: after *k* iterations, know the least cost path to *k* destinations
    * Notation:
      * *D(v):* current value of cost of path from source to destination *v*
      * *p(v):* predecessor node along path from source to *v*
      * *N':* set of nodes whose least cost path is definitely known
    * Can achieve O(nlogn) runtime with an efficient implementation

* Distance vector routing

  * Each node only knows the cost to get to its immediate neighbours
  * Iteratively exchange messages with neighbours and update routes accordingly
  * Each node has a **forwarding table**, which contains the estimated cost to each destination and the first hop on the path to get there
    * Initially all values are set to infinity except for the node's direct neighbours
  * Nodes then **advertise** to their neighbours the path costs for various destinations (if they have changed)
    * These advertisements allow nodes to recalculate their own path costs and update their tables
    * Only advertise when a node's costs have changed!

  * Link cost changes
    * Good news travels fast: if a link cost decreases then the updated information diffuses rapidly through the network
    * Bad news travels slowly: count to infinity problem
      * If a link cost increases then it can take many iterations for the correct information to be reached
    * Solution: **poisoned reverse**
      * If `Z` routes through `Y` to get to `X`, then `Z` tells `Y` that `Z`'s distance to `X` is infinite (so that `Y` won't route to `X` via `Z`)

### Hierarchical Routing

* So far, we have assumed an ideal routing scenario where the network is flat
  * However, at the scale of the internet we cannot store 200 million+ destinations in routing tables!
* The internet is really a network of networks
  * Each autonomous system is responsible for the routing within its own network
  * Autonomous system (AS): aggregate routers in a region
  * Routers in the same AS run the same routing protocol
* Gateway router: direct link to a router in another AS
* We now have inter-AS routing and intra-AS routing
  * When a router receives a packet destined for a host outside of its AS, it needs to identify the exit gateway router
  * Using the inter-AS protocol, an AS communicates with another AS in order to learn which subnets can be reached via that AS
    * The inter-AS protocol propagates *reachability* information to all internal routes
  * Hot potato routing: send packet towards the closer router
* Intra-AS routing
  * Also known as interior gateway protocols (IGP)
  * Most common intra-AS routing protocols:
    * RIP: Routing Information Protocol
    * OSPF: Open Shortest Path First
    * IGRP: Interior Gateway Routing Protocol

* OSPF
  * Publicly available, uses link state algorithm
* BGP (Border Gateway Protocol)
  * The de facto standard
  * BGP provides each AS with a means to:
    * Obtain subnet reachability information from neighbouring ASes
    * Propagate reachability information to all AS-internal routers
    * Determine "good" routes to subnets based on reachability information and policy
* BGP basics
  * Pairs of routers (BGP peers) exchange routing information
    * Use semi-permanent TCP connections (BGP sessions)
    * Each AS advertises prefixes to other ASes
    * When an AS advertises a prefix, it is promising that it will forward any datagrams destined to that prefix towards the subnet associated with the prefix
    * Each advertisement can contain information about multiple prefixes
  * The prefix advertisement contains not only the prefix (indicating reachability) but also a variable number of attributes
    * Two important attributes are the AS-PATH and NEXT-HOP
    * A gateway router can accept or decline a route advertisement based on its import policy
* BGP route selection
  * In many cases a router or AS may learn about multiple routes to a prefix
  * BGP supports multiple rules for selecting the route; one of the most important is local preference or policy
    * If there is no policy preference, then a common criterion is the shortest AS-PATH
    * Following this, a router might choose the closest NEXT-HOP router
* BGP routing policy
  * Ideally, a provider network only wants to carry the traffic generated or received by its customers
  * A customer network never wants to act as a transit; as a result it will only advertise routes to its subnets

### Software-Defined Networking

* The internet network layer was historically implemented via a distributed, monolothic approach

  * Monolithic router: contains switching hardware, runs proprietary implementation of internet standard protocols
  * Different "middleboxes" for different network layer functions (e.g. firewalls, load balancers)
  * Around 2005 there was a renewed interest in rethinking the network control plane

* Per-router control plane

  * Individual routing algorithm components in each and every router interact with each other in the control plane to compute forwarding tables
  * Traffic engineering is difficult with this approach (and certain things just cannot be done at all)

* SDN: Logically centralized control plane

  * A distinct (typically remote) controller interacts with local control agents (CAs)
  * Why a logically centralized control plane?
    * Easier network management: avoid router misconfigurations + greater flexibility of traffic flows
    * Table-based forwarding allows "programming" routers
      * Centralized programming is easier: compute tables centrally and distribute
      * Distributed programming is more difficult: compute tables as a result of distributed algorithm implemented in each and every router
    * Implementation of control plane is open (non-proprietary)

* OpenFlow data plane abstraction

  * Generalized forwarding: simple packet-handling rules

    * Pattern: match values in packet header fields
    * Actions for matched packet: drop, forward or modify the matched packet, or send matched packet to controller
    * Priority: disambiguate overlapping patterns
    * Counters: number of bytes and number of packets

  * Examples:

    ```
    1. src=1.2.*.*, dest=3.4.5.* -> drop
    2. src=*.*.*.*, dest=3.4.*.* -> forward(2)
    3. src=10.1.2.3, dest=*.*.*.* -> send to controller
    ```

  * Abstraction: `match + action`
    * Specifies which action (e.g. drop, forward) should occur when a match is found 