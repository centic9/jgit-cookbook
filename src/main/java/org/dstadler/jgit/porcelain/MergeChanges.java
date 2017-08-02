package org.dstadler.jgit.porcelain;

/*
   Copyright 2017 Dominik Stadler

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

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeCommand;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.IOException;

/**
 * Snippet which shows how to merge changes from another branch.
 */
public class MergeChanges {

    public static void main(String[] args) throws IOException, GitAPIException {
        try (Repository repository = CookbookHelper.createNewRepository()) {
            try (Git git = new Git(repository)) {
                // create some commit on master
                createCommit(repository, git, "masterFile");

                // create branch "changes"
                Ref changes = git.branchCreate().setName("changes").call();
                System.out.println("Result of creating the branch: " + changes);

                // check out branch "changes"
                Ref checkout = git.checkout().setName("changes").call();
                System.out.println("Result of checking out the branch: " + checkout);

                // create some commit on branch "changes"
                createCommit(repository, git, "branchFile");

                // check out "master"
                checkout = git.checkout().setName("master").call();
                System.out.println("Result of checking out master: " + checkout);

                // retrieve the objectId of the latest commit on branch
                ObjectId mergeBase = repository.resolve("changes");

                // perform the actual merge, here we disable FastForward to see the
                // actual merge-commit even though the merge is trivial
                MergeResult merge = git.merge().
                        include(mergeBase).
                        setCommit(true).
                        setFastForward(MergeCommand.FastForwardMode.NO_FF).
                        //setSquash(false).
                        setMessage("Merged changes").
                        call();
                System.out.println("Merge-Results for id: " + mergeBase + ": " + merge);
            }
        }
    }

    private static void createCommit(Repository repository, Git git, String fileName) throws IOException, GitAPIException {
        // create the file
        File myFile = new File(repository.getDirectory().getParent(), fileName);
        if(!myFile.createNewFile()) {
            throw new IOException("Could not create file " + myFile);
        }

        // run the add
        git.add()
                .addFilepattern(fileName)
                .call();

        // and then commit the changes
        RevCommit revCommit = git.commit()
                .setMessage("Added " + fileName)
                .call();

        System.out.println("Committed file " + myFile + " as " + revCommit + " to repository at " + repository.getDirectory());
    }
}
