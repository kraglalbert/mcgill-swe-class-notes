# Network Security

1. [Introduction](#introduction)
2. [Public Key Cryptography](#public-key-cryptography)
3. [Authentication](#authentication)
4. [SSL](#ssl)
5. [Securing LAN](#securing-lan)
6. [Firewalls](#firewalls)

### Introduction

* Principles of network security

  * Confidentiality
  * Authentication
  * Message integrity
  * Access + Availability (e.g. of servers)

* Security in practice

  * Firewalls + intrusion detection systems
  * Security in application, transport, network and link layers

* Confidentiality

  * Sender encrypts message, receiver decrypts message

* Authentication

  * Sender and receiver can confirm each others' identities

* What can a bad actor do?

  * Eavesdrop/intercept messages
  * Actively insert messages into the connection
  * Impersonation: can spoof a source IP address in a packet (or spoof any field in general)
  * Hijacking: take over ongoing connection by removing the sender or receiver and inserting themselves in their place
  * Denial of service: prevent a service from being used by others

* The language of cryptography

  * Let $m$ be a plaintext message
  * $K_A(m)$ is the encrypted message, encrypted with key $K_A$
  * $m = K_B(K_A(m))$
  * $K_B$ is a decryption key

* As a general example, we assume Alice and Bob are two parties trying to communicate securely and Trudy is a bad actor that is trying to do malicious things

* Breaking an encryption scheme

  * Ciphertext-only attack: Trudy has cipher text she can analyze
    * Brute force: search through all keys
    * Statistical analysis
  * Known-plaintext attack: Trudy has the plaintext corresponding to the ciphertext 
    * Only effective against simple ciphers; not effective against modern ciphers
  * Trudy needs to know the encryption algorithm in order to know what the keys look like

* Symmetric key cryptography

  * Bob and Alice share the same key $K_s$
  * How can Bob and Alice agree on a shared key without anyone else knowing what it is?

* Simple encryption scheme

  * Substitution cipher: substitute each letter for another
    * Encryption key: mapping of 26 letters to 26 letters

* A more sophisticated encryption approach

  * $n$ substitution ciphers: $M_1, M_2..., M_n$
  * A cycling pattern; e.g. for $n=4$: $M_1, M_3, M_4, M_3, M_2, M_1, M_3, M_4, M_3, M_2 ...$

  * For each new plaintext symbol, use the next substitution pattern in the cyclic pattern
  * Encryption key: $n$ substitution ciphers and cyclic pattern

* AES: Advanced Encryption Standard

  * A symmetric-key encryption standard


### Public Key Cryptography

* People migrated to this since you don't need to secretly figure out a shared key as in symmetric-key encryption

* Sender and receiver do **not** share their private keys

* Public encryption keys are known to all

* Private decryption key is known only to the receiver

* $m = K^-_B(K^+_B(m)) = K^+_B(K^-_B(m))$

  * Here $K^-_B$ is Bob's private key and $K^+_B$ is Bob's public key

* Given a public key, it should be impossible to compute the corresponding private key

* RSA public key encryption algorithm

  * Recall:

    $[(a \text{ mod } n) \cdot (b \text{ mod } n)] \text{ mod } n = (a \cdot b) \text{ mod } n$

    $(a \text{ mod } n )^d \text{ mod } n = a^d \text{ mod } n$

  * Getting ready
    * Message: just a bit pattern (64-bit)
    * Each bit pattern can be uniquely represented by an integer number
    * Encrypting a message is equivalent to encrypting a number

* RSA algorithm steps:

  1. Choose 2 large (1024-bit) prime numbers $p, q$
  2. Compute $n = pq$, $z = (p-1)(q-1)$
  3. Choose $e$ with $e < n$ such that $e$ has no common factors with $z$ (relatively prime)
  4. Choose $d$ such that $ed-1$ is exactly divisible by $z$ (i.e. $ed \text{ mod } z = 1$)
  5. Public key is then $(n,e)$ and the private key is $(n,d)$

* Encrypting and decrypting messages with RSA

  * Given $(n,e)$ and $(n,d)$:
    * Encrypt: $c = m^e \text{ mod } n, (m < n)$
    * Decrypt: $m = c^d \text{ mod } n$

* Example

  * Bob chooses $p=5$ and $q=7$
  * Then $n=35$ and $z = 24$
  * $e=5$ so that $e,z$ are relatively prime
  * $d = 29$
  * Encrypting 8-bit messages:
    * $m=12$, $m^e = 24,832$
    * $c = m^e \text{ mod } n = 17$
  * Decrypt:
    * $c^d = \text{ huge number}$
    * $m = c^d \text{ mod } n = 12$

* Why does RSA work?

  * Must show that $c^d \text{ mod } n = m$, where $c = m^e \text{ mod n}$

  * Fact: for any $x$ and $y$, $x^y \text{ mod } n = x^{(y \text{ mod } z)} \text{ mod } n$

    * Where $n = pq$ and $z = (p-1)(q-1)$

  * Thus:

    $c^d \text{ mod } n = (m^e \text{ mod } n)^d \text{ mod } n$

    $= m^{ed} \text{ mod } n$

    $= m^{(ed \text{ mod } z)} \text{ mod } n$

    $= m^1 \text{ mod } n$

    $= m$

  * Why is $m = K^-_B(K^+_B(m)) = K^+_B(K^-_B(m))$ true?

    * Because:

      $(m^e \text{ mod } n)^d \text{ mod } n = m^{ed} \text{ mod } n$

      $= m^{de} \text{ mod } n$

      $= (m^d \text{ mod } n)^e \text{ mod } n$

* Why is RSA secure?

  * Suppose you know Bob's public key $(n,e)$. How hard is it to determine his private key?
  * Essentially you would need to find factors of $n$ without knowing the 2 factors of $p$ and $q$
    * Factoring a large number is a NP-complete problem, so it is computationally infeasible

* RSA in practice: session keys

  * The expontentiation in RSA is computation expensive
  * DES is at least 100 times faster
  * In practice, public key cryptography should be used to establish a secure connection; once that is done a second (symmetrical) key can be used to encrypt the data
  * Use RSA to exchange a symmetric key $K_s$

### Authentication

* When designing an authentication scheme, we must assume Trudy can intercept all messages on the network
* Goal: avoid a playback attack
  * This can be done by using a **nonce**
    * A nonce is a number that should be generated/used "once-in-a-lifetime"
    * This ensures some sort of uniqueness in every message/packet
* To prove Alice is live, Bob sends a nonce $R$. Alice must return $R$ encrypted with the shared secret key
  * This is not foolproof
  * Vulnerable to a man-in-the-middle (MITM) attack
* A better approach is:
  * Bob sends $R$ 
  * Alice returns $K^-_A( R)$
  * Bob asks for Alice's public key
  * Alice returns $K^+_A$
  * Bob verifies that $K^+_A(K^-_A( R)) = R$ 
  * However, this is still vulnerable to a MITM attack!
* Man-in-the-middle attacks
  * If Trudy were to execute a MITM attack, she would insert herself into the connection between Alice and Bob, pretending to be Bob when receiving messages from Alice and pretending to be Alice when receiving messages from Bob
  * Can be difficult to detect
  * How could it be executed?
    * Trudy gets $R$ from Bob and returns $K^-_T( R)$ to Bob
    * Bob asks Trudy for the public key and she sends him $K^+_T$; as far as Bob can tell nothing is wrong
    * Trudy receives $K^+_T(m)$ from Bob which she can then decrypt with her private key to see the message contents
    * Trudy also pretends to be Bob to Alice; she gives her $R$, gets $K^-_A( R)$ in response, asks for Alice's public key, and then gets $K^+_A$
    * Trudy can then send $m$ to Alice encrypted with Alice's public key
* How are MITM attacks mitigated?
  * With **certificates**
  * Instead of getting Alice to send her public key, we ask her to send her digital certificate which is verified by an external entity (e.g. Verisign)
  * A certificate is harder to forge but could still be untrustworthy
  * Without a certificate there's no way to know who's public key you're actually getting
* Digital signatures
  * Cryptographic technique analogous to handwritten signatures
  * The sender digitally signs a document, establishing that he/she is the document owner
  * Digital signatures must be verifiable and non-forgable
* A simple digital signature for a message $m$: encrypt $m$ with your private key
  * This has high computational complexity
  * The signature will be very large for large $m$
  * We can instead encrypt a fixed-length version of the message if we hash it
    * Danger: collisions!
    * Collisions aren't really an issue as long as there is no way for an attack to figure out which messages map to the same hash
  * Final result: $K^-_B(H(m))$
* Digital signatures recap
  * Suppose Alice receives message $m$ with signature $(m, K^-_B(m))$
  * Alice verifies that $m$ is signed by Bob by applying Bob's public key to $K^-_B(m)$ to check that $K^+_B(K^-_B(m)) = m$
  * Alice thus verifies that:
    * Bob signed $m$
    * No one else signed $m$
    * Bob signed $m$ and not $m'$
* A checksum is an example of a bad hash function since it's easy to find alternate messages that give the same result
* Public key certification
  * Certification Authority (CA): binds public key to a particular entity E
  * E (e.g. a person, router) registers its public key with the CA
    * E provides proof of identity to the CA
    * CA creates certificate binding E to its public key
    * The certificate is digitally signed by the CA; CA says "this is E's public key"
* Now we should ask Bob for his certificate then decrypt it using the CA's public key (which is trusted)
  * This is how a MITM attack can be prevented
* Securing an email
  * Encrypt message with $K_s$ (for efficiency)
  * Also encrypt $K_s$ with Bob's public key so that it is not sent in plaintext over the network
  * Send both $K_s(m)$ and $K^+_B(K_s)$ to Bob
  * Add a digital signature, treating the email contents as message $m$
* Hash functions are standardized and known to all
  * When a secure connection is established, a hash function is agreed upon by the 2 parties
  * When receiving a digital signature that encrypted a hashed message, the receiver needs to hash the message themselves and compare that with the decrypted signature
* Final secure email
  * Produce $K^-_A(H(m))$; feed $m, K^-_A(H(m))$ into encryption with $K_s$
  * Combine above with $K^+_B(K_s)$
  * Assuming Alice is the sender, she uses 3 keys: her private key, Bob's public key, and the newly-created symmetric key

### SSL

* Short for Secure Sockets Layer

  * Variation: TLS (Transport Layer Security)

* SSL sits between the application and transport layers

  * Provides an API to applications

* Recall: congestion control (network) vs. flow control (receiver)

* A simple version of SSL

  * **Handshake:** Alice and Bob use their certificates and private keys to authenticate each other and exchange shared secret
  * **Key derivation:** Alice and Bob use shared secret to derive a set of keys
  * **Data transfer:** data to be transformed is broken up into a series of records
  * **Connection closure:** special message to securely close the connection

* We can't use the scheme we saw earlier for encrypting emails since it treats the message as one block of data

  * We would not be able to authenticate until after all data is received

* Simple handshake

  * MS: Master Secret; EMS: Encrypted Master Secret
  * MAC: Message Authentication Code
  * Want to avoid using the same key for more than one cryptography operation
  * Four keys:
    * $K_c$: encryption key for data sent from the client to server
    * $M_c$: MAC key for data sent from the client to server
    * $K_s$: encryption key for data sent from the server to client
    * $M_s$: MAC key for data sent from the server to client
  * These keys are derived using a key derivation function (KDF)
    * The KDF takes in the master secret and (possibly) some additional random data and creates the keys
  * MAC is used to verify that the data was sent by the correct person

* Why not encrypt data in constant streams as we write it to TCP?

  * Where would we put the MAC bits? If at the end, then no message integrity until all data is processed

  * Instead, break streams into a series of records

    * Each record carries a MAC
    * Receiver can act on each record as it arrives

  * Record format (variable-length data):

    ```
    length |      data      | MAC
    ```

  * This way the receiver knows where the MAC bits are

* Toy sequence numbers

  * Problem: an attacker can capture and replay records or re-order records
  * Solution: put the sequence number in the MAC
    * $MAC = mac(M_x, sequence||data)$
  * Attacker could still replay *all* records though
    * Solution: use a nonce

* Control information

  * Problem: truncation attack

    * An attacker could forge a TCP connection-close segment
    * One or both sides thinks there is less data than there actually is

  * Solution: record types

    * `0` for data record, `1` for closure

      ```
      length | type |      data      | MAC
      ```

    * Now $MAC = mac(M_x, sequence||type||data)$

* SSL cipher suite
  * Public key algorithm
  * Symmetric encryption algorithm
  * MAC algorithm
* SSL supports several cipher suites
  * Negotiation: client and server aggree on a cipher suite
    * The client offers one or more choices and the server picks one
* Common SSL symmetric ciphers
  * DES: Data Encryption Standard (block)
  * 3DES: Triple Strength DES (block)
  * RC2: Rivest Cipher 2 (block)
  * RC4: Rivest Cipher 4 (stream)
* SSL public key encryption: RSA

* Real SSL handshake
  * Server authentication
  * Negotiation: agree on cryptography algorithms
  * Establish keys
  * Client authentication (optional)

### Securing LAN

* WEP (Wired Equivalent Privacy) design goals

  * Symmetric-key cryptography
    * Confidentiality
    * End host authentication
    * Data integrity
  * Self-synchronizing: each packet is separately encrypted
  * Efficient

* Nowadays WPA2 is used

* Symmetric stream ciphers

  ```
  key -> keystream generator -> keystream
  ```

  * Combine each byte of keystream with each byte of plaintext to get the ciphertext
    * $m(i)$: $i^{th}$ unit of message
    * $ks(i)$: $i^{th}$ unit of keystream
    * $c(i)$: $i^{th}$ unit of ciphertext 
    * $c(i) = ks(i) \oplus m(i)$
    * $m(i) = ks(i) \oplus c(i)$

* Stream cipher and packet independence

  * Design goal: each packet should be separately encrypted
  * If for frame $n+1$ we use the keystream from where we left off for frame $n$, then each frame is not separately encrypted (we would need to know where we left off for frame $n$)
  * WEP approach: initialize keystream with key + new IV (initialization vector) for each packet
    * This allows for frames to be decrypted independently

* Weakness of WEP 802.11

  * Used a 24-bit IV, which lead to IVs being re-used relatively frequently
  * Since the IV is sent in plaintext, reuse is easy to detect
  * Attack:
    * Trudy causes Alice to encrypt known plaintext message
    * Trudy sees $c_i = d_i \oplus k_i^{IV}$
    * Trudy knows $c_i$ and $d_i$ so she can compute $k_i^{IV}$
    * The next time this IV is used, Trudy can decrypt the message!

* 802.11i improved the security of WEP

### Firewalls



