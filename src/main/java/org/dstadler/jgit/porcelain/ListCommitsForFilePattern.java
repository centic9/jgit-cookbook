package org.dstadler.jgit.porcelain;

/*
   Copyright 2026 Dominik Stadler

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
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Simple snippet which shows how to find all commits that touch files matching
 * a file pattern such as "*.md".
 */
public class ListCommitsForFilePattern {
    private static final String FILE_SUFFIX = ".md";

    public static void main(String[] args) throws IOException, GitAPIException {
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            try (Git git = new Git(repository)) {
                Iterable<RevCommit> logs = git.log()
                        .all()
                        .call();

                int count = 0;
                for (RevCommit commit : logs) {
                    Set<String> matchingPaths = findMatchingPaths(repository, git, commit);
                    for (String path : matchingPaths) {
                        System.out.println("Commit " + commit.getName() + " (" + commit.getShortMessage() + ") touched " + path);
                        count++;
                    }
                }

                System.out.println("Found " + count + " matching commit/file combinations for *" + FILE_SUFFIX);
            }
        }
    }

    private static Set<String> findMatchingPaths(Repository repository, Git git, RevCommit commit) throws IOException, GitAPIException {
        Set<String> matchingPaths = new LinkedHashSet<>();

        if (commit.getParentCount() == 0) {
            matchingPaths.addAll(findMatchingPaths(repository, git, null, commit.getId()));
            return matchingPaths;
        }

        for (RevCommit parent : commit.getParents()) {
            matchingPaths.addAll(findMatchingPaths(repository, git, parent.getId(), commit.getId()));
        }

        return matchingPaths;
    }

    private static Set<String> findMatchingPaths(Repository repository, Git git, ObjectId oldCommit, ObjectId newCommit)
            throws IOException, GitAPIException {
        List<DiffEntry> diffs = git.diff()
                .setOldTree(oldCommit == null ? new EmptyTreeIterator() : prepareTreeParser(repository, oldCommit))
                .setNewTree(prepareTreeParser(repository, newCommit))
                .call();

        Set<String> matchingPaths = new LinkedHashSet<>();
        for (DiffEntry diff : diffs) {
            String path = getMatchingPath(diff);
            if (path != null) {
                matchingPaths.add(path);
            }
        }
        return matchingPaths;
    }

    private static String getMatchingPath(DiffEntry diff) {
        String oldPath = diff.getOldPath();
        String newPath = diff.getNewPath();

        boolean oldMatches = matchesPattern(oldPath);
        boolean newMatches = matchesPattern(newPath);

        if (oldMatches && newMatches && !oldPath.equals(newPath)) {
            return oldPath + " -> " + newPath;
        }
        if (newMatches) {
            return newPath;
        }
        if (oldMatches) {
            return oldPath;
        }

        return null;
    }

    private static boolean matchesPattern(String path) {
        return path != null && !DiffEntry.DEV_NULL.equals(path) && path.endsWith(FILE_SUFFIX);
    }

    private static AbstractTreeIterator prepareTreeParser(Repository repository, ObjectId objectId) throws IOException {
        try (RevWalk walk = new RevWalk(repository)) {
            RevCommit commit = walk.parseCommit(objectId);
            RevTree tree = walk.parseTree(commit.getTree().getId());

            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            try (ObjectReader reader = repository.newObjectReader()) {
                treeParser.reset(reader, tree.getId());
            }

            walk.dispose();

            return treeParser;
        }
    }
}
