# Introduction

### Networks: Intro and Architecture

* The physical internet

  * Millions of connected computing devices
  * hosts = end systems (running network apps)

  * Communication links
    * The **bandwidth** is the transmission rate
  * Routers forward **packets** (chunks of data)

* Network edge: applications and hosts

* Network core: interconnected routers and "network of networks"

* The network edge

  * End systems (hosts)
    * Run application programs, e.g. Skype, web, email
    * At the "edge" of the network
  * Client/server model
    * Client host requests, receives service from always-on server
  * Peer-to-peer (P2P) model
    * Minimal (or no) use of dedicated servers
    * e.g. Skype, BitTorrent

* The network core

  * Mesh of interconnected routers
  * Carry "long haul" traffic from source to destination
  * Substantially more bandwidth
  * Packet-switching
    * Long messages are split into packets
    * Forward packets from one router to the next along path from source to destination
    * Each packet is transmitted at the full link capacity