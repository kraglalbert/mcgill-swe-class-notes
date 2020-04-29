# Peer to Peer Architectures

1. [Architecture Comparison](#architecture-comparison)
2. [BitTorrent](#bittorrent)

### Architecture Comparison

* Say we want to distribute a file from a server to N clients

  * In a client-server architecture, each client can download the file from the server

  * In a peer-to-peer architecture, clients can download from the server, but can also download (portions of) the file from other clients
  * We usually assume that download rates are considerably higher than upload rates

* Client-server file distribution time

  * Server sequentially sends N copies
    * $NF/u_s$ time
    * $u_s$: server upload bandwidth
  * The download time taken by each client depends on its bottleneck link which governs the maximum transfer rate
  * We can bound the distribution time as the maximum of two quantities
    * The server has to upload $NF$ bits, so it will take $NF/u_s$ seconds to do this
    * The slowest client has a download rate of $min(d_i)$. It will take $F/min(d_i)$ seconds to transfer  the file to this client
    * This increases **linearly** with N
    * $d_{cs} \geq max\{ \frac{NF}{u_s}, \frac{F}{min(d_i)} \}$

* P2P file distribution time

  * The server has to send at least one copy into the network. This takes $F/u_s$ seconds
  * Each client requires $F/d_i$ seconds to download the file. Thus the transfer time will be at least $F/min(d_i)$
  * Finally, there is a total upload of $NF$ bits to the P2P network
  * The upload rate of the entire network is $u_s + \sum u_i$, so the transfer will take at least $NF/(u_s + \sum u_i)$

  * This increases **sublinearly** with N
  * $d_{p2p} \geq max\{ \frac{F}{u_s}, \frac{F}{min(d_i)}, NF/(u_s + \sum u_i) \}$

* In practice, there is significant coordination overhead with P2P networks

  * Achievable efficiency can be diminished due to unreliable clients
  * If a client becomes disconnected, all of the bits that it was supposed to upload will need to be uploaded by the server or another client
  * It takes time to discover that a client has been disconnected and also to determine which client (or server) should take over

* It often won't be the case that the server upload speed is the bottleneck

### BitTorrent

* Commonly-used P2P file sharing protocol
* A **swarm** is a group of peers exchanging chunks of a file
* A **tracker** is a server that keeps track of the peers participating in the swarms
* Files are divided into pieces (chunks)
* A peer joining the swarm:
  * Will have no pieces, but will accumulate them over time
  * Registers with tracker to get a list of peers
  * Connects to subset of peers (its neighbours)
    * The tracker determines who the neighbours will be
* Periodically, the peer notifies the tracker whether it is still participating in the swarm
* When the peer joins initially it is a "leech" since it has no pieces to upload
  * Once it retrieves some pieces, it begins uploading them and changes role to become a "seed"
* At any given time, different peers have different subsets of file pieces
  * Periodically, a peer asks each neighbour for the list of pieces that they have
  * Issues requests for the rarest missing pieces first
    * This is to eliminate bottlenecks in sharing
* Each peer sends pieces to 5 other peers
  * 4 of these are selected based on the rates at which they download data to the peer
  * These "top 4" are providing the peer with the best service, so the peer aims to reciprocate
    * Top 4 are reevaluated every 10 seconds
  * This tit-for-tat mechanism encourages peers with similar capabilities to exchange data
  * The remaining peer is randomly chosen every 30 seconds
  * The peer "optimistically unchokes" another peer, uploading a piece without having necessarily received any data
    * This allows new peers to acquire pieces

