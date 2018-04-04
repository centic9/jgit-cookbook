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

import org.apache.commons.io.FileUtils;
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
import java.util.Arrays;
import java.util.Map;

/**
 * Snippet which shows how to merge changes from another branch.
 */
public class MergeChanges {

    public static void main(String[] args) throws IOException, GitAPIException {
        final File localPath;
        try (Repository repository = CookbookHelper.createNewRepository()) {
            localPath = repository.getWorkTree();

            try (Git git = new Git(repository)) {
                // create some commit on master
                createCommit(repository, git, "masterFile", "content12");

                // create branch "changes"
                Ref changes = git.branchCreate().setName("changes").call();
                System.out.println("Result of creating the branch: " + changes);

                // now start a change on master
                createCommit(repository, git, "sharedFile", "content12");

                // check out branch "changes"
                Ref checkout = git.checkout().setName("changes").call();
                System.out.println("Result of checking out the branch: " + checkout);

                // create some commit on branch "changes", one of them conflicting with the change on master
                createCommit(repository, git, "branchFile", "content98");
                createCommit(repository, git, "sharedFile", "content98");

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
                for (Map.Entry<String,int[][]> entry : merge.getConflicts().entrySet()) {
                    System.out.println("Key: " + entry.getKey());
                    for(int[] arr : entry.getValue()) {
                        System.out.println("value: " + Arrays.toString(arr));
                    }
                }
            }

            // clean up here to not keep using more and more disk-space for these samples
            FileUtils.deleteDirectory(localPath);
        }
    }

    private static void createCommit(Repository repository, Git git, String fileName, String content) throws IOException, GitAPIException {
        // create the file
        File myFile = new File(repository.getDirectory().getParent(), fileName);
        FileUtils.writeStringToFile(myFile, content, "UTF-8");

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
