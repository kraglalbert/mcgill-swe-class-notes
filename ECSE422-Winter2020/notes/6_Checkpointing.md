# Checkpointing

1. [Introduction](#introduction)
2. [Checkpointing Variations](#checkpointing-variations)
3. [Checkpointing Improvements](#checkpointing-improvements)
4. [Checkpointing in Distributed Systems](#checkpointing-in-distributed-systems)

### Introductions

* Computers today are much faster, but applications are also more complicated
* Some applications which still take a long time:
  * Database updates
  * Fluid-flow simulation
  * Optimization, i.e. optimal deployment of resources by industry (e.g. airlines)
* When execution time is very long, both the probability of failure during execution and cost of failure become significant

* Checkpointing definition
  * A **checkpoint** is a snapshot of the entire state of the process at the moment it was taken
  * Checkpoint is saved on stable storage of sufficient reliability
  * Disks are most commonly used, since they can hold data even if power is interrupted and are also cheap
  * Checkpoints can be very large (tens or hundreds of megabytes)
  * RAM with a battery backup is also used as a stable storage
  * No medium is perfectly reliable, but the reliability must be sufficiently high for the application at hand
* Overhead and latency of checkpoint
  * Overhead: incrrease in execution time (of application) due to taking a checkpoint
  * Latency: time needed to save a checkpoint
  * In a simple system, overhead and latency are identical
  * If part of checkpointing can be overlapped with function execution, then overhead may be substantially smaller than latency
* Checkpoint size depends on how many things (e.g. variables) need to be saved

### Checkpointing Variations

* Issues in checkpointing
  * At what level (user/kernel/application) should we checkpoint?
  * How transparent to the user should checkpointing be?
  * How many checkpoints should we have?
  * At which points during the program execution should we checkpoint?
  * How can we reduce checkpointing overhead?
  * How do we checkpoint distributed systems with/without a central controller?
  * How do we restart the computation at a different node if necessary?
* Checkpointing at the kernel level
  * Transparent to user; no changes to program
  * When system restarts after failure, the kernel is responsible for managing recovery operation
  * Every OS takes checkpoints when a process is preempted
    * Process state is recorded so that execution can resume from interrupted point without loss of computational work
  * However, most operating systems have little or no checkpointing for fault tolerance
* Checkpointing at the user level
  * A user-level library provided for checkpointing
    * Application programs are linked to this library
  * Like kernel-level checkpointing, this approach generally requires no changes to application code
  * Library also manages recovery from failure
* Checkpointing at the application level
  * Application is responsible for all checkpointing functions
  * Code for checkpointing and recovery part of application
  * Provides greatest control over the checkpointing process
  * Disadvantage: expensive to implement and debug
* Comparing checkpoint levels
  * Information available to each level may be different
  * For example, user and application levels do not have access to information held at the kernel level
    * Cannot assign process IDs, which can be a problem
  * User and application levels may not be allowed to checkpoint parts of the file system
    * May have to store names and pointers to appropriate files
* Uniform checkpoint placement
  * We assumed that checkpoints are placed uniformly along the time axis, but is this optimal?
  * If the checkpointing cost is the same, regardless of when the checkpoint is taken, then the answer is *yes*
  * If checkpoint size (and cost) vary from one part of the execution to the other, the answer is often *no*

### Checkpointing Improvements

* Buffering
  * Processor writes checkpoint into main memory and then returns to the executing application
  * Direct memory access (DMA) is used to copy checkpoint from main memory to disk
    * DMA requires CPU involvement only at the beginning and end of operation
  * Further refinement: copy-on-write buffering
  * No need to copy portions of process state that are unchanged since last checkpoint
  * If process does not update main memory pages too often, most of the work involved in copying pages to a buffer area can be avoided
* Copy-on-write buffering
  * Most memory systems provide memory protection bits (per page of physical main memory) which indicate whether the page is read-write, read-only or inaccessible
  * When checkpoint is taken, protection bits of pages belonging to process are set to read-only
  * Application continues running while checkpoint pages are transferred to disk
  * If application attempts to update a page, an access violation is triggered 
  * System then buffers page, and permission is set to read-write
  * Buffered page is later copied to disk
  * This is an example of **incremental checkpointing**
* Incremental checkpointing
  * Involves recording only the *changes* in process state since the previous checkpoint was taken
  * If these changes are few, then less has to be saved per incremental checkpoint
  * Disadvantage: recovery is more complicated
    * Must rebuild system state by examining a succession of incremental checkpoints
* Reducing checkpointing overhead: memory exclusion
  * Two types of variables that do not need to be checkpointed:
    * Those that have not been updated, and
    * Those that are "dead"
  * A dead variable is one whose present value will never again be used by the program
  * Two kinds of dead variables
    * Those that will never again be referenced by the program, and
    * Those for which the next access will be a write
  * The challenge is to accurately identify these dead variables
* Reducing latency
  * Checkpoint compression: less written to disk
  * How much is gained through compression depends on:
    * Extent of compression: can vary between 0% and 50%
    * Work required to execute the compression algorithm (done by CPU); adds to checkpointing overhead as well as latency
  * In simple sequential checkpointing where the overhead and latency are similar, compression may be beneficial
  * In more efficient systems where the overhead is much smaller than the latency, compression may be less useful
  * Incremental checkpointing also reduces latency

### Checkpointing in Distributed Systems

* In a distributed system, we have processors and associated memories connected by an interconnection network
  * Each processor may have local disks
  * Can be a network file system accessible by all processors
* Processes are connected by directional channels—point-to-point connections from one process to another
  * Assume channels are error-free and deliver messages in the order received
* Process/channel/system state
  * The state of channel at time *t* is:
    * The set of messages carried by it up to time *t*
    * The order in which they were received
  * State of distributed system: aggregate states of individual processes and channels
  * State is consistent if, for every **message delivery** there is a corresponding **message-sending** event
  * A state violating this (i.e. a message delivered that had not yet been sent) violates causality
    * Such a message is called an orphan
  * The converse—a system state reflecting sending of a message but not its receipt—is consistent
* Consistent/inconsistent states
  * Assume we have two processes $P$ and $Q$
  * Each has two checkpoints: $CP_1, CP_2, CQ_1, CQ_2$
  * A message *m* is sent from $P$ to $Q$ between the first and second checkpoints of each process
  * Checkpoint sets representing consistent system states:
    * $\{CP_1, CQ_1\}$: neither checkpoint knows about *m*
    * $\{CP_2, CQ_1\}$: $CP_2$ indicates that *m* was sent; $CQ_1$ has no record of receiving *m*
    * $\{CP_2, CQ_2\}$: $CP_2$ indicates that *m* was sent; $CQ_2$ indicates that it was received
  * $\{CP_1, CQ_2\}$ is **inconsistent**
    * $CP_1$ has no record of *m* being sent, but $CQ_2$ records that *m* was received
    * *m* is an orphan message
* Recovery lines
  * A consistent set of checkpoints forms a **recovery line**
    * Can roll the system back to these checkpoints and restart from there
  * Example: $\{CP_1, CQ_1\}$
    * Rolling back $P$ to $CP_1$ undoes the sending of *m*
    * Rolling back $Q$ to $CQ_1$ means that $Q$ now has no record of *m*
    * Restarting from this recovery line will send *m* again
  * Example: $\{CP_2, CQ_1\}$
    * In this case, $P$ will not re-send *m*
    * The recovery process has to be able to play back *m* to $Q$
    * Can add it to the checkpoint of $P$, or
    * Have a separate message log which records everything received by $Q$
* Useless checkpoints
  * Checkpoints are useless if they will never form a recovery line
* The domino effect
  * A single failure can cause a sequence of rollbacks that send every process back to its starting point
  * Happens if the checkpoints are not coordinated either directly (through message passing) or indirectly (by using synchronized clocks)
* Lost messages
  * Suppose a process rolls back to a checkpoint after receiving a message from another process
  * All activity associated with having received the message is now lost
    * Not as severe as orphan messages, since the message can just be retransmitted
  * If process $Q$ sent an acknowledgement of the sent message to $P$ before rolling back, then the acknowledgement would be an orphan message unless $P$ also rolls back to the appropriate checkpoint
* Another problem that can arise in distributed systems is **livelock**
* Coordinated checkpointing
  * Uncoordinated checkpointing may lead to the domino effect or livelock
* A  coordinated checkpointing algorithm
  * Two types of checkpoints: tentative and permanent
  * Process $P$ records its state in a tentative checkpoint
  * $P$ then sends a message to set $\hat{P}$—all processes from whom it received a message since its last checkpoint
  * $Q$ is asked to take a tentative checkpoint recording sending $m_{qp}$ (if not already in the checkpoint)
  * If all processes in $\hat{P}$ (that need to) confirm taking a checkpoint as requested, then all the tentative checkpoints are converted to permanent checkpoints
  * Otherwise, $P$ and all others in $\hat{P}$ abandon their checkpoints
* Time-based synchronization
  * Orphan messages cannot happen if each process checkpoints at the same time
  * Time-based synchronization: processes are checkpoointed at previously agreed times
  * Not enough to avoid orphan messages—clock skews and message communication times are not zero
  * Example:
    * Each process is checkpointing at local time 1100
    * Skew between the two clocks: $P_0$ (sender) may checkpoint much earlier than $P_1$ (receiver) according to real time
    * As a result, $P_0$ sends a message to $P_1$ after its checkpoint, received by $P_1$ before its checkpoint, leading to a potential orphan message
* Preventing creation of an orphan message
  * Suppose that skew between any two clocks is bounded by a value $\delta$
  * Each process checkpoints when its local clock reads time *t*
  * Process remains silent during $[t, t+\delta]$ (local clock)
  * Guarantees all other processes took a checkpoint
  * If inter-process message delivery time has a lower bound $\epsilon$, then the process needs to remain silent during a shorter interval $[t, t+\delta-\epsilon]$
  * If $\epsilon > \delta$, then there is no need for the process to remain silent
* Other uses of checkpointing
  * Process migration
    * Migrating a process from one process to another means moving checkpoint, and resuming computation on new processor—can be used to recover from permanent or intermittent faults
    * Nature of checkpoint determines whether new processor must be same model and run the same OS
  * Load-balancing
    * Better utilization of a distributed system by ensuring that the computational load is appropriately shared among the processors
  * Debugging
    * Core files are dumped when a program exits abnormally—these are essentially checkpoints
  * Snapshots
    * Observing the program state at discrete epochs; deeper understanding of program behaviour