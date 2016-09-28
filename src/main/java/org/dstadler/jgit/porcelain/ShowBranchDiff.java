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

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import java.io.IOException;
import java.util.List;



/**
 * Simple snippet which shows how to show diffs between branches
 *
 * @author dominik.stadler at gmx.at
 */
public class ShowBranchDiff {

    public static void main(String[] args) throws IOException, GitAPIException {
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            try (Git git = new Git(repository)) {
                if(repository.exactRef("refs/heads/testbranch") == null) {
                    // first we need to ensure that the remote branch is visible locally
                    Ref ref = git.branchCreate().setName("testbranch").setStartPoint("origin/testbranch").call();

                    System.out.println("Created local testbranch with ref: " + ref);
                }

                // the diff works on TreeIterators, we prepare two for the two branches
                AbstractTreeIterator oldTreeParser = prepareTreeParser(repository, "refs/heads/testbranch");
                AbstractTreeIterator newTreeParser = prepareTreeParser(repository, "refs/heads/master");

                // then the procelain diff-command returns a list of diff entries
                List<DiffEntry> diff = git.diff().setOldTree(oldTreeParser).setNewTree(newTreeParser).call();
                for (DiffEntry entry : diff) {
                    System.out.println("Entry: " + entry);
                }
            }
        }
    }

    private static AbstractTreeIterator prepareTreeParser(Repository repository, String ref) throws IOException {
        // from the commit we can build the tree which allows us to construct the TreeParser
        Ref head = repository.exactRef(ref);
        try (RevWalk walk = new RevWalk(repository)) {
            RevCommit commit = walk.parseCommit(head.getObjectId());
            RevTree tree = walk.parseTree(commit.getTree().getId());

            CanonicalTreeParser oldTreeParser = new CanonicalTreeParser();
            try (ObjectReader oldReader = repository.newObjectReader()) {
                oldTreeParser.reset(oldReader, tree.getId());
            }

            walk.dispose();

            return oldTreeParser;
        }
    }
}
