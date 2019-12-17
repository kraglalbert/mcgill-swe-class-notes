# Midterm 2 Review Session Notes

1. Name four means by which defects are found in a software system
    * Testing, statistical defect prediction, static analysis tools, dynamic analysis tools, inspections
   
2. Give the cloud types of the technologies below:
    * Heroku: *Platform as a Service*
    * Amazon EC2: *Infrastructure as a Service*
    * Firebase: *(Mobile) Backend as a Service*
    * Linode: *Infrastructure as a Service*

4. What is the key different between a Type 1 and a Type 2 Hypervisor?
    * Type 1 Hypervisor: VMM sits directly on top of the hardware
    * Type 2 Hypervisor: OS separates VMM and the hardware

6. Statistical defect prediction uses historical data about defects to...?
    * Predict likely locations in the codebase where defects may occur
   
8. How does container-based development differ from traditional development?
    * Developers do not need to set up an entire development environment when using containers; instead they can run their applications inside containers
   
9. What is "serverless"? How is it typically achieved nowadays?
    * Serverless is a variant of IaaS where capacity planning decisions are made by the provider (instead of the customer)
    * Customers are charged only for the resources that their apps use
    * Typically realized with FaaS (e.g. AWS Lambda)

11. What is the difference between static and dynamic analysis? Give an example of a tool for each analysis.
    * Static analysis tools examine the code without running it (e.g. syntax analysis)
      * e.g. PyLint
    * Dynamic analysis tools examine how information propagates through blocks and paths of a program
      * e.g. Valgrind

13. Identify the code smell in the following code:
    ```c
    int foo(int iX, int iY) {
    	int iZ = iX / iY;
    	return iX * iY;
    }
    ```

    * This code can throw an arithmetic exception (if `iY` is zero we have division by zero)

14. What is meant by a declarative syntax?
    * Expresses the logic of a computation without describing its control flow
    * Describes *what* the program needs to do, but not *how*

15. What does the following puppet configuration do?
    ```c
    file { '/var/www/html/hello.html':
    	source => 'puppet:///modules/cowsay/hello.html',
    }
    ```
    * Copies the file specified in `source` to `/var/www/html/hello.html` on the agent node(s)

16. What is the purpose of virtualization? What are the two key types?
    * Purpose: emulate a computer system (gives process isolation, flexibility, scalability, makes entire machines "shippable")
    * Types: system and process-level virtualization

17. Which Docker command do we need to use to transform a Dockerfile into its image?
    * `docker build`

19. What does the `puppet-resource` command do?
    * It is an administrative tool that lets the user inspect and manipulate resources on a system
    * It can work with any resource type that Puppet knows about

21. Explain the relationship between Docker images and Docker containers.
    * Containers are running instances of images
    
23. When copying an executable into a “chroot” jail, we must also copy shared libraries that it requires. Which command do we use to view the shared libraries that an executable depends upon?    
    * `ldd`
    
24. How does a technology like Docker differ from a technology like Puppet?
    * Docker is a virtualization tool for managing different containers
    * Puppet is a tool for managing infrastructure

26. After deploying a web application to an IaaS cloud provider (e.g., Digital Ocean) and starting the web server, when we point our web browser to the correct URL, the page is not displayed. What could be the reason?
    * Ports are not correctly configured
    * The IaaS cloud provider currently has an outage
    * The application was not started up correctly on the cloud platform
    
27. List 5 code smells that can be detected using static analysis tools
    * Long parameter list
    * God class
    * Duplicate code
    * Shotgun surgery
    * Feature envy

30. What is a Procfile used for in Heroku? What does the following Procfile specification mean?
    ```yaml
    web: java -jar lib/foobar.jar $PORT
    queue: java -jar lib/queue-processor.jar
    ```

    * Specifies which commands should be executed by the app on startup
    * The `web` process is special, since it is the only process that can receive external HTTP traffic from Heroku's routers
    * The `queue` process defines another process which will also run but not receive any web traffic
