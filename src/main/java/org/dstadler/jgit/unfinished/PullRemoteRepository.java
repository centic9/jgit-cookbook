package org.dstadler.jgit.unfinished;

/*
   Copyright 2013, 2014 Dominik Stadler

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;



/**
 * Simple snippet which shows how to poull from a remote repository from a remote source
 *
 * @author dominik.stadler at gmx.at
 */
public class PullRemoteRepository {

    private static final String REMOTE_URL = "https://github.com/github/testrepo.git";

    public static void main(String[] args) throws IOException, InvalidRemoteException, TransportException, GitAPIException {
        Repository repository = cloneRepository();

        System.out.println("Having repository: " + repository.getDirectory() + " with head: " +
                repository.getRef(Constants.HEAD) + "/" + repository.resolve("HEAD") + "/" +
                repository.resolve("refs/heads/master"));

        // TODO: why do we get null here for HEAD?!? See also
// http://stackoverflow.com/questions/17979660/jgit-pull-noheadexception

        PullResult call = new Git(repository).pull().call();

        System.out.println("Pulled from the remote repository: " + call);

        repository.close();
    }

    private static Repository cloneRepository() throws IOException, GitAPIException, InvalidRemoteException, TransportException {
        // prepare a new folder for the cloned repository
        File localPath = File.createTempFile("TestGitRepository", "");
        localPath.delete();

        // then clone
        System.out.println("Cloning from " + REMOTE_URL + " to " + localPath);
        Git result = Git.cloneRepository()
                .setURI(REMOTE_URL)
                .setDirectory(localPath)
                .call();

        // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
        return result.getRepository();
    }
}
