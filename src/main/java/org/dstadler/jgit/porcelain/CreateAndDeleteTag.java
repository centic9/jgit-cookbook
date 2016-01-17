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

import java.io.IOException;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;



/**
 * Simple snippet which shows how to create a tag
 *
 * @author dominik.stadler at gmx.at
 */
public class CreateAndDeleteTag {

    public static void main(String[] args) throws IOException, GitAPIException {
        // prepare test-repository
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            try (Git git = new Git(repository)) {
                // remove the tag before creating it
                git.tagDelete().setTags("tag_for_testing").call();

                // set it on the current HEAD
                Ref tag = git.tag().setName("tag_for_testing").call();
                System.out.println("Created/moved tag " + tag + " to repository at " + repository.getDirectory());

                // remove the tag again
                git.tagDelete().setTags("tag_for_testing").call();

                // read some other commit and set the tag on it
                ObjectId id = repository.resolve("HEAD^");
                try (RevWalk walk = new RevWalk(repository)) {
                    RevCommit commit = walk.parseCommit(id);
                    tag = git.tag().setObjectId(commit).setName("tag_for_testing").call();
                    System.out.println("Created/moved tag " + tag + " to repository at " + repository.getDirectory());

                    // remove the tag again
                    git.tagDelete().setTags("tag_for_testing").call();

                    // create an annotated tag
                    tag = git.tag().setName("tag_for_testing").setAnnotated(true).call();
                    System.out.println("Created/moved tag " + tag + " to repository at " + repository.getDirectory());

                    // remove the tag again
                    git.tagDelete().setTags("tag_for_testing").call();

                    walk.dispose();
                }
            }
        }
    }
}
