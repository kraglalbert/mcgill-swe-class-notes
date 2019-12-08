# Synchronization

* Concurrent processes can either:
  * Compete with each other for resources
  * Cooperate with each other in sharing global resources
* OS provides mechanisms for both
  * Deals with competing processes by carefully allocating resources and properly isolating processes from each other
  * Deals with cooperating processes by providing mechanisms for sharing resources

#### Competing Processes

* Processes that do not work together cannot affect the execution of each other
  * However, they can compete for resources (e.g. CPU time)
* These processes are deterministic, can proceed at an arbitrary rate, and can be stopped and restarted without side effects

#### Cooperating Processes

* Cooperating processes are aware of each other and can communicate directly (message passing) or indirectly (shared memory)
  * Can affect the execution of each other!
* These processes share a common object or exchange messages, can be non-deterministic, and are subject to **race conditions**

* Cooperating processes present more challenges—why have them?

  * Out of necessity, e.g. sharing a global task queue
  * Take advantange of concurrent processing
  * Problems can be solved with a more modular solution

* Order of instructions matters when dealing with cooperating processes that access data concurrently

  * Example:

    ```c
    /* No issues */
    A = 1 // Process 1
    B = 2 // Process 2
    
    /* This can cause inconsistent results! */
    A = B + 1 // Process 1
    B = B * 2 // Process 2
    ```

#### Race Conditions

> A race condition occurs when two or more processes are running and the output of a computation/program depends on the exact order that instructions were executed.

* We don't want race conditions, so how can we avoid them?
  * Don't allow more than one process to read/write shared data at the same time (exclusive access/mutual exclusion)
* Mutual exclusion
  * When one process is reading or writing some shared data, other processes are prevented from doing so at the same time

#### Critical Sections

> A critical section is a part of a program that accesses shared data.

* If we arrange a program such that no two processes are in their critical section at the same time, then race conditions can be avoided
* Requirements for a correct critical section (has been asked on exams):
  * No two processes can be in their critical sections at the same time
  * No assumptions can be made about the CPU speed or number of CPUs
  * No process running outside of its critical section can block other processes from entering their critical sections
  * No process should have to wait forever to enter its critical section
* If a process enters its critical section, it cannot leave halfway through! That would defeat the entire purpose of a critical section
  * Critical section execution must be **atomic**, i.e. it executes all of the code (or none) inside of it before leaving 
* For efficiency, critical sections should be as short as possible
  * When a process is in its critical section, all other processes need to wait until it finishes
* A strictly software-based solution does not work easily, because the CPU can switch processes at any time
  * A process can leave its critical section halfway through; not atomic
* Busy waiting
  * Continuously testing a variable until it gets a certain value
  * This wastes CPU time and should only be used when the expected wait time is short
  * A lock that uses busy waiting is called a **spin lock**
* **Deadlock** occurs when two or more processes are waiting on each other because a resource they require is held by another process, where that process is waiting on a resource held by another process, etc.
  * No progress can be made if this occurs, forward progress stops for all processes in deadlock
* **Livelock** is a special case of deadlock, which occurs when two or more processes continually repeat the same interactions in response to changes in other processes without doing any useful work
  * Each process gets stuck in a loop and does not make forward progress

#### Peterson's Algorithm

* A purely software implementation of mutual exclusion
* In its original formulation it only works for two processes (exam answer)
  * The algorithm *can* be generalized for more than two processes
* Uses busy waiting (see implementation in `pseudocode` folder)

#### TEST and LOCK Instructions

* `TSL RX, LOCK` is a CPU instruction that provides support for mutual exclusion
  * `RX` is a register
  * Reads the contents of memory location `LOCK` into `RX` and stores a non-zero value at `LOCK`
  * The operation of reading and writing are indivisible, so this operation is atomic
* This is a hardware-level solution
* Advantages:
  * Can work for more than two processes
  * Can be used with a single or multiple processors that **share a single memory**
  * Simple and easy to verify
  * Can be used to support multiple critical sections, i.e. one variable for each section
* Disadvantages:
  * Uses busy waiting (process waiting to get into its critical section consumes CPU time)
  * Can lead to starvation (the selection of entering process is arbitrary when multiple processes are trying to enter their critical sections)
* This can be used in a semaphore implementation for shared variable updates (i.e. ensuring only one process updates the variable at a time)

#### Priority Inversion

* Busy waiting is generally not desirable

* A good example of this is the priority inversion problem:

  > Suppose we have two processes, one with high priority (process H) and one with low priority (process L). Assuming both processes are runnable, the scheduler will opt to run process H since it has higher priority. However, at a certain point process L may be in its critical section when process H becomes runnable; H begins running and does busy waiting to try and enter the critical section, but L is never scheduled to *leave* the critical section, so we get deadlock. 

* An alternative to busy waiting is the sleep/wakeup approach, which is used by semaphores

#### Semaphores

* Semaphores are essentially a counter that can be incremented or decremented in a structured way
* Two general methods are available:
  * `sem.wait()`: attempts to decrease the value of `sem` by 1. However, if decreasing its value would cause the value to become negative, then the function call is blocked until the value of `sem` is greater than zero
  * `sem.signal()`: increases the value of `sem` by 1
  * Both of these operations must be atomic!
* A semaphore can be initialized to a nonnegative value
* A queue is used to hold all of the processes waiting on a semaphore
  * Strong semaphore: the process that has been blocked longest is released first
  * Weak semaphore: release order is not specified
* Semaphores can be **binary** (i.e. have a value of 0 or 1) or they can be **general** (i.e. can have a value of 0 up to any arbitrary number)
  * A binary semaphore can be used as a mutex
  * A general semaphore can be used as a counter for how many units of a critical resource are available

#### Monitors

* A monitor is a high-level *abstraction* that combines and hides the following:
  * Shared data
  * Operations on the shared data
  * Synchronization using **condition variables**
* Semaphores are very low-level, so monitors can be useful as another layer of abstraction
* A monitor ensures that only one process is active within it at any given time
  * Processes enter a queue in order to enter the monitor
* Condition variables allow processes to leave and enter the monitor at arbitrary times, which is often necessary to do
  * Each condition variable has its own entry queue of processes
* Condition variable operations are similar to semaphore operations (but **not** the same)
  * `cond_var.wait()`: suspends the calling process (i.e. makes it leave the monitor) until another process calls `cond_var.signal()`
  * `cond_var.signal()`: resumes one suspended process, and has no effect if no processes are waiting on `cond_var`
* The `signal` operation doesn't make a process leave the monitor, so we could have two processes in the monitor at once—how do we fix this?
  * Hoare's Monitor: 
    * If P executes `x.signal()` and there is a process Q suspended on the condition variable `x`, we have two options
    * Option 1: P waits on some condition until Q leaves the monitor (*signal and wait*)
    * Option 2: Q waits on some condition until P leaves the monitor (*signal and continue*)
  * Hansen's Monitor:
    * P executes `signal` as its last operation, and so it leaves the monitor immediately after

#### Classic Synchronization Problems

* Producer-Consumer problem (and its variations)
* Readers-Writers Problem
  * A data set is shared with a number of concurrent processes
  * Readers only read from the data set and do not modify it
  * Writers can read and write
  * We want to allow multiple readers to have access to the data at the same time, but only one writer can have access at a time
  * Three implementations
    * Option 1: reader threads can starve
    * Option 2: writer threads can starve
    * Option 3: no starvation
* Dining Philosophers Problem
  * 5 philosophers sit at a table with 5 chopsticks and 5 bowls of rice
  * We want to maximize the number of philosophers eating at any given time while also avoiding issues like deadlock
  * A philosopher needs to hold 2 chopsticks to eat, and releases both when done
* Implementations for all 3 can be found in the `pseudocode` folder
* 