# Virtualization and Containers

* Virtual machines (VMs):
  * Emulate a computer system
  * Provide a computer system from within the scope of another system
* What does virtualization provide?
  * It makes entire machines "shippable" (OS, system software, application stack)
* Virtualization can give shared infrastructure users (e.g. cloud users) the impression of having their own machine
* Types of virtualization
  * System virtualization
    * Also known as full virtualization
    * Hosts an entire OS
  * Process virtualization
    * Provides the isolation of system virtualization while also sharing common software tools with the underlying platform
* chroot
  * Goal: treat a directory within the file system as another root directory
  * Allows sharing of system and kernel-level features
* Java
  * JVM abstracts hardware details (compile once, run anywhere)
* Hypervisor types
  * Type 1 Hypervisor (Bare Metal)
    * Hypervisor sits directly on top of the hardware; applications sit on top of the hypervisor
  * Type 2 Hypervisor
    * OS separates hypervisor and hardware

#### Containers

* A set of processes running on top of a common kernel
* Isolated from each other; cannot impact each other or the host system
* How can we guarantee isolation?
  * Namespaces: enable processes to have a private view of the system resources
    * Network interfaces, process IDs, mount points
  * Control groups
    * Allow system to control access to system resources
    * Meter (rate limit)
    * Limit (only allow access at particular times)
    * Restrict (protect sensitive resources)

#### Docker

* Docker is an orchestration tool for containers with several features:
  * Portability
  * App-centric
  * Builds from source
  * Versioning
  * Component reuse
  * Public registry (Docker Hub)
  * Tool ecosystem
* Before containers came along, memory and CPU was used very inefficiently
* Containers result in isolated services in fewer VMs and VMs being used more efficiently
* Traditional development
  * Assume we are developing a Python app
  * First, the developer needs to have a Python runtime on their machine
  * The choice of Python runtime for your development machine imposes constraints on the runtime that can be used in production
  * Traditionally these constraints are listed in a README file or release notes
* Docker-based development
  * Development requirements, execution platform, dependency stack, and application code all evolve together
  * Portable **images** define execution platform 
  * Defined using **Dockerfiles**
  * Images are instantiated as **containers**
    * A container is simply a running instance of an image