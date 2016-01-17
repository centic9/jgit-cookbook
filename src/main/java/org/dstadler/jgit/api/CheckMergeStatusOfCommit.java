package org.dstadler.jgit.api;

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

import java.io.IOException;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

/**
 * Snippet which shows how to check if commits are merged into a
 * given branch.
 *
 * See also http://stackoverflow.com/questions/26644919/how-to-determine-with-jgit-which-branches-have-been-merged-to-master
 */
public class CheckMergeStatusOfCommit {

    public static void main(String[] args) throws IOException {
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            try (RevWalk revWalk = new RevWalk( repository )) {
                RevCommit masterHead = revWalk.parseCommit( repository.resolve( "refs/heads/master" ));

                // first a commit that was merged
                ObjectId id = repository.resolve("05d18a76875716fbdbd2c200091b40caa06c713d");
                System.out.println("Had id: " + id);
                RevCommit otherHead = revWalk.parseCommit( id );

                if( revWalk.isMergedInto( otherHead, masterHead ) ) {
                    System.out.println("Commit " + otherHead + " is merged into master");
                } else {
                    System.out.println("Commit " + otherHead + " is NOT merged into master");
                }


                // then a commit on a test-branch which is not merged
                id = repository.resolve("ae70dd60a7423eb07893d833600f096617f450d2");
                System.out.println("Had id: " + id);
                otherHead = revWalk.parseCommit( id );

                if( revWalk.isMergedInto( otherHead, masterHead ) ) {
                    System.out.println("Commit " + otherHead + " is merged into master");
                } else {
                    System.out.println("Commit " + otherHead + " is NOT merged into master");
                }

                // and finally master-HEAD itself
                id = repository.resolve("HEAD");
                System.out.println("Had id: " + id);
                otherHead = revWalk.parseCommit( id );

                if( revWalk.isMergedInto( otherHead, masterHead ) ) {
                    System.out.println("Commit " + otherHead + " is merged into master");
                } else {
                    System.out.println("Commit " + otherHead + " is NOT merged into master");
                }

                revWalk.dispose();
            }
        }
    }
}
