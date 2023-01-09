package org.dstadler.jgit.porcelain;

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
import org.dstadler.jgit.helper.SimpleProgressMonitor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.FetchResult;

import java.io.File;
import java.io.IOException;


/**
 * Simple snippet which shows how to clone a repository from GitHub and
 * then checkout a PR
 *
 * @author dominik.stadler at gmx.at
 */
public class CheckoutGitHubPullRequest {

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
                .setProgressMonitor(new SimpleProgressMonitor())
                .call()) {
	        // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
	        System.out.println("Having repository: " + result.getRepository().getDirectory());

            FetchResult fetchResult = result.fetch()
                    .setRemote(REMOTE_URL)
                    .setRefSpecs("+refs/pull/6/head:pr_6")
                    //.setRefSpecs(new RefSpec("+refs/heads/*:refs/heads/*"))
                    .call();

            System.out.println("Result when fetching the PR: " + fetchResult.getMessages());

            Ref checkoutRef = result.checkout()
                    .setName("pr_6")
                    .call();

            System.out.println("Checked out PR: " + checkoutRef +
                    ", now printing log, it should include two commits from the PR on top");

            Iterable<RevCommit> logs = result.log()
                    .call();
            for (RevCommit rev : logs) {
                System.out.println("Commit: " + rev /* + ", name: " + rev.getName() + ", id: " + rev.getId().getName() */);
            }
        }

        // clean up here to not keep using more and more disk-space for these samples
        FileUtils.deleteDirectory(localPath);
    }
}
