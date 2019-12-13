# Cloud Deployment

* Cloud computing
  * Shared pools of configurable computing resources
  * Can be rapidly provisioned (semi-)automatically
  * Can *elastically* scale up or down based on demand
* What is the business case for cloud computing?
  * Shares underutilized compute resources
  * If you maintain your own compute resources, you must estimate your needed capacity
  * Cloud providers automatically scale applications based on demand
  * You only pay for what you use!
* The business case *against* cloud computing
  * Offsite hosting introduces risk
  * Security, privacy and otherr concerns about data and IP shared in the cloud are real
  * Big cloud companies could be doing anything with your data
* Despite this, cloud computing has taken over
  * Cloud computing is present in all aspects of the modern computing experience
    * Consumer environments (desktop, mobile)
    * Development environments
* Different flavours of cloud computing
  * Traditional cloud computing
    * Infrastructure as a Service (IaaS)
    * Platform as a Service (PaaS)
    * Software as a Service (SaaS)
  * Modern variants
    * Serverless
    * Function as a Service (FaaS)
    * Mobile backend as a Service (MBaaS)

#### Infrastructure as a Service

* What is it?
  * A service that provides **computing resources** to users
  * Typically the computing resources are in the form of VMs or containers
* Business models
  * Typical plans include:
    * Compute hours per month
    * Per-machine "T-shirt" sizing (i.e. small, medium, large)
* Examples: AWS EC2, DigitalOcean, Google Cloud Platform, Microsoft Azure
* IaaS vs. in-house infrastructure
  * Pros:
    * Explainable infrastructure operation costs
    * IT support is offloaded to the cloud provider
    * Elastic scaling
  * Cons:
    * Data privacy and security may be difficult to verify
    * Costs can grow in unexpected ways

#### Platform as a Service

* What is it?
  * A service that provides a **development platform** to users
  * The platform provides users with a comfortable application development entry point
  * Exposes configuration options relevant for developers while hiding irrelevant options
* Business models
  * Wall clock hours of availability per month
  * Per-machine T-shirt sizing
* Examples: Heroku, Microsoft Azure, Google Cloud Platform

* PaaS vs. IaaS
  * Pros:
    * Devs can focus on app development instead of (irrelevant) infrastructure configuration
    * Platforms are typically tuned to near-optimal settings for most application workloads
  * Cons:
    * Convenience at the cost of configurability; non-standard workloads may suffer
    * Platform lock-in; difficult to port applications to other platforms

#### Software as a Service

* What is it?
  * The entire stack (infrastucture, platform, execution environment) is hosted on the cloud and delivered over the web
  * Usually involves a "thin client" that connects to a backend that delivers most of the functionality
* Business models
  * Public user plans (typically free)
  * Business user plans (typically a monthly rate depending on usage/capacity and number of company users)
* Examples: Google Suite (Drive, Sheets, etc.)

#### Serverless

* What is it?	
  * A varient of IaaS where capacity planning decisions are made by the **provider** rather than the customer
  * Customers are charged for the resources that their applications use and nothing else
  * Elastic scalability is at the application level rather than the VM level
* Business models
  * Similar to IaaS, except that charges are calculated based on the resources that the application requires
* Serverless vs. IaaS
  * Pros:
    * Users pay only for the compute resources that their code consumes
    * Code can be deployed and scaled on a finer granularity
  * Cons:
    * A little complex to learn
    * Costs can be difficult to predict, but this is not specific to serverless computing

#### Function as a Service

* What is it?
  * FaaS is the primary means by which serverless computing is realized today
  * Functions are deployed to be executed on demand within a cloud environment
  * Very recent trend that only gained broad visibility in 2017 with the introduction of AWS Lambda
* Business models
  * Freemium: a limited number of calls to your function are provided for free
  * Calls over the free limit have a cost
  * Packages are available to allow users to pay a flat rate for additional resources

#### Mobile Backend as a Service

* What is it?
  * Many application backends provide similar features
    * Identity management (authentication, authorization)
    * Notification routing
    * Social media integration
    * Cloud-like storage
  * Rather than reinvent the wheel, MBaaS providers share these features as APIs
* Business models
  * Typically BaaS generate revenue using the freemium model
    * i.e. up to a certain number of active users per month are provided for free
    * More active users costs money
* Examples: Firebase