# Version Control

* Git is a distributed version control system (DVCS)
  * Centralized VCS clients need server connections to perform most operations
  * By contrast, most operations can be performed on the DVCS client without interacting with the server
* DVCS users can create and merge branches as lightweight operations on their client machines

#### Git Server Operations

* Git is decentralized by design; each repository can act as a server (provider of commits) or a client (receiver of commits)
* By convention, a server-only repository is typically a **bare** repository named with the `.git` extension
  * The `git init --bare` command can be used to create a bare repository
* Changes can be pushed to a bare repository

#### Git Internals

* The Git object database
  * objects
    * The key-value storage at the core of how git works
    * Three types of values are stored in the object database (listed below)
  * Commit
    * A reference to a change being tracked by Git
    * Refers to a *tree* object that describes the state of the repository at that commit
  * Tree
    * A directory being tracked by Git
    * Contains a listing of *blob* objects (files) and tree objects (subdirectories)
  * Blob
    * A file being tracked by Git
* The `git cat-file` command allows you to see the type/contents of a Git object
* Git refs and the HEAD file
  * refs
    * `heads/`: a listing of commit-type object database keys that each branch currently points to
    * `remotes/`: a listing of branches that are currently being tracked in upstream repositories that you've cloned from or manually configured
    * `tags/`: a listing of commit-type object database keys that each tag corresponds to
  * HEAD
    * A reference to the current branch that is checked out
    * May refer to a commit if we are in a "detached HEAD" state
* Other contents of the Git tracking folder
  * `config`: a file that stores configuration options for the current Git repository
  * `description`: legacy file used only by the `gitweb` program (web server for Git repositories)
  * `info`: contains an `exclude` file that can be used to ignore files inside the Git folder by default

#### Git Hooks

* Stored in the `hooks/` folder
  * Suppose that there are operations that you would like to perform for each interaction with your Git repository
    * Example: enforcing a commit message style
  * Hooks allow these interactions to run via scripts
    * Hooks can be made on the client and server side
    * Git book chapter on hooks: [link](https://git-scm.com/book/en/v2/Customizing-Git-Git-Hooks)
  * Example hooks exist in the `hooks/` folder with the `.sample` extension
  * There are different hooks which run for different interactions/different points in the Git lifecycle
    * The hook script files must be named correctly!