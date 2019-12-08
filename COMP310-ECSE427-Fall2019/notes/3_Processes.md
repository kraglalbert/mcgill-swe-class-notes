# Processes

* An OS executes programs as processes
* **Process:** a program in execution; a process is executed in sequential fashion
* A process consists of multiple parts:
  * The program code, called the **text section**
  * Current activity including program counter and registers
  * The **stack**, which contains temporary data (e.g. function parameters, return addresses, local variables)
  * The **data section**, which contains global variables
  * The **heap**, which contains variables that are *dynamically allocated* at runtime

* One program can be multiple processes

* As a process executes, it can be in different states

  | State          | Description                                                  |
  | -------------- | ------------------------------------------------------------ |
  | **New**        | The process is being created/just created                    |
  | **Running**    | Instructions are currently being executed                    |
  | **Waiting**    | The process is waiting for some event to occur (e.g. waiting on a semaphore) |
  | **Ready**      | The process is ready to run and waiting to be assigned to a processor |
  | **Terminated** | The process has finished execution                           |

#### Process Control Block

* Information associated with each process is stored in a structure called the Process Control Block (PCB)
* The PCB stores:
  * Process state
  * Program counter, which points to the address of the next instruction to execute
  * CPU registers 
  * CPU scheduling information (e.g. priorities, scheduling queue pointers)
  * Memory management information, i.e. the memory allocated to the process
  * Accounting information: CPU used, clock time elapsed since start, time limits
  * I/O device information: I/O devices allocated to the process, list of open files
* If we have threads, then the PCB must be able to store multiple program counters

#### Process Scheduling

* Goal: maximize CPU usage and quickly switch between processes
  * CPU usage: the percentage of time that the CPU spends doing useful works (i.e. executing processes)
* The **process scheduler** selects among ready processes for the next one to execute on the CPU
* Scheduling queues is used to maintain a list of ready processes
  * Ready queue: all processes that are ready to run
  * Waiting queue: all processes that are waiting on some event, e.g. I/O
  * Processes move in and out of the various queues

#### Context Switches

* When the CPU switches from one process to another, a context switch occurs
  * The system must save the state of the old process and load the saved state of the new process
  * The context of a process is represented by the PCB
  * Context switching adds performance overhead; the system does no useful work while switching between processes
  * The level of hardware support affects the time required to complete a context switch

#### Process Creation

* Two ways to create a process: either from scratch or by **forking** from a parent process
* When a fork is done, a **child** process is created
* Processes are identified by their PID (process ID)
* Resource sharing options:
  * Parent and children share all resources
  * Child processes share a subset of the parent's resources
  * Parent and child share no resources
* Execution options:
  * Parent and child execute concurrently
  * Parent waits until the child process terminates
* The child process is a duplicate of the parent (at the moment the fork occurs)
* System calls
  * `fork()`: creates a child process from the current (calling) process
  * `exec()`: executes another program, e.g. `ls` or `cd`
  * `wait()`: forces the parent to wait for the child process to terminate
* Some operating systems do not allow child processes to exist if the parent has terminated
  * A cascading termination is initiated by the OS in this case, where each child, grandchild, etc. process is terminated
* Undesired states for child processes:
  * **Zombie process**: a child process that has terminated but still has an entry in the process table—it is a process in the terminated state. 
    * Why does this happen? The child process's status is needed by the parent so that it can know when the child has terminated, so it needs to stay in the process table. When the parent reads that the child has terminated, it **reaps** the child and removes it from the process table—but this only occurs if the parent is waiting on the child process to finish! If the parent never calls `wait()` the child process will become a zombie when it terminates
  * **Orphan process:** a child process whose parent process has terminated before it, i.e. the parent terminated without calling `wait()`

#### Interprocess Communication

* Processes within a system can be independent or cooperate
* Cooperating processes can affect and be affected by other processes
* Why have cooperating processes?
  * Information/data sharing
  * Computation speedup
  * Modularity
  * Convenience
* In order to have cooperating processes, it is necessary to have a form of interprocess communication
* Two methods exist: message passing and shared memory
  * In shared memory, processes read and write data from the same block of memory
    * Major issue is to ensure the shared memory stays synchronized—this is often achieved with semaphores
  * In message passing, processes read and write data to a message queue
    * Processes can communicate without the use of shared variables
    * Message size can be fixed or variable

#### Producer-Consumer Problem

* An example problem for cooperating processes
* The **producer** writes data to a buffer and the **consumer** reads it
  * An unbounded buffer places no restriction on the size of the buffer
  * A bounded buffer gives the buffer a fixed size

#### Message Passing

* General functions that processes can use:
  * `send(message)`
  * `receive(message)`
* If processes *P* and *Q* wish to communicate, they must establish some sort of communication link between them
* Communication link implementation
  * Physical:
    * Shared memory
    * Hardware bus
    * Network
  * Logical:
    * Direct or indirect
    * Synchronous or asynchronous
    * Automatic or explicit buffering
* Direct communication
  * Processes must name each other explicitly, e.g. `send(P, message)` to send a message to process *P*
* Indirect communication
  * Messages are directed and received from "mailboxes" (known as ports), e.g. `send(A, message)` to send a message to port *A*
  * Each port has a unique ID
  * Processes can communicate only if they share a port
* Message passing can be either blocking (synchronous) or non-blocking (asynchronous)

#### Pipes

* A means for two processes to communicate
* Two types of pipes:
  * Ordinary pipe: created by a process and can only be accessed by that process and its child
    * Often used so that a parent can communicate with a child process that it created
    * Data in written into one end of the pipe and read out the other (unidirectional flow)
    * Can be created with the `pipe()` system call
  * Named pipe: exists as a file and can be used by any processes
    * Can be created with the `mkfifo` command in bash
    * Data flow can be bidirectional

#### Process I/O

* Each process has an array of file descriptors (fd)
  * Each fd corresponds to an external device
* By default, index 0 in the file descriptor table is `stdin`, index 1 is `stdout`, and index 2 is `stderr`
* When we open a file, a new file descriptor is created and it is given the *first available* slot in the file descriptor table starting from index 0
  * Example: say we want to print content to a file. We can achieve this by first closing `stdout` and then opening our desired file, which will now be open in the index of `stdout`
  * `open()` returns the first available file descriptor

#### Address Space

* When a process runs, all the addresses it *could* generate (full address space) belong exclusively to it, i.e. this is not shared with any other process
* However, part of this address space is taken by the kernel
  * The kernel space can be used to communicate with other processes
* When a context switch happens, we must switch the address space as well
* What does the user space portion of a process's address space contain?
  * The stack
  * The heap/dynamic memory allocation
  * Block Started by Symbol (BSS), a section that contains unitialized variables
  * Text section (the program code)
  * Data section
* When a process is forked, the user space address space is cloned but kernel section is not