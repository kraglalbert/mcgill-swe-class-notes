# Threads

* Most modern applications are multithreaded
* Threads run *within* a process
* Process creation is heavyweight but thread creation is lightweight
* Process creation:
  * Create new address space at allocation
  * Allocate resources at creation
  * Interprocess communication required in order to share data
  * Deeper isolation for security and fault tolerance
* Thread creation:
  * Same address space as the process it was created in
  * Quicker creation time
  * Sharing through shared memory
  * Fault sharing between all threads within a process
* Each thread gets its own stack and registers, but all code, data and files are shared
* Benefits of threads:
  * Improved responsiveness: it may be possible to continue execution if part of a process is blocked
  * Resource sharing: threads share resource of process, which is easier than either form of IPC
  * Cheaper than process creation, less overhead for thread switching versus context switching
  * Scalability: process can take advantage of multiprocessor architectures
* **Parallelism** implies that a system can execute multiple tasks simultaneously
* **Concurrency** implies that a system can make progress on multiple tasks at a time
  * Possible to achieve with a single core

#### Amdahl's Law

> If we add additional cores to a system, the speedup (i.e. improvement) is limited by the serial portion of a program, i.e. the sections of a program that cannot be parallelized.

* The speedup is often expressed as follows: 

  $speedup \leq \frac{1}{S + \frac{1-S}{N}}$

  * $S$ is the serial portion (a value between 0 and 1)
  * $N$ is the number of cores
  * $1-S$ is the parallel portion

* For exam problems, the following equation is often used:

  $\text{Execution time} = S + \frac{P}{N}$

#### User and Kernel-Level Threads

* A user thread executes code in the user space
  * Can call into the kernel space at any time—still considered a user thread even though it's executing kernel-level code (at higher security levels)
* A kernel thread *only* runs kernel-level code and isn't associated with a user-space process

#### Multithreading Models

- Many-to-one
  - Many user threads mapped to a single kernel thread
  - One thread blocking causes all others to block
  - Multiple threads cannot run in parallel because only one can be in the kernel at a time
  - Few systems currently use this mode
- One-to-one
  - Each user thread maps to a kernel thread
  - Creating a user-level thread creates a kernel thread
  - More concurrency possible compared to many-to-one
  - The number of threads per process can sometimes be restricted due to overhead
- Many-to-many
  - Many user threads are mapped to many kernel threads
  - Allows the OS to create a sufficient number of kernel threads

#### pthreads

* The pthread specification is a POSIX standard API for thread creation and synchronization
* Common in UNIX operating systems
* A thread can be created with `pthread_create(...)`, where one of the arguments is a function for the thread to execute

* A thread terminates for any of the following:
  * The function passed in at thread creation returns
  * The thread calls the `pthread_exit()` function
  * The thread is cancelled using `pthread_cancel()`
  * Any thread calls `exit()` or the main thread returns
* Each thread is uniquely identified by an ID
  * ID comparison can be done to see if two threads are the same
* A thread can wait for another thread using the `pthread_join()` function
  * Similar to processes and waits, if we don't join a (non-detached) thread we will get a zombie thread
* Detaching a thread
  * By default, a thread is joinable
  * If we don't care about a thread's return state we can **detach** it
  * It is not possible to join a detached thread
* Thread attributes can be used to set properties of a thread, e.g. if the thread is detached or not
* Data can be shared between threads with global variables
  * Must ensure that two threads are not modifying a variable at the same time; this can be achieved with a mutex or semaphore
* Thread cancellation: terminating a thread before it has finished executing
  * Asynchronous cancellation: terminate the target thread immediately
  * Deferred cancellation (default): allows the target thread to periodically check if it should be cancelled
  * Invoking thread cancellation *requests* cancellation, but actual cancellation depends on thread state
    * If the thread has cancellation disabled, then cancellation remains pending until the thread enables it