jgit-cookbook
=============
[![Build Status](https://buildhive.cloudbees.com/job/centic9/job/jgit-cookbook/badge/icon)](https://buildhive.cloudbees.com/job/centic9/job/jgit-cookbook/)

Provides examples and code snippets for the [JGit](http://wiki.eclipse.org/JGit/) Java Git implementation. 

The JGit framework is rich and diverse, it has two layers, a low-level _api_ and a higher-level set of _porcelain_ commands. 

This project tries to provide a collection of ready-to-run snippets which try to augment the existing [JavaDoc](http://download.eclipse.org/jgit/docs/latest/apidocs/) and the [User Guide](http://wiki.eclipse.org/JGit/User_Guide)

#### Getting started

##### Grab it

    git clone git://github.com/centic9/jgit-cookbook

##### Build it and create Eclipse project files

	mvn dependency:sources eclipse:eclipse package

#### Run it

    Import the project into an Eclipse workspace and execute the snippets there.

#### Currently the following snippets are available

##### General Repository handling
* [Open an existing repository](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/OpenRepository.java)
* [Create a new repository](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/CreateNewRepository.java)

##### Porcelain commands

* [Initialize a new repository](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/InitRepository.java)
* [Add a new file to the index](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/AddFile.java)
* [Commit a file to an existing repository](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/CommitFile.java)
* [List all tags in a repository](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/ListTags.java)
* [List all branches in a repository](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/ListBranches.java)
* [List all commits in a repository](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/WalkAllCommits.java)
* [Create and delete branches](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/CreateAndDeleteBranch.java)
* [Create and delete tags](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/CreateAndDeleteTag.java)
* [Return diff between two branches](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/ShowBranchDiff.java)

* [Clone a remote reppository into a new local directory](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/CloneRemoteRepository.java)
* [Iterate remote references like heads and tags](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/ListRemoteReferences.java)

##### Low-level API

* [Get the SHA-1 ref from a name, e.g. refs/heads/master](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/GetRefFromName.java)
* [Get the commit-object from a name or a SHA-1](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/GetRevCommitFromObjectId.java)
* [Get the tree-object from a commit-object, name or SHA-1](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/GetRevTreeFromObjectId.java)
* [Read the contents of a file/blob](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/ReadBlobContents.java)
* [Get the tag-object from a name or a SHA-1](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/ReadTagFromName.java)
* [Resolve complex references, e.g. HEAD^^ to a SHA-1](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/ResolveRef.java)
* [Iterate over the commits on a branch](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/WalkRev.java)
* [Read contents of a specific file from a specific commit](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/ReadFileFromCommit.java)
* [List remotes configured for the current repository](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/PrintRemotes.java)
* [Print out user information from Git](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/ReadUserConfig.java)
* [Read file attributes, e.g. executeable state, file or directory, size, ...](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/GetFileAttributes.java)

#### Missing snippets

* Iterate all commits of a repository - https://gerrit.googlesource.com/plugins/branch-network/+log/refs/heads/master/src/main/java/com/googlesource/gerrit/plugins/branchnetwork/data/JGitFacade.java
* Take some of the unit tests as example: https://github.com/eclipse/jgit/tree/master/org.eclipse.jgit.test/tst/org/eclipse/jgit/api

#### Sources

The following sources were used to build the snippets

* [JGit JavaDoc](http://download.eclipse.org/jgit/docs/latest/apidocs/)
* [JGit User Guide](http://wiki.eclipse.org/JGit/User_Guide)
* [JGit related questions on Stackoverflow](http://stackoverflow.com/questions/tagged/jgit)

#### Contribute

Please note that the list of snippets is not yet complete, probably never will. If you are missing things or have suggestions how to improve or add snippets, please either send pull requests or create [issues](https://github.com/centic9/jgit-cookbook/issues).
