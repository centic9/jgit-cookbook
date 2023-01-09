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

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;



/**
 * Note: This snippet is not done and likely does not show anything useful yet
 *
 * @author dominik.stadler at gmx.at
 */
public class TrackMaster {

    private static final String REMOTE_URL = "https://github.com/github/testrepo.git";

    public static void main(String[] args) throws IOException, GitAPIException {
        // prepare a new folder for the cloned repository
        File localPath = File.createTempFile("TestGitRepository", "");
        if(!localPath.delete()) {
            throw new IOException("Could not delete temporary file " + localPath);
        }

        // then clone
        System.out.println("Cloning from " + REMOTE_URL + " to " + localPath);
        try (Git result = Git.cloneRepository()
                .setURI(REMOTE_URL)
                .setDirectory(localPath)
                .call()) {
            System.out.println("Result: " + result);

            // now open the created repository
            FileRepositoryBuilder builder = new FileRepositoryBuilder();
            try (Repository repository = builder
                    .readEnvironment() // scan environment GIT_* variables
                    .findGitDir(localPath) // scan up the file system tree
                    .build()) {
                try (Git git = new Git(repository)) {
                    git.branchCreate()
                            .setName("master")
                            // ?!? .setUpstreamMode(SetupUpstreamMode.SET_UPSTREAM)
                            .setStartPoint("origin/master")
                            .setForce(true)
                            .call();
                }

                System.out.println("Now tracking master in repository at " + repository.getDirectory() + " from origin/master at " +
                        REMOTE_URL);
            }
        }

        // clean up here to not keep using more and more disk-space for these samples
        FileUtils.deleteDirectory(localPath);
    }
}
