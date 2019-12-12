# Secondary Storage

* What is secondary storage?
  * A non-volatile repository for (user and system) data and programs
* The file system manages secondary storage (part of the OS)
* What are the main requirements for file systems?
  * Should provide persistent storage
  * Handle large information (i.e. large chunks, large numbers)
  * Concurrent access

#### Files

* A file is a named collection of related information
  * Typically a sequence of bytes
* A file can be viewed in two different ways:
  * Logical View: as the users see it
  * Physical View: as it actually resides on secondary storage
* Files are intended to be non-volatile and long-lasting
  * Also intended to be moved around/copied, and accessed by different programs and users
* Each file has a collection of information known as **attributes**
  * Name, owner, creator
  * Type (e.g. source, data, binary)
  * Location (e.g. I-node or disk address), 
  * Organization (e.g. sequential, indexed, random)
  * Access permissions
  * Time and date
  * Size

#### Directories

* A directory is a **symbol table** that can be searched for information about files
  * Implemented as a file itself
* Directories give a namespace for files to be placed 
  * i.e. two files with the same name cannot exist in the same directory, but they *can* exist in different directories
* A **directory entry** contains information about a file
  * Entries are added when files are created and removed when files are deleted
* Common directory structures
  * Single-level (flat)
    * A directory that is shared by all users
  * Two-level
    * One level for each user—some form of user login is required in order to identify each user
  * Tree
    * Arbitrary tree structure for each user (login required)
* UNIX uses a DAG structure for directories (basically a tree)

#### File Systems

* The basic services of a file system include:
  * Keeping track of files (i.e. knowing their location)
  * I/O support, especially the transmission mechanism to and from main memory
  * Management of secondary storage
  * Sharing of I/O devices
  * Providing protection mechanisms for information held on the system

* The file system layout consists of the Master Boot Record (MBR), the partition table, and each separate disk partition
  * The MBR is located in the first sector of any hard disk, and it identifies where the OS is located so that it can be loaded into the computer's main memory and booted up
    * General flow: read the MBR, read the partion table, determine active partition, read and execute the boot block
  * A disk partition contains a boot block, a super block, I-nodes, some sort of free space management and the files/directories themselves

#### File Operations

* File sharing raises the issue of protection
* One approach is to provide controlled access to files through a common set of operations (i.e. read, write, delete, list, append)
* One popular protection mechanism is to use an access list, which can be divided into user, group and other
  * An access list defines an access level for each entry in the list (e.g. a user)
* Basic file operations
  * Create
    * File is created with no data and some file attributes are set 
  * Write
    * Data is written to the file starting from the position of the file's write pointer
    * If the end of the write goes past EOF, then the file's size increases
  * Read
    * Data is read starting from the position of the file's read pointer
  * Delete
    * The file is removed from the file system and its storage is marked as available
  * Seek (reposition read/write pointer for a given file)
    * Repositions the file pointer for random access
  * Open
    * Opens a file, i.e. fetch the attributes and list of disk addresses for the file into main memory
    * File is available for reading and writing
  * Close
    * Removes the file from the per-process and global open file table
    * File is removed from main memory

#### File Access Methods

* The information stored in a file can be accessed in different ways
  * **Sequential:** in order, one record after another
    * Convenient with sequential access devices (e.g. magnetic tape)
  * **Direct (Random):** in any order; records can be skipped
  * **Indexed:** in any order, but accessed using particular keys (e.g. hash table)

#### File Allocation

* Files need to be allocated in a smart way, otherwise we can waste time and space (on the secondary storage)
* Common file allocation methods are:
  * Contiguous
  * Chained (linked)
  * Indexed
* A balance must be struck between block size and data rate (kB/sec)—as block size increases the data rate increases but disk space utilization decreases
* Contiguous allocation
  * Blocks must be allocated contiguously, or they will not be allocated at all
    * e.g. if we need to create a file with 5 data blocks, we need 5 blocks that are contiguous
    * It's possible that there are 5 blocks available, but if they are not all contiguous we cannot use them!
  * Advantages
    * Easy sequential and random access
    * Simple, few seeks required
  * Disadvantages
    * External fragmentation
    * May not know the file size in advance
  * Must maintain a free list of unused blocks on the disk
* Chained (linked) allocation
  * Each directory entry contains the starting block for each file
  * Each block points to the next block for that file (essentially a linked list)
  * Advantages
    * No external fragmentation
    * Files can grow easily, any available blocks can be used
  * Disadvantages
    * Lots of seeking
    * Random access is difficult (must start at first block and traverse the linked list to get to blocks in the middle of the file)
  * Can be enhanced with an in-memory **file allocation table (FAT)**
    * Essentially an array of block numbers, where the value at each index points to a different index in the table
    * An index can also have an EOF value
* Indexed allocation
  * Allocate an array of pointers during file creation, and fill this array as new blocks are assigned
  * Used by UNIX
  * Advantages
    * Small internal fragmentation
    * Easy sequential and random access
  * Disadvantages
    * Lots of seeks if the file is large
    * Maximum file size if limited by the size of a block

#### Free Space Management

* Since disk space is fixed, it is necessary to reuse space as it becomes available (e.g. when a file is deleted)
  * The file system needs a mechanism to keep track of where the free space is
* The following techniques for free space management exist:
  * Bit vectors
  * Linked lists (chains)
    * Single list of a set of free block lists
  * Indexing (single or multiple levels)
* Bit vectors
  * Use 1 bit per block on the disk
    * If the bit is set to 1, then that block is available; otherwise it is allocated
* Chains
  * Must storethea pointer to the start of the free block list in a well-known location (e.g. block 0)
  * Each block in the list points to another block that is available
    * Note that links are not necessarily ordered by block address
* Indexed
  * Maintain a table with each index pointing to the starting block of an available (contiguous) set of blocks
  * Each entry in the table can contain the length (i.e. number of blocks) of the available section
  * The table could be ordered by the sizes of the available block sections

#### Finding Free Space

* It is necessary in any file system to be able to find free space; how this is done depends on which free space management technique we use
* Bit vector
  * Search for enough 1 bits
* Indexed
  * Store the number of available blocks in each index entry and order index by size
* Chained
  * First fit
    * Find the first block on the chain big enough to accomodate the request
  * Best fit
    * Find the region on the chain nearest in size but at least as large as the request
  * Worst fit
    * Allocate from the largest region on the chain
  * Next fit
    * Similar to first fit, but continue searching on the chain from where you last left off

#### UNIX File System Layout

* In traditional UNIX systems, each disk partition would contain the following:
  * The boot block
    * Usually contains bootstrap code to boot the system
  * The super block
    * Contains critical information about the layout of the file system (e.g. number of I-nodes and number of disk blocks)
  * I-nodes
    * Each I-node contains all file attributes for a file (except its name)
    * The first I-node points to the block containing the root directory of the file system

* There are three different ways to access a file
  * File descriptor table
    * One per process; keeps track of open files
    * Stored in memory only
  * Open file table
    * One per system; keeps track of *all* files that are currently open
    * Stored in memory only
  * I-node table
    * One per system; keeps track of all files
    * Stored on disk and can be cached in memory

#### Disk Drives

* Access time for a disk
  * Head movement from current position to desired cylinder/track: **seek time**
  * Disk rotation until the desired sector arrives under the head: **rotational latency**
  * Disk rotation until the sector has passed under the head: **data transfer time**
* By processing requests in different ways, we can minimize these different access times

#### Disk Scheduling

* Strategy: minimize the time spent on disk seeking for data so that we can maximize the time spent reading and writing data
* Disk scheduling strategies (based on request process):
  * Random
    * Select from pool of requests randomly
    * Worst-performing strategy
    * Can be useful as a benchmark for analysis
  * FIFO (first-come-first-served)
    * Fairest strategy, no starvation
    * Requests are honored in the order that they came in
    * Works well for few processes
    * Approaches performance of the random strategy as the number of processes competing for the given disk increases
  * Priority
    * Access to disk is not controlled by the disk management software
    * Based on the execution priority of each process
    * Designed to meet job throughput criteria and *not* to optimize disk usage
  * LIFO
    * Stack-style; service the most recently arriving request first
    * Can be useful for processing sequential files
    * Chances of starvation are high; once a process falls behind it can only be serviced once every process ahead of it finishes
* Disk scheduling strategies (based on the requests themselves)
  * Shortest service time first (SSTF)
    * Select the request requiring the shortest seek time
    * In general better average seek time than FIFO (though not guaranteed)
    * A random tie breaker is used (if needed) to decide in which direction to move
  * SCAN (back and forth over disk)
    * The head moves in the same way as an elevator
    * Move in only one direction until the last track is reached, then reverse direction
    * The **LOOK** variation will reverse the direction early if no more requests are present in the current direction
    * No danger of starvation
    * Biased *against* the most recently used area of the disk (bad!)
      * Does not exploit locality as well as SSTF or LIFO
    * Often similar to SSTF
  * C-SCAN (circular SCAN, one way scan)
    * Scanning is done in one direction only; when the last track is reached the head is quickly reset back to the first track
    * Reduces the maximum delay for new requests
  * C-LOOK (circular LOOK)
    * Scan in one direction and go back to the starting track as soon as there are no more requests ahead of the disk head