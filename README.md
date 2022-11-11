jgit-cookbook
=============
[![Build Status](https://github.com/centic9/jgit-cookbook/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/centic9/jgit-cookbook/actions)
[![Gradle Status](https://gradleupdate.appspot.com/centic9/jgit-cookbook/status.svg?branch=master)](https://gradleupdate.appspot.com/centic9/jgit-cookbook/status)
[![Tag](https://img.shields.io/github/tag/centic9/jgit-cookbook.svg)](https://github.com/centic9/jgit-cookbook/tags)

Provides examples and code snippets for the [JGit](https://eclipse.org/jgit/) Java Git implementation. 

The JGit framework is rich and diverse, it has two layers, a low-level _api_ and a higher-level set of _porcelain_ commands. This can be a bit intimidating at first as there are lots of classes, some of which are not relevant for most tasks.

This project tries to provide a collection of ready-to-run snippets which provide a quick start for building functionality using JGit. 

Please make sure to take a look at the nicely written [introduction](http://www.codeaffine.com/2015/12/15/getting-started-with-jgit/) and also use the existing [JavaDoc](http://download.eclipse.org/jgit/site/6.3.0.202209071007-r/apidocs/) and the [User Guide](http://wiki.eclipse.org/JGit/User_Guide) as well, as they are well done and provide detailed information and a general overview of JGit respectively.

*Note: Please use sites such as http://stackoverflow.com for general questions about JGit usage, not issues in this project. Issues should be used for problems with snippets and suggestions of missing snippets. Snippets from good answers on stackoverflow can then be included here, naturally.*

#### Getting started

##### Grab it

    git clone https://github.com/centic9/jgit-cookbook.git

##### Build it and create Eclipse project files

###### When using Maven

    mvn dependency:sources eclipse:eclipse package

###### When using Gradle

    ./gradlew eclipse check

#### Run it

    Import the project into your favourite IDE and execute the snippets there.

#### Currently the following snippets are available

##### General Repository handling

* [Open an existing repository](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/OpenRepository.java)
* [Create a new repository](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/CreateNewRepository.java)

##### Porcelain commands

* [Initialize a new repository](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/InitRepository.java)
* [Add a new file to the index](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/AddFile.java)
* [Commit a file to an existing repository](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/CommitFile.java)
* [Commit all changes](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/CommitAll.java)
* [List commits (i.e. Log)](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/ShowLog.java)
* [List all tags in a repository](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/ListTags.java)
* [List all branches in a repository](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/ListBranches.java)
* [List all commits in a repository](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/WalkAllCommits.java)
* [List uncommitted changes of a repository](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/ListUncommittedChanges.java)
* [Create and delete branches](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/CreateAndDeleteBranch.java)
* [Create and delete tags](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/CreateAndDeleteTag.java)
* [List all tags applied on a branch](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/ListTagsOnBranch.java)
* [Revert a modified tracked file back to its original state in most recent commit](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/RevertChanges.java)
* [Return diff between two branches](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/ShowBranchDiff.java)
* [Show diff of changes to a file between two revs](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/ShowFileDiff.java)
* [Show diff of changes to all files between two commits](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/ShowChangedFilesBetweenCommits.java)
* [Show diff of changes to a file between two commits when the file has been renamed](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/DiffRenamedFile.java)
* [Show Status](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/ShowStatus.java)
* [Store contents of branch into a compressed file](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/CreateArchive.java)
* [Write contents of branch into a compressed file using a custom archive format](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/CreateCustomFormatArchive.java)
* [Blame, i.e. find out which commit changed specific lines in a file](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/ShowBlame.java)
* [Add and list Notes attached to commits](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/AddAndListNoteOfCommit.java)
* [List available Notes](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/ListNotes.java)
* [Clean all untracked files](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/CleanUntrackedFiles.java)
* [Create, list, apply and drop stashes](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/CreateListApplyAndDropStash.java)
* [Run garbage collection](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/CollectGarbage.java)
* [Blame, i.e. retrieve information who last changed which line in a file](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/BlameFile.java)
* [Merge changes from a branch](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/MergeChanges.java)
* [List changed files between two commits](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/DiffFilesInCommit.java)

##### Commands working with remote repositories

* [Clone a remote repository into a new local directory](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/CloneRemoteRepository.java)
* [Iterate remote references in a repository](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/ListRemotes.java)
* [List remote heads/tags without a local clone](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/ListRemoteRepository.java)
* [Fetch from remote repositories](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/FetchRemoteCommits.java)
* [Fetch from remote repositories and use 'prune' to remove outdated remote branches/tags](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/FetchRemoteCommitsWithPrune.java)
* [Fetch from remote repositories via ssh protocol](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/FetchRemoteCommitsWithSshAuth.java)
* [Clone a remote repository via SSH protocol and username/password credentials](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/CloneRemoteRepositoryWithAuthentication.java)
* [Rebase onto an upstream branch](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/RebaseToOriginMaster.java)
* [Using InMemoryRepository to clone a Git repo in-memory and work from there](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/CloneRemoteRepositoryIntoMemoryAndReadFile.java)
* [Checkout a PR from GitHub](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/porcelain/CheckoutGitHubPullRequest.java)

##### Low-level API

* [Get the SHA-1 ref from a name, e.g. refs/heads/master](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/GetRefFromName.java)
* [Get the commit-object from a name or a SHA-1](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/GetRevCommitFromObjectId.java)
* [Get the commit-message](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/GetCommitMessage.java)
* [Get the tree-object from a commit-object, name or SHA-1](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/GetRevTreeFromObjectId.java)
* [Read the contents of a file/blob](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/ReadBlobContents.java)
* [Get the tag-object from a name or a SHA-1](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/ReadTagFromName.java)
* [Resolve complex references, e.g. HEAD^^ to a SHA-1](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/ResolveRef.java)
* [Iterate over the commits on a branch](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/WalkRev.java)
* [Iterate over a range of commits](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/WalkFromToRev.java)
* [Read contents of a specific file from a specific commit](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/ReadFileFromCommit.java)
* [List remotes configured for the current repository](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/PrintRemotes.java)
* [Print out user information from Git](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/ReadUserConfig.java)
* [Read file attributes, e.g. executable state, file or directory, size, ...](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/GetFileAttributes.java)
* [Use class BranchTrackingStatus to retrieve number of commits ahead/behind compared to remote branches](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/ShowBranchTrackingStatus.java)
* [Check if commits on other branches are merged into a given branch](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/CheckMergeStatusOfCommit.java)
* [List files in a directory as-of a specific commit or a tag](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/ListFilesOfCommitAndTag.java)
* [Iterate over files of a commit recursively](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/WalkTreeRecursive.java)
* [Iterate over files of a commit non-recursively](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/WalkTreeNonRecursive.java)
* [Find all commits that are reachable via tags, branches, remotes, HEADs, ...](https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/WalkAllCommits.java)

##### GitServlet

* There is a standalone sub-project in directory [httpserver](https://github.com/centic9/jgit-cookbook/blob/master/httpserver) which 
starts up a simple HTTP Git server based on the JGit GitServlet.

Just import the project in your IDE and start up the `Main` application, see the Comments in the code for more details.

Another simple way to start the sample-server is to run `./gradlew run` in the httpserver-directory.

#### Useful code elsewhere

##### cf-ops-automation-broker 

* Implementation of a simple git server serving anynymous `git:` protocol: https://github.com/orange-cloudfoundry/cf-ops-automation-broker/blob/8bcb286652fae2b8fe2ccc9f67c53cb0272bcbd0/cf-ops-automation-broker-core/src/main/java/com/orange/oss/cloudfoundry/broker/opsautomation/ondemandbroker/git/GitServer.java
* Usage in tests: https://github.com/orange-cloudfoundry/cf-ops-automation-broker/blob/8bcb286652fae2b8fe2ccc9f67c53cb0272bcbd0/cf-ops-automation-bosh-broker/src/test/java/com/orange/oss/cloudfoundry/broker/opsautomation/ondemandbroker/sample/BoshServiceProvisionningTest.java#L134

#### Missing snippets

* Iterate all commits of a repository: https://gerrit.googlesource.com/plugins/branch-network/+log/refs/heads/master/src/main/java/com/googlesource/gerrit/plugins/branchnetwork/data/JGitFacade.java
* Take some of the unit tests as example: https://github.com/eclipse/jgit/tree/master/org.eclipse.jgit.test/tst/org/eclipse/jgit/api
* SubModules: http://stackoverflow.com/questions/13426798/jgit-read-gitmodules http://www.codeaffine.com/2014/04/16/how-to-manage-git-submodules-with-jgit/ https://stackoverflow.com/questions/26090139/jgit-reading-commits-from-a-submodule https://download.eclipse.org/jgit/site/6.3.0.202209071007-r/apidocs/org/eclipse/jgit/submodule/package-frame.html
* Diffing: http://stackoverflow.com/questions/12987364/how-to-diff-with-two-files-by-jgit-without-creating-repo
* Amend a previous commit: http://stackoverflow.com/questions/4772142/jgit-unstaging-files-removing-files-from-the-index-and-ammending-a-commit
* Remove a file from the index: http://stackoverflow.com/questions/4803462/jgit-java-git-library-unstaging-files
* Git repo on Amazon S3: http://stackoverflow.com/questions/8744611/git-repository-on-s3-as-origin-not-as-backup http://stackoverflow.com/questions/7031729/publish-to-s3-using-git http://www.fancybeans.com/blog/2012/08/24/how-to-use-s3-as-a-private-git-repository/
* CherryPick: http://download.eclipse.org/jgit/site/6.3.0.202209071007-r/apidocs/org/eclipse/jgit/api/CherryPickCommand.html http://stackoverflow.com/questions/18300898/how-to-cherry-pick-a-commit-that-has-more-than-one-parent
* More authentication: http://www.lordofthejars.com/2016/09/authenticating-with-jgit.html
* How to do a shallow clone (i.e. --depth 1) as soon as https://bugs.eclipse.org/bugs/show_bug.cgi?id=475615 is implemented

#### Support this project

If you find these snippets useful and would like to support it, you can [Sponsor the author](https://github.com/sponsors/centic9)

#### Sources

The following sources were used to build the snippets:

* [JGit JavaDoc](http://download.eclipse.org/jgit/site/6.3.0.202209071007-r/apidocs/)
* [JGit User Guide](http://wiki.eclipse.org/JGit/User_Guide)
* [JGit related questions on stackoverflow](http://stackoverflow.com/questions/tagged/jgit)
* [AlBlue's Blog: Embedding JGit](http://alblue.bandlem.com/2013/11/embedding-jgit.html)
* [JGit main page](http://www.eclipse.org/jgit/)

#### Other applications using JGit

* EGit - Git plugin for Eclipse - https://www.eclipse.org/egit/
* Gerrit - A web-based team code collaboration tool - https://www.gerritcodereview.com
* Gitiles - A simple Git repository browser - http://code.google.com/p/gitiles/ and https://android.googlesource.com
* JGitFS - A userfs implementation which allows to browse branches, tags, committs as a directory structure - https://github.com/centic9/JGitFS
* jgitstats - displays repository stats - https://github.com/selesse/jgitstats
* git-to-solr - Index git history into a Solr repository - https://github.com/arafalov/git-to-solr
* Elegit - GUI client for people who want to learn Git - https://github.com/dmusican/Elegit
* Grgit - A wrapper over JGit that provides a fluent API for interacting with Git repositories in Groovy-based tooling - https://github.com/ajoberstar/grgit
* jgitver A library to calculate a project semver compatible version from a git repository and its content - https://github.com/jgitver/jgitver
* gitective - Investigate Git repositories via filters - https://github.com/kevinsawicki/gitective
* RJGit - A JRuby wrapper around the JGit library - https://github.com/repotag/rjgit
* KGit - A Kotlin Wrapper of JGit - https://github.com/sya-ri/KGit

Ruby Build

#### Contribute

Please note that the list of snippets is not yet complete, probably never will. If you are missing things or have suggestions how to improve or add snippets, please either send pull requests or create [issues](https://github.com/centic9/jgit-cookbook/issues).

#### Licensing

   Copyright 2013-2020 Dominik Stadler

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
