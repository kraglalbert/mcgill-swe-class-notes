# Virtual Memory

* So far, we have separated the programmer's view of memory from that of the OS using a mapping mechanism
  * This allows the OS to move user programs around and simplifies sharing of memory between them
* However, we also assumed that a user program had to be loaded completely into memory before it could run
  * Problem: waste of memory, because a program only needs a small amount of memory at any given time
  * Solution: **virtual memory**; a program can run with only some of its logical address space in main memory
* The basic idea with virtual memory is to create an *illusion* of memory that is as large as a disk (in GB) and as fast as memory (in nanoseconds)
* The key principle is *locality of reference*, which recognizes that a significant portion of memory accesses in a running program are made to a subset of its pages
  * i.e. a running program only needs access to a portion of its logical address space at a given time
* With virtual memory, a logical address translates to:
  * Main memory (small but fast), or
  * Paging device on disk (large but slow), or
  * None (not allocated, not used, free)
* The virtual memory (sub)system can be implemented as an extension of paged or segmented memory management (or sometimes as a combination of both)
* In this scheme, the OS has the ability to execute a program which is only *partially loaded* in memory

#### Missing Pages

* What happens when an executing program references an address that is not in main memory?
  * Hardware and software cooperate to solve the problem
  * The page table is extended with an extra bit called the **present bit**
    * Initially all present bits are cleared (on both hardware and software)
  * While doing an address translation, the MMU checks to see if the present bit is set
    * Access to a page whose present bit is not set causes a special hardware interrupt called a **page fault**
    * When a page fault occurs the OS brings the page into memory, sets the corresponding present bit, and restarts execution of the instruction (software side)
  * Most likely, the page carrying the address will be on the paging device, but it possibly does not exist at all!
* The technique of using a present bit can be applied to any non-contiguous memory allocation scheme
* Page fault handling
  * When a page fault occurs, the OS:
    * Marks the current process as blocked (since it is waiting for a page)
    * Finds an empty frame or makes a frame empty in main memory
    * Determines the location of the requested page on the paging device
    * Performs an I/O operation to fetch the page to main memory
    * Triggers a "page fetched" event (special form of I/O completion interrupt) to unblock and wake up the process
  * Since the fourth (and sometimes the second) step involves I/O operations, this operation is typically performed with a special system process (e.g. the `pager` process in UNIX)
* Hardware support
  * Certain hardware instructions can break if the instruction itself and the address of registers are in two different pages, and the latter page is not found in memory
  * In this case, we need additional support from the hardware
  * Different approaches:
    * Partial effects of the faulted instructions are undone and the instruction is restarted aftter servicing the fault
    * The instruction resume from the point of the fault
    * Before executing an instruction, make sure that all referenced addresses are available in memory
  * In practice, some or all of these approaches are used together

#### Basic Policies for Virtual Memory

* The hardware only provides basic capabilities for virtual memory—the OS must make several decisions:
  * **Allocation:** how much real memory to allocate to each (ready) program?
  * **Fetching:** when to bring pages into main memory?
  * **Placement:** where in the memory should the fetched pages be located?
  * **Replacement:** which page should be removed from main memory?
* Allocation policy
  * The allocation policy deals with conflicting requirements:
    * The fewer the frames allocated for a program, the higher the page fault rate
    * The fewer the frames allocated for a program, the more programs can reside in memory—decreasing the need for swapping
    * Allocating additional frames to a program beyond a certain number results in little or only moderate gain in performance
  * The number of allocated pages (also known as resident set size) can be fixed or variable during the execution of a program
* Fetch policy
  * Demand paging (pure)
    * Start a program with no pages loaded, and wait until it references a page
    * Once a page is referenced, load it
    * This is the most common approach used in paging sysems
  * Request paging
    * Similar to overlays, let the user identify which pages are needed
    * Not practical: leads to overestimation and user may not know what to ask for
  * Pre-paging
    * Start with one or more pages pre-loaded
    * As pages are referenced, bring in other (not yet referenced) pages as well
  * Opposite to fetching, the cleaning policy deals with determining when a modified (dirty) page should be written back to the paging device
* Placement policy
  * This policy usually follows the rules about paging and segmentation discussed earlier
  * Given the matching sizes of a page and a frame, placement within paging is straightforward
* Replacement policy
  * FIFO
    * Frames are treated as a circular list, and the oldest page is replaced
    * Belady's Anomaly: in some cases allowing more frames to exist in memory can lead to *more* page faults instead of fewer!
  * LRU
    * The frame whose contents have not been used for the longest time is replaced
  * Optimal algorithm (OPT)
    * The page that will not be referenced again for the longest time is replaced
    * Purely theoretical, but can be useful for comparison
  * Random
    * A frame is selected to replace at random
* More on replacement policy
  * Replacement scope
    * Global: select a victim among all processes
    * Local: select a page from the faulted process
  * Frame locking
    * Frames that belong to the kernel, or that are used for critical purposes, may be locked for improved performance
  * Page buffering
    * Victim frames are grouped into two categories: those that hold unmodified (clean) pages and those that hold dirty pages

#### Clock Algorithm

* All frames, along with a "used" bit, are kept in a circular queue
* A pointer indicates which page has just been replaced
* When a frame is needed, the pointer is advanced to the first frame with a zero used bit
  * As the pointer advances, it clears the used bits
* Once a victim is found, the page is replaced and the frame is marked as used (i.e. its used bit is set to 1)
* The hardware sets the used bit each time an address in the page is referenced
* Some systems also use a "dirty" bit to give preference to dirty pages
  * Why? It is more expensive to victimize a dirty page

#### Thrashing

* The number of processes that are in memory determines the level of multiprogramming
  * The effectiveness of virtual memory management is closely related to the multiprogramming level
* When there are just a few processes in memory, the possibility of processes being blocked (and thus swapped out) is higher
* When there are too many processes in memory, the resident set (number of pages allocated in memory) of each process is smaller
  * This leads to higher page fault frequency, causing the system to exhibit a behaviour known as **thrashing**
  * In other words, the system is spending its time moving pages in and out of memory and not doing much useful work
* The only way to eliminate thrashing is to reduce the multiprogramming level by suspending one or more processes
* Victim processes can be:
  * The lowest priority process
  * The faulting process
  * The newest process
  * The process with the smallest resident set
  * The process with the largest resident set
* Analogy to thrashing: taking too many courses
  * Solution: don't overload yourself

#### More on Virtual Memory

* Virtual memory is critical to most modern computing systems and must be efficient to be useful
* The **working set** of a program is the set of pages that are accessed by the last $\Delta$ memory references at a given time $t$ and is denoted by $W(t, \Delta)$
* Denning's Working Set Principle states that:
  * A program should run if and only if its working set is in memory
  * A page may not be victimized if it is a member of the current working set of any runnable (non-blocked) program
* One problem with the working set approach is that the information about each working set (one per process) must constantly be gathered (e.g. which pages have been accessed in the last $\Delta$ seconds?)
* A solution (along with the clock algorithm) is:
  * Maintain idle time value (amount of CPU time received by the process since last access to a page)
  * Every once in a while (e.g. every few seconds), scan all pages of a process. For each used bit that is set to 1, clear that page's idle time; otherwise add the process's CPU time since the last scan to the idle time
    * Turn off all used bits at the end
  * The collection of pages with the lowest idle time is the working set

#### Page Fault Frequency

* Dealing with the details of working sets usually adds a lot of overhead

  * Instead, an algorithm known as *page-fault frequency* can be used to monitor thrashing

* The page fault rate is defined as:

  $P = \frac{\text{# of page faults}}{\text{specified time period T}}$

  Where $T$ is the critical inter-page fault time

* When a process runs below the lower bound of the page fault rate, a frame is taken away from it (its resident set size is reduced)

  * i.e. number of page fault errors is low, so its safer to take frames away at this point

* Similarly, when a process runs above its upper bound, a frame is added to the process

#### Current Trends

* Larger physical memory
  * Page replacement is less important
  * Less hardware support for replacement policies
  * Larger page sizes
    * Better TLB coverage
    * Smaller page tables, fewer pages to manage
* Larger address spaces
  * Sparse address spaces
  * Single (combined) address space
* File systems using virtual memory
  * Memory-mapped files
  * File caching with VM

#### Copy-on-Write

* Copy-on-write allows both parent and child processes to **initially** share the same pages in memory
  * If either process modifies a shared page, only then is the page copied
  * Example: say two processes (P1 and P2) share 3 pages in memory (Page A, Page B and Page C)
    * If P1 modifies Page C, a copy of Page C will be created, which P1 will now point to
    * P2 will still read from the original Page C
* Copy-on-write allows **more efficient process creation** as only modified pages are copied
* In general, free pages are allocated from a pool of zero-fill-on-demand pages
* The `vfork()` variation of `fork()` has the parent suspend and the child uses copy-on-write address space of parent
  * Very efficient; designed to have the child call `exec()`

#### Allocating Kernel Memory

* Kernel memory is treated differently from user memory
* Often allocated from a free-memory pool
  * Kernel requests memory for structures of various sizes
  * Some kernel memory needs to be contiguous (e.g. for device I/O)
* Buddy System
  * Allocates memory from fixed-size segment consisting of physically-contiguous pages
  * Memory is allocated using a power-of-2 allocator
    * Satisfies requests in units sized as power of 2
    * Request is rounded up to the next highest power of 2
    * When a smaller allocation is needed than is available, the current chunk is split into two "buddies" of equal size (both have size equal to the next-lower power of 2)
  * Example: assume 256 KB chunk is available and the kernel requests 21 KB
    * Split into two segments of 128 KB each
    * One is further divided into 64 KB segments, and then one of those is divided into 32 KB segments, where one is used to satisfy the request
  * Advantage: quickly coalesce unused chunks into a larger chunk
  * Disadvantage: fragmentation
* Slab allocator
  * Alternate strategy to the buddy system
  * A **slab** is one or more physically contiguous pages
  * Cache consists of one or more slabs
  * Single cache for each unique kernel data structure
    * Each cache is filled with objects (instantiations of the data structure)
  * When each cache is created, it is filled with objects marked as `free`
  * When a structure is stored, the object is marked as `used`
  * If slab is full of used objects, the next object is allocated from an empty slab
    * If no empty slabs, a new slab is allocated
  * Benefits include no fragmentation and fast memory request satisfaction
* Slab allocator in Linux
  * For example, process descriptor is of type `struct task_struct`
    * Approximately 1.7 KB of memory
  * For a new task we allocate a new struct from cache
    * Use an existing free `struct task_struct`
  * A slab can be in three possible states:
    * Full, all used
    * Empty, all free
    * Partial, mix of free and used
  * Upon request, the slab allocator:
    * Uses free struct in partial slab
    * If none, takes one from empty slab
    * If no empty slab, create a new empty one

### Pre-paging

* Goal: to reduce the large number of page faults that occur at process startup
* Pre-page all or some of the pages a process will need, before they are referenced
* However, if pre-paged pages are unused, then I/O and memory was wasted
* Assume $s$ pages are pre-paged and $\alpha$ is the fraction of pages used
  * If $\alpha$ is near zero then pre-paging is not worth it

#### Page Size

* Sometimes OS designers have a choice, and page size selection must be taken into consideration
* Things to consider:
  * Fragmentation
  * Page table size
  * Resolution
  * I/O overhead
  * Number of page faults
  * Locality
  * TLB size and effectiveness
* Always a power of 2, usually in the range of $2^{12}$ to $2^{22}$ bytes
* On average, page size is growing over time

#### TLB Reach

* TLB reach is the amount of memory accessible from the TLB
* $\text{TLB Reach} = \text{TLB Size} \times \text{Page Size}$
* Ideally, the working set of each process is stored in the TLB
  * Otherwise, there is a high degree of page faults
* Increasing the page size may lead to an increase in fragmentation, as not all applications require a large page size
* Provide multiple page sizes
  * This allows applications that require larger page sizes the opportunity to use them without an increase in fragmentation

#### I/O Interlock

* Pages must sometimes be locked into memory
* Pages that are used for copying a file from a device must be locked from being selected for eviction by a page replacement algorithm
* A page is **pinned** so that it is locked into memory