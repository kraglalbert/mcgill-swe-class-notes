# CPU Scheduling

* What is scheduling?
  * Suppose we have some jobs to run on a resource
  * Scheduling finds a sequence of jobs that optimizes a certain criterion (or multiple criteria)
  * Scheduling refers to a set of policies and mechanisms to control the sequence of jobs (work) to be performed by a system
* What is allocation?
  * Allocation determines which jobs get to use which resources
  * Scheduling only determines the order of execution
* What is the goal of CPU scheduling?
  * For a single CPU, we often want to maximize CPU utilization, minimize job response times, and maximize throughput
* What information do we need?
  * Resources
    * Assume only one CPU
    * We have I/O resources, and memory is used in a shared manner
  * Jobs
    * We need to know the number of processes
    * Process creation and termination rate
    * How long a process runs
    * How long a process uses CPU vs. I/O
  * Other issues
    * Scheduling policies and priorities

#### Nature of Processes

* Processes alternate between **CPU bursts** and **I/O bursts**
* A process is said to be **CPU-bound** if it primarily uses CPU
* A process is said to be **I/O-bound** if it primarily uses I/O

#### CPU Scheduler

* The **short-term scheduler** selects among the processes in the ready queue and allocates the CPU to one of them
* CPU scheduling decisions can be made when:
  * A process switches from running to waiting state (non-preemptive)
  * A process switches from running to ready state (preemptive)
  * A process switches from waiting to ready state (preemptive)
  * A process terminates (non-preemptive)

#### Evaluating Schedulers

* We can use the following criteria to evaluate a job scheduler:
  * CPU utilization
  * Throughput
  * Turnaround time
  * Waiting time
  * Response time
  * Fairness
  * Deadlines
* CPU utilization
  * Defined as the capacity used divided by the total capacity
  * Ranges from 100% to 0%
* Efficiency
  * Useful work divided by total work
* Throughput
  * Defined as the number of jobs completed per unit time (e.g. per second)
* Turnaround time
  * The time taken to complete a job, including the time where the job was not actively being run
  * Sum of waiting + running times
* Waiting time
  * The total time spent by the job waiting in the ready queue
* Service time
  * The total time spent servicing the job (i.e. actually running it)
* Response time
  * Time elapsed between the time of the first response and the arrival of the job

#### Scheduler Design

* How do we design a scheduler?
  * Select one or more primary performance criteria and rank them in order of importance
  * Some criteria may not be independent of each other
* Scheduling approaches
  * Policies
    * Preemptive or non-preemptive
  * Non-preemptive
    * The scheduler does not interrupt a running process
    * The current process runs until it blocks (waiting for an event or resource) or until it terminates
    * Common algorithms: First-Come-First-Served, Shortest Job First
    * Good for background batch jobs
  * Preemptive
    * Scheduler forces the current process to release the CPU
    * Eviction of current process occurs at a clock or I/O interrupt
    * Common algorithms: Round-Robin, Priority-based
    * Good for foreground, interactive jobs

#### Scheduling Approaches

* First-Come-First-Served (FIFO)
  * The simplest and fairest scheduling policy
  * Arriving jobs are inserted into the back of the ready queue
  * Jobs to be executed next are removed from the head of the queue
  * Performs better for long jobs
  * Problems:
    * A long CPU-bound job can hog the CPU and prevent other jobs from running
    * Short (I/O-bound) jobs have to wait for long periods of time
    * Can lead to the convoy effect, where jobs continually pile up in the ready queue
* Shortest Job First (SJF)
  * Selects the job with the shortest (expected) processing time first
  * Shorter jobs are always executed before long jobs
  * Problems:
    * Need to know or estimate the processing time of each job
    * Long running jobs can starve when there are a lot of short-running jobs
  * Preemptive SJF is called Shortest Remaining Time First (SRTF)
  * Can have high processing overhead
* Round-Robin
  * Periodically preempts a running job
  * CPU suspends the current job when its time slice expires
  * The job is rescheduled after all other ready jobs are executed at least once
  * Efficiency depends on time slice length
    * If too short, the CPU will spend too much time context switching
    * If too long, interactive jobs will suffer
  * Not suitable for long batch jobs, but good for interactive jobs
  * Medium processing overhead
* Priority-based
  * Each process is assigned a priority
  * The process with highest priority is chosen to run first (lower number = higher priority)
  * Preemption allows a new process to run once a higher-priority process enters the ready queue
  * As a process stays in the ready queue, its priority increases the longer it waits (prevents starvation)

#### Scheduler Classification

* Long-term scheduling
  * Done when a process is created
  * Controls the degree of multiprogramming
* Medium-term scheduling
  * Involves suspend/resume processes by swapping them in or out of memory
* Short-term scheduling
  * Occurs most frequently
  * Picks which job to execute next

#### Metered Processors

* Each thread has a meter, which rruns only when the thread is running in the processor
* At every clock tick:
  * Give processor to thread that's had the least processor time (as shown on its meter)
  * In case of a tie, the thread with lower ID wins
* Problems:
  * Some threads may be more important than others
  * New threads can enter the system, what should their meter value be?
  * Threads can block for I/O and synchronization
* Mafia variation
  * Each thread pays a bribe
    * The greater the bribe, the slower the meter runs
  * To simplify bribing, you buy **tickets**
    * One ticket gives a fair meter
    * Two tickets get a meter running at half speed, three at 1/3 speed, etc.
  * Each thread has a (possibly crooked) meter, which still only runs when the thread is running on the processor
  * At every clock tick:
    * Give the processor to the thread that's had the least processor time as shown on its meter
    * In case of tie, lower thread ID wins

