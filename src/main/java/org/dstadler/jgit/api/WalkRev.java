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
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;



/**
 * Simple snippet which shows how to use RevWalk to iterate over objects
 */
public class WalkRev {

    public static void main(String[] args) throws IOException {
        Repository repository = CookbookHelper.openJGitCookbookRepository();

        Ref head = repository.getRef("refs/heads/master");

        // a RevWalk allows to walk over commits based on some filtering that is defined
        RevWalk walk = new RevWalk(repository);

        RevCommit commit = walk.parseCommit(head.getObjectId());
        System.out.println("Start-Commit: " + commit);

        System.out.println("Walking all commits starting at HEAD");
        walk.markStart(commit);
        int count = 0;
        for (RevCommit rev : walk) {
            System.out.println("Commit: " + rev);
            count++;
        }
        System.out.println(count);

        repository.close();
    }
}
