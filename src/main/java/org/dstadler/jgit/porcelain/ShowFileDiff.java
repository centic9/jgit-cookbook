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
import java.util.List;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.filter.PathFilter;



/**
 * Simple snippet which shows how to show diffs between branches
 * 
 * @author dominik.stadler at gmx.at
 */
public class ShowFileDiff {

    public static void main(String[] args) throws IOException, GitAPIException {
        Repository repository = CookbookHelper.openJGitCookbookRepository();

        // the diff works on TreeIterators, we prepare two for the two branches
        AbstractTreeIterator oldTreeParser = prepareTreeParser(repository, "09c65401f3730eb3e619c33bf31e2376fb393727");
        AbstractTreeIterator newTreeParser = prepareTreeParser(repository, "aa31703b65774e4a06010824601e56375a70078c");

        // then the procelain diff-command returns a list of diff entries
        List<DiffEntry> diff = new Git(repository).diff().
                setOldTree(oldTreeParser).
                setNewTree(newTreeParser).
                setPathFilter(PathFilter.create("README.md")).
                call();
        for (DiffEntry entry : diff) {
            System.out.println("Entry: " + entry + ", from: " + entry.getOldId() + ", to: " + entry.getNewId());
            DiffFormatter formatter = new DiffFormatter(System.out);
            formatter.setRepository(repository);
            formatter.format(entry);
        }

        repository.close();
    }

    private static AbstractTreeIterator prepareTreeParser(Repository repository, String objectId) throws IOException,
            MissingObjectException,
            IncorrectObjectTypeException {
        // from the commit we can build the tree which allows us to construct the TreeParser
        RevWalk walk = new RevWalk(repository);
        RevCommit commit = walk.parseCommit(ObjectId.fromString(objectId));
        RevTree tree = walk.parseTree(commit.getTree().getId());

        CanonicalTreeParser oldTreeParser = new CanonicalTreeParser();
        try (ObjectReader oldReader = repository.newObjectReader()) {
            oldTreeParser.reset(oldReader, tree.getId());
        }
        
        walk.dispose();

        return oldTreeParser;
    }
}
