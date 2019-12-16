# Deadlocks

* Deadlock is a permanent blocking of a set of processes that compete for resources
  * A set of processes are all in a wait state because each process is waiting on a resource held by another (waiting) process
* There is no efficient solution to the deadlock problem in the general case! 

#### Resource Classification

* Resources can be differentiated in two different ways
  * Reusable vs. Consumable
    * **Reusable:** a resource that can safely be used by one process at a time and not be depleted by that use
      * Processes obtain these resources and later release them for reuse
      * Examples: CPU, memory, specific I/O devices, and files
    * **Consumable:** a resource that can be created and destroyed; when a resource is acquired by a process it ceases to exist
      * Examples: interrupts, signals, and messages
  * Preemptable vs. Non-preemptable
    * **Preemptable:** a resource that can be taken away from the process owning it with no ill effects (e.g. memory or CPU)
    * **Non-Preemptable:** a resource that cannot be taken away from the process owning it without causing the program/computation to fail (e.g. printer or floppy disk)
* Deadlocks mostly occur when using reusable and non-preemptable resources

#### Conditions for Deadlock

* In order for deadlock to occur, all four of these conditions need to be met:
  1. **Mutual exclusion:** processes need exclusive access to their resources
  2. **Hold and wait:** processes can wait for resources while holding others
  3. **No preemption:** processes will not give up a resource until they are finished with it
  4. **Circular wait:** each process in the chain holds a resource requested by another
* These four cases only guarantee deadlock if there is **one instance of each resource type**
* If one of these necessary conditions is prevented, then deadlocks cannot occur
  * Various solutions prevent one of the four conditions

#### Strategies for Deadlock

* In general, four strategies exist for mitigating deadlocks:
  * **Ignorance:** pretend there is no problem at all!
  * **Prevention:** prevent deadlocks from ever occurring
  * **Avoidance:** continually analyze the state of the system to see if granting a certain resource request will lead to deadlock
  * **Detection:** let deadlocks occur and detect when they happen so that the system can recover after the fact

#### Deadlock Prevention

* Indirect methods: prevent the occurrence of one of the first three deadlock conditions
* Direct method: prevent the circular wait condition
* The cost of deadlock prevention is often high and adds significant overhead
* Deadlock prevention strategies are very **conservative**: access to resources is restricted and additional restrictions can be imposed on processes
* Deadlock conditions
  * In general, **mutual exclusion** cannot be prevented
  * The **hold and wait** condition can be prevented by requiring that a process requests all of its required resources at the same time, and blocking the process until this request can be granted
  * We can add **preemption** by implementing a restriction where if a process makes a resource request that gets denied, it must release all of its held resources and request them again later
  * We can prevent **circular wait** by defining a linear ordering of resource types; for example, if a process has been allocated resources of type R, it can subsequently only request resources of types that come after R in the ordering

#### Deadlock Avoidance

* All four of the necessary conditions are allowed, so it is possible for deadlocks to occur
* The system evaluates every resource request and denies them if granting it would lead to *potential* deadlock
* Knowledge of future requests and resource needs for every process is required
* Processes can be blocked for a long time!
* The simplest and most useful deadlock avoidance technique requires that each process declares the *maximum* number of each resource type that it may need
  * The system examines current resource allocation to see which **state** it is in
* Safe state
  * If a system is in safe state, then deadlocks cannot occur
* Unsafe state: 
  * If a system is in unsafe state, then there is a possibility that deadlocks can occur
  * The system must ensure that it never enters this state
* If we have just a single instance of a resource type, then we can use a resource-allocation graph
* If we have multiple instances of a resource type, then we can use the **Banker's algorithm**

#### Resource Allocation Graphs

* Resource allocation graphs consist of claim edges, request edges and allocation edges
* Claim edge: $P_i \rightarrow R_j$
  * Indicates that a process may request/wait to access a resource 
  * Represented by a dashed line
  * A claim edge converts to a request edge when a process requests a resource
* Request edge: $P_i \rightarrow R_j$
  * Converted to an allocation edge when the resource is granted to the process
  * Becomes a claim edge when the process releases the resource
  * The edge goes from process to resource and is represented by a solid line
* Allocation edge: $R_j \rightarrow P_i$
  * A solid line that goes from resource to process, indicating the resource has been allocated to that process

* Suppose a process requests a resouce
  * We can only grant the request if converting the request edge to allocation edge does not create a cycle in the graph

#### Banker's Algorithm

* When a process requests a resource, it may have to wait (for a long time)

* Banker's algorithm setup

  * Assume we N processes and M resources
  * We have an availability vector `Avail` that denotes the number of units of each resource that are available to claim
  * Let `Max` be an N by M matrix. Then:
    * $Max_{ij}$ denotes that process $P_i$ will require at most L units of resource $R_j$
    * $Hold_{ij}$ denotes the number of units of $R_j$ that are currently held by $P_i$
    * $Need_{ij}$ denotes how many more units of $R_j$ are required by $P_i$
      * $Need_{ij} = Max_{ij} - Hold_{ij}$ for all $i$ and $j$

* A process makes a request with a vector `REQ`

* Steps to verify if a request is valid

  1. Verify that a process matches its needs

     `if REQ[j] > NEED[i][j] then abort` — this is impossible

  2. Check if the requested amount is available

     `if REQ[j] > AVAIL[j] then abort` — not enough resources of that type available

  3. Update matrices and grant resources if the system is safe 

     ```
     AVAIL[j] = AVAIL[j] - REQ[j]
     HOLD[i][j] = HOLD[i][j] + REQ[j]
     NEED[i][j] = NEED[i][J] - REQ[j]
     ```

* What is safe?

  * The system is **very safe** if `NEED[i] <= AVAIL` for all processes
    * Processes can run to completion in any order
  * The system is **safe** if `NEED[i] <= AVAIL` for *at least one* process
    * There is at least one correct order in which the processes can all run to completion
  * The system is **unsafe** if `NEED[i] <= AVAIL` for at least one process **but** there is no order in which the processes can all run to completion
    * Some processes cannot complete successfully
  * The system is in deadlock if `NEED[i] > AVAIL` for all processes

* Algorithm to determine if state is safe

  1. Initialize `WORK[j] = AVAIL[j]` for all `j`, and `FINISH[i] = false` for all `i`

  2. Find a process $P_i$ such that `FINISH[i] = false` and `NEED[i][j] <= WORK[j]`

     If no such process, go to step 4

  3. `WORK[j] = WORK[j] + HOLD[i][j]`, `FINISH[i] = true`

  4. If `FINISH[i] = true` for all `i` then return true, otherwise return false

#### Deadlock Detection

* This technique does attempt to prevent deadlocks, it lets them happen
* The system detects if any deadlocks have occurred by periodically checking for circular wait
* With deadlock detection, resources should be granted to processes whenever possible
* A check for deadlock can be made as frequently as each resource request, or less frequently if deadlocks are not very likely
* Checking at each resource request is:
  * Good because it leads to early detection and the algorithm is simpler
  * Bad because constant checking has significant overhead (consumes a lot of CPU cycles)
* Deadlock recovery technique
  * Recovery through preemption
    * Take a resource away from its current owning process and give it to another
  * Recovery through rollback
    * If deadlocks are likely, processes can be checkpointed periodically and undo certain work (e.g. undo a transaction in a banking system)
  * Recovery through termintation
    * The simplest and most dangerous solution!
    * Simply terminate one process in the deadlock cycle
    * The results of computations can be irreversibly lost

#### Deadlock Strategy Summary

| Principle  | Resource Allocation Strategy                      | Different Schemes                                    | Advantages                                                   | Disadvantages                                                |
| ---------- | ------------------------------------------------- | ---------------------------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Prevention | Conservative; under-commits resources             | Request all resources at once                        | Good for processes with single burst of activity; no preemption needed | Inefficient; delays process initiation                       |
|            |                                                   | Preemption                                           | Convenient when applied to resources whose state can be saved and restored easily | Preempts more often than necessary; subject to cyclic restart |
|            |                                                   | Resource ordering                                    | Feasible to enforce via compile-time checks; needs no runtime computation | Preempts without immediate use; disallows incremental resource requests |
| Avoidance  | Selects midway between Prevention and Detection   | Manipulate to find at least one safe execution order | No preemption necessary                                      | Future resource requirements must be known; processes can be blocked for a long time |
| Detection  | Very liberal; grant resources as much as possible | Invoke periodically to test for deadlock             | Process initiation is never delayed                          | Losses due to preemption                                     |

