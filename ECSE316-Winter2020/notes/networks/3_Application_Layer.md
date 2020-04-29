# Application Layer

1. [Review Question](#review-question)
2. [Web and HTTP](#web-and-http)
3. [Cookies](#cookies)
4. [Caching](#caching)
5. [Domain Name System](#domain-name-system)
6. [DNS Caching and Updating Records](#dns-caching-and-updating-records)

### Review Question

* Suppose N packets arrive simultaneously at a link

  * No packets are currently in the queue
  * Each packet has length L bits
  * The link has a transmission rate of R bits per second
  * The link is 150 km long (assume $2\times 10^8$ m/s propagation speed)

* Let $d_i$ be the queueing delay experienced by the $i^{th}$ packet in the queue (where $i=1$ is the first packet and $i=N$ is the last)

* The transmission delay is $L/R$ seconds

* Then, the queueing delay experienced by:

  * The first packet is $d_1 = 0$
  * The second packet is $d_2 = L/R$
  * The $i^{th}$ packet is $d_i = (i-1)L/R$

* Therefore the average queueing delay is:

  $\frac{1}{N} \sum_{i=1}^N (i-1)L/R$

  $= \frac{L}{NR} \sum_{i=1}^N (i-1)$

  $= \frac{L(N-1)}{2R}$ seconds

### Web and HTTP

* Some jargon

  * A web page consists of objects
  * An object can be HTML, CSS, an image, etc.
  * A web page consists of a base HTML file which includes several referenced objects
  * Each object is addressable by a uniform resource locator (URL)

* HTTP

  * The web's application layer protocol
  * Client-server model
  * HTTP is **stateless**: server maintains no information about past client requests
    * Protocols that maintain state are complex
    * Past history must be maintained, and a client/server's view on state may be inconsistent if one of them crashes

* Non-persistent connections

  * HTTP/1.0: server parses request, responds, closes TCP connection
  * 2 round-trip times (RTTs) to fetch object
    * TCP connection
    * Object request/transfer

  * Each transfer suffers from TCP's initially slow sending rate
  * Many browsers open multiple connections in parallel

* Persistent connections

  * Default since HTTP/1.1
  * Same TCP connection: server parses request, responds, parses new request...
  * Client sends requests for all referenced objects as soon as it receives the base HTML
  * Fewerr RTTs and less overhead

* Pipelining in persistent connections

  * Persistent without pipelining
    * Client issues new request only when the previous response has been received
    * Each request consumes an additional 1 RTT (plus data transfer time)
  * Persistent wiith pipelining
    * Default in HTTP/1.1
    * Client can request multiple objects in 1 RTT
    * Server sends the objects one after the other
    * As little as 1 RTT for all referenced objects

* HTTP/2.0

  * Major revision in 2015 (RFC 7540)
  * Used by approximately 30% of the top 10 million websites
  * Goals:
    * Maintain high compatibility with 1.1
    * Decrease latency to improve page load speed
  * Binary framing
    * Headers and data encoded in binary
  * Stream
    * Bidirectional flow of bytes consisting of one or more messages
    * Can prioritize a stream or make it dependent on another
  * Single TCP connection with multiple *bidirectional* streams
  * Each stream has an identifier and optional priority
  * All connections are persistent (only one connection per origin)

### Cookies

* A way to keep track of user state (since HTTP itself is stateless)
* Four components
  * Cookie header line in HTTP response message
  * Cookie header line in HTTP request message
  * Cookie file kept on user's host, managed by user's browser
  * Backend database at website
* Cookies can be persistent or set to expire after some amount of time

### Caching

* Goal: don't send object if cache has up-to-date cached version
* Cache: specify date of cached copy in HTTP request
  
* e.g. `if-modified-since: <date>`
  
* Server response contains no object if cached copy is up-to-date

  * Receive either `304 NOT MODIFIED` or `200 OK` along with the new object (if modified)

* Web caches (proxy server)

  * Goal: satisfy client request without involving origin server
  * Browser sends all HTTP requests to cache
    * If the object is in the cache, then return it
    * If not, then the cache requests the object from the origin server and returns it to the client, and also adds it to the cache
  * The cache is a "middle-man" server
  * Caches are necessary since repeatedly retrieving popular objects from a remote server leads to an undesirable (and unnecessary) use of bandwidth 
    * Leads to latency in servicing requests
  * We assume the cache is "close" to the client (e.g. in the same network)
  * Caches reduce the response time and decrease the amount of traffic to and from remote servers
  * The access link from an institutional ISP can often be the bottleneck link and it is therefore desirable to reduce the traffic that contends for the link's resources

* Web caching example

  <center><img src="./images/3_Example_Problem_1.jpg" style="width:55%"/><center/> 


  * Assumptions
    * Average object size: 100,000 bits
    * Average request rate: 15/sec
    * Delay from public access router to origin server and back: 2 seconds
  * Consequences
    * Utilization on LAN: 15%
    * Utilization on access link: 100%
    * Total delay: internet delay + access delay + LAN delay
    * Total delay = 2 seconds + minutes + milliseconds
  * If access link is changed to 10 Mbps, then utilizatin drops to 15% and total delay becomes (2 seconds + milliseconds + milliseconds)
    * This is often a costly upgrade

* Example problem: institutional network connected to the Internet

  * Avg. object size requested: 100,000 bits
  * Avg. request rate: 10/second
  * Access link: 2 Mbps
  * Model access delay as $\frac{D}{1-BD}$
  * $D =$ avg. time to send object over access link (average size/link rate, $L/R$)
  * $B = $ arrival rate of objects 
  * Avg. internet delay: 0.5 seconds (round-trip, from router/switch at other side of access link and back)
  * What is the total average response time T?
    * $B=10$
    * $D = 100,000/2,000,000 = 0.05$
    * Avg. access delay: $\frac{D}{1-BD} = 0.1$ seconds
    * $T = 0.5 + 0.1 = 0.6$ seconds
  * If we install a cache with hit rate 1/2 on the institutional side of the access link, what is the new response time T'? (Assume zero delay if object is in cache)
    * $B'=5$ (since only half of the objects travel through the access link)
    * New average access delay: $\frac{D}{1-B'D} = 66.7$ milliseconds
    * $T' = \frac{1}{2} \times 0 + \frac{1}{2}(500+66.7) = 283.4$ milliseconds

### Domain Name System

* DNS provides the critical role of converting domain names into IP addresses

* Distributed database implemented in hierarchy of many **name servers**

  * Centralized system would not be able to scale

* **Application-layer protocol** that allows host, routers and name servers to communicate to resolve names (address/name translation)

* DNS is an example of intelligence being pushed out to the edges of the network

* DNS name servers

  * No server has all name-to-IP address mappings
  * Local name servers:
    * Each ISP/company has a local (default) name server
    * A DNS query first goes to the local name server
  * Authoritative name server
    * For a host: stores that host's IP address and name
    * Can *always* perform name/address translation for that host's name

* If client wants to resolve a query (e.g. `www.facebook.com`), the steps are (approximately):

  1. Client queries local server, but doesn't get a resolution
  2. Local DNS server queries root server to find top-level domain server (i.e. `.com`)
  3. Local DNS server queries the TLD server to get `facebook.com` DNS server
  4. Local DNS server queries `facebook.com` DNS server to get IP address for `www.facebook.com`
  5. Local DNS server returns IP address to client

  * This is approximate since (in practice) very few queries will go to the root DNS servers (or even the TLD servers)
  * There are numerous intermediate DNS servers and one of these can usually provide an address for the ISP or enterprise DNS server

* The root name servers are at the top of the database hierarchy

  * Contacted when the intermediate DNS servers cannot resolve an address
  * Provide a pointer to top-level-domain DNS servers
  * 13 in the world

* Top-level-domain (DNS) servers

  * Responsible for `.com`, `.org`, `.net`, etc. as well as all top-level country domains (e.g. `.ca`, `.fr`)

* Authoritative DNS servers

  * Organization's name servers
  * Provide aothoritative hostname to IP mappings for organization's servers (e.g. web and email)

* Local name servers

  * Does not strictly belong to hierarchy
  * Each ISP (residential ISP, company, university) has one
  * When a host makes a DNS query, the query is sent to its local DNS server
    * Acts as a proxy that forwards the query into the DNS hierarchy

* Iterative vs. recursive DNS queries

  * Recursive: 
    * Puts burden of name resolution on the contacted name server
    * Uncommon and generally undesirable because too much work is being done by the relatively few servers near the top of the hierarchy
  * Iterative:
    * Contacted name server replies with the name of server to contact
    * "I don't know this name, but ask this server"

### DNS Caching and Updating Records

* Once any name server learns a mapping, it caches the mapping
* Cached entries timeout (disappear) after some time
  * Expiry time is determined by the TTL field in a DNS response
* TLD servers are typically cached in local name servers
  * This means root servers are rarely visited
  * Cached for a long time (many hours)

* In 2002, a DDoS attack briefly interrupted 9 root DNS servers (for about 1-3) hours. What was the effect?
  * No major service disruption, since most requests don't get relayed to root servers
  * If the attack had persisted for many hours, then there would have been a more serious effect
  * Requests would have been directed to the remaining 4 root servers, resulting in delays due to overload
* Inserting records into DNS
  * Example: new startup "Network Utopia"
  * Register name `networkutopia.com` at *DNS registrar*
    * Must provide names and IP addresses of authoritative name server (primary and secondary)
    * Registrar inserts two resource records into `.com` TLD server
  * Create authoritative server type A record `www.networkutopia.com` and type MX record for `networkutopia.com`
* 