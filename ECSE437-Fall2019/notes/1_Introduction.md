# Introduction

* Learning objective: design and implementation of release pipelines
  * Phases of the release cycle:
    1. Integrate (push code)
    2. Build
    3. Deploy
    4. Monitor (in production)
* Firefox Release Engineering reading ([link](https://www.aosabook.org/en/ffreleng.html))
  * Section 2.4
  * Repackaging jobs
    * Take the original build (in en-US), unpack it and replace the en-US strings with the strings for another locale that is being shipped to
  * Partner repackages
    * Customized builds for various partners
    * Custom bookmarks, custom homepages, and custom search engines (for example)
  * Localizers nominate their changeset(s) and Mozilla reviews it
  * The Android process is a bit different
    * Only 2 installers: English and multi-locale
    * The size of the multi-locale version is an issue
    * One proposal is to have languages be requested as an add-on instead
  * This process used to be serial but it now runs on 6 machines
    * If a repack fails, only a subset of the process needs to be rerun
* Software releases of the past were:
  * Rare, large, expensive to produce, and intrusive to install
* Software releases now are:
  * Frequent, compact, inexpensive, and non-intrusive to install (e.g. updates install automatically in the background)
* Modern tech companies will often release several times per day
* The build process
  * Step 1: Configuration
    * Conditionally-included features are selected here
    * Important for flexible software
  * Step 2: Construction
    * Order-dependent steps are executed
  * Step 3: Certification
    * Automated testing
  * Step 4: Packaging
    * Bundle into proper format
  * Step 5: Deployment
  * Step 6: Monitoring
    * Watch out for any issues that may come up after the code is deployed
* Continuous integration feedback loop
  * Commit, Build, Test, Report (repeat)