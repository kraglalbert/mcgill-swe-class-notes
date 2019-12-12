# Virtual Machines

* Multi-threading
  * We have virtual CPUs that give the illusion that enables threads to persist their computation on the CPU as switching happens (without clobbering each other)
* Multi-processing
  * Adds memory virtualization to multi-threading
  * Each process has its own memory view
* File system is shared and so is the kernel
  * Kernel is a protected resource!
  * In a perfect setup, we don't need to worry about this sharing
* Fundamental idea
  * Abstract the hardware of a single computer into several different execution environments
    * Similar to layered approach
    * Layers create virtual machine (VM) on which operating systems or applications can run on
  * Components:
    * **Host:** the underlying hardware system
    * **Hypervisor/Virtual Machine Manager (VMM):** creates and runs virtual machines by providing an interface that is identical to the host machine (except in paravirtualization)
    * **Guest:** process provided with a virtual copy of the host
      * Usually an OS
  * A single physical machine can run multiple operating systems concurrently, each in its own virtual machine

#### Implementations of VMMs

* Type 0 Hypervisor
  * Hardware-based solutions that provide support for VM creation and management via firmware
* Type 1 Hypervisor
  * OS-like software built to provide virtualization
  * Also includes general-purpose operating systems that provide standard functions as well as VMM functions
  * Hypervisor is directly above the hardware
* Type 2 Hypervisor
  * Applications that run on standard operating systems but provide VMM features to guest operating systems
  * Hypervisor sits on top of the host OS
* Other variations include:
  * **Paravirtualization:** technique in which the guest operating system is modified in cooperation with the VMM to optimize performance
    * Presents a software interface to the VMs which is similar, but not identical to the  underlying hardware-software interface
    * The goal is to reduce the portion of the guest's execution time spent performing operations which are *substantially more difficult* to run in a virtual environment compared to a non-virtualized environment
  * **Programming-environment virtualization:** VMMs do not virtualize real hardware but instead create an optimized 
    * Used by Java and .NET
  * **Emulators:** allow applications written for one hardware environment to run on a very different hardware environment, such as a different CPU
  * **Application containment:** not virtualization at all but rather provides virtualization like features by segregating applications from the operating system, making them more secure and manageable

#### Benefits and Features

* The host system us protected from VMs, and VMs are protected from each other
  * e.g. a virus is less likely to spread
  * Sharing is provided through shared file system volume and network communication
* A running VM can be frozen or **suspended**
  * Then, it can be moved or copied somewhere else and resumed
  * When suspended we are given a snapshot of the VMs current state, and we are able to restore it back to that state
    * Some VMMs allow multiple snapshots per VM
  * We can **clone** a VM by creating a copy and then running both the original and the copy
* VMs are great for OS research and better system development efficiency
* Allows for multiple, different operating systems to be run on a single machine
* Templating
  * Create an OS plus application VM, provide it to customers, and can use it to create multiple instances of that combination
* Live migration
  * Move a running VM from one host to another
  * No interruption of user access
* All of these features combined give us **cloud computing**
  * Using APIs, programs tell cloud infrastructure (servers, networking, storage) to create new guests, VMs, and virtual desktops

#### Building Blocks

* Generally difficult to provide an exact duplicate of an underlying machine
  * This is especially true if only dual-mode operation is available on the CPU
  * However, this is getting easier over time as CPU features and support for VMM improves
  * Most VMMs implement virtual CPU to represent the state of CPU per guest, as the guest believes it to be
    * When guest context is switched onto the CPU by the VMM, information about the virtual CPU is loaded and stored
* Requirements: a VM is an efficient, isolated duplicated of a real machine
* Sensitive instructions
  * Control-sensitive instructions
    * Affect the allocation of resources available to the virtual machine
    * Change the processor mode without causing a trap
  * Behaviour-sensitive instructions
    * The effect of execution depends upon location in real memory or on processor mode
  * Privileged instructions
    * Cause a fault in user mode
    * Work normally in privileged mode
* For any conventional third-generation computer, a VMM may be constructed if the set of sensitive instructions for that computer is a *subset* of the set of privileged instructions
* Trap and emulate
  * Dual mode CPU means guest executes in user mode
  * Kernel runs in kernel mode
  * It is not safe to let the guest kernel run in kernel mode as well
    * The VM needs two modes—virtual user mode and virtual kernel mode
    * Both of these run in the real user mode
  * Actions in the guest that normally cause a switch to kernel mode must instead cause a switch to virtual kernel mode
* How does the switch from virtual user mode to virtual kernel mode occur?
  * Attempting a privileged instruction in user mode causes a trap (error)
  * VMM gains control, analyzes error, executes operation as attempted by guest
  * Returns control to guest in user mode
  * This process is known as **trap-and-emulate**
  * Most virtualization products use this (at least in part)
* User mode code in guest runs at the same speed as if not a guest
* Kernel mode, privileged code runs slower due to trap-and-emulat
  * This is especially a problem when there are multiple guests running
* Some CPUs add hardware support and CPU modes to improve virtualization performance

#### Binary Rewriting

* Rewrite kernel binaries of guest operating systems
  * Replace sensitive instructions with hypercalls (done dynamically)
* Privileged code is run via a binary translator
  * Translated code is cached
  * Usually translated just once
  * Done by VMware
* Implemented by translation of code **within the VMM**
  * Once the instruction is translated it then gets executed
* Code reads native instructions dynamically from guest, on demand, and generates native binary code that executes in place of original code
* The performance of this method would be poor without optimizations
  * Products such as VMware use caching

#### Paravirtualization

- Sensitive instructions are replaced with hypervisor calls
  - Traps to VMM
- VM provides higher-level device interface
  - Guest machine has no device drivers