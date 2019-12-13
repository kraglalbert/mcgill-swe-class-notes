# Mitigating Failures

* Release and deployment
  * The systematic process of making official versions of software systems available
* Release pipelines require experts to develop, maintain, and operate them
* Releasing strategies
  * Send it to production teams for etching on to shipment media (e.g. shrink wrapped boxes of DVDs)
  * Upload a tarball to a location on the web
  * Push changes into package repositories such as Maven Central or NPM
  * Upload a new release to an app store like Google Play or the Apple Store
  * Replace the previous version of application resources on web servers
* Some concrete strategies that are used in industry:
  * Blue-green deployment
  * A/B testing
  * Canary releases
  * Chaos engineering

#### Blue-Green Deployment

* Reading: [link](https://martinfowler.com/bliki/BlueGreenDeployment.html)
* One of the challenges of automating deployment is the cut-over itself, i.e. taking software from the final stage of testing to live production
  * Usually needs to be done quickly in order to minimize downtime
* The idea: have two environments (a "blue" environment and a "green" environment) that are near-identical
* One of these two environments is the *production* environment at any given time
* The non-production environment can act as a staging area where changes can be tested
  * Once the changes are ready to go, traffic can simply be switched to go to this environment instead
* Risk of lost transactions
  * If the newly-switched-to environment is broken in some way, then requests that are sent to the broken version are at risk of going unfulfilled
* How can the risk of lost transactions be mitigated?
  * Send requests to both the blue and green deployments during the cut-over
  * If the new environment fails, the previously running environment can resume the operation seamlessly
* Blue-green deployment doubles as a disaster recovery approach
  * Disaster: a catastrophic failure of hardware or software components needed to deliver a service
  * Disaster recovery should be actively tested, and blue-green deployment effectively does that during every release
  * If a failure occurs while the blue deployment is live, the green deployment can take over and vice versa
* If a database schema is changed, it needs to be backwards-compatible
  * The database needs to be reorganized from time to time as a system evolves
  * A schema change can make the previous logic layer obsolete!
    * When this type of change is deployed in a blue-green setting, it makes promotion from one environment to the other difficult, and sometimes impossible
* Instead, perform schema updates in two phases:
  * Phase 1: transitionary status
    * First, the database schema should be refactored in such a way that both the new and old logic layers will still work
    * This way, if promotion fails the application can fail back over to the prior deployment
  * Phase 2: final cleanup
    * After the analytics about the new deployment are clean, the old schema details can be retired