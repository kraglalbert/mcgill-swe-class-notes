# OS Structures

* Operating systems provide a) an environment for execution of programs and b) services to programs and users
* One set of OS services provides functions that are helpful to the user:
  * Almost every OS has a user interface
  * The system must provide functionality for program execution, i.e. loading a program into memory, running it, and ending execution either normally or abnormally
  * The OS must provide a mechanism for file manipulation
  * The OS must allow processes to communicate (via shared memory or message passing)
  * The OS needs to constantly be aware of potential errors
* Another set of OS services ensures the efficient operation of the system itself
  * Resources must be allocated in an effective way
  * Access to system resources should be controlled, i.e. protected
* System call implementation
  * Typically there is a number associated with each system call
  * The **system-call-interface** maintains a table that is indexed according to the number of each system call
  * The system-call interface invokes the intended system call in the kernel and returns the status of the call plus any return values
  * The system-call interface is the divider between user and kernel mode
* To pass parameters to a system call, one of three approaches can be used:
  * Pass the parameters in registers
  * Pass the parameters in a block of memory, and pass the address to the block in a register
  * Push the parameters onto the stack and pop them off after
* The **linker** combines object files into the final executable/binary
  * Also links in any libraries that are required
  * The compiler produces object files
* A program resides on secondary storage as a binary executable; the **loader** is required in order to bring it into memory and execute it
  * Executable files have standard formats so the OS knows how to load and start them
  * A program is given its final addresses once it is loaded into memory (relocation)
* Each operating system has its own unique system calls, and so different versions of an application must be made in order to run on each OS

#### Monolithic Structure (Original UNIX)

* The original UNIX OS had limited structuring and consisted of two separate parts:
  * System programs
  * The kernel
    * Consists of everything below the system-call interface

#### Layered Architecture

* In this design, the OS is divided into layers, which are built on top of each other
* The bottom layer is the hardware and the top layer is the user interface
* The goal is to make the design modular; each layer should only use services provided by the layers below it

#### Microkernels

* Goal: move as much content from the kernel into the user space
* Communication takes place between user modules using message passing
* Benefits:
  * Easier to extend
  * Easier to port the OS to new architectures
  * More reliable (since less code is running in kernel mode)
  * More secure
* Drawbacks:
  * More performance overhead due to the frequent communication between user space and kernel space