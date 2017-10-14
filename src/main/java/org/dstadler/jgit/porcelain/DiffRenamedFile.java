package org.dstadler.jgit.porcelain;

import java.io.IOException;
import java.util.List;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffConfig;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.FollowFilter;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

// Simple example that shows how to diff a single file between two commits when
// the file may have been renamed.
public class DiffRenamedFile {
    public static void main(String args[])
            throws IOException, GitAPIException {
        try (Repository repo = CookbookHelper.openJGitCookbookRepository()) {
            runDiff(repo,
                    "2e1d65e4cf6c5e267e109aa20fd68ae119fa5ec9",
                    "5a10bd6ee431e362facb03cfe763b9a3d9dfd02d",
                    "README.md");

            // try the reverse as well
            runDiff(repo,
                    "5a10bd6ee431e362facb03cfe763b9a3d9dfd02d",
                    "2e1d65e4cf6c5e267e109aa20fd68ae119fa5ec9",
                    "README.md");

            // caret allows to specify "the previous commit"
            runDiff(repo,
                    "7b2e6193a39726510ed9d0f66a779665d0e4ce23^",
                    "7b2e6193a39726510ed9d0f66a779665d0e4ce23",
                    "build.gradle");
        }
    }

    private static void runDiff(Repository repo, String oldCommit, String newCommit, String path) throws IOException, GitAPIException {
        // Diff README.md between two commits. The file is named README.md in
        // the new commit (5a10bd6e), but was named "jgit-cookbook README.md" in
        // the old commit (2e1d65e4).
        DiffEntry diff = diffFile(repo,
                oldCommit,
                newCommit,
                path);

        // Display the diff
        System.out.println("Showing diff of " + path);
        try (DiffFormatter formatter = new DiffFormatter(System.out)) {
            formatter.setRepository(repo);
            //noinspection ConstantConditions
            formatter.format(diff);
        }
    }

    private static AbstractTreeIterator prepareTreeParser(Repository repository, String objectId) throws IOException {
        // from the commit we can build the tree which allows us to construct the TreeParser
        //noinspection Duplicates
        try (RevWalk walk = new RevWalk(repository)) {
            RevCommit commit = walk.parseCommit(repository.resolve(objectId));
            RevTree tree = walk.parseTree(commit.getTree().getId());

            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            try (ObjectReader reader = repository.newObjectReader()) {
                treeParser.reset(reader, tree.getId());
            }

            walk.dispose();

            return treeParser;
        }
    }

    private static @NonNull DiffEntry diffFile(Repository repo, String oldCommit,
                       String newCommit, String path) throws IOException, GitAPIException {
        Config config = new Config();
        config.setBoolean("diff", null, "renames", true);
        DiffConfig diffConfig = config.get(DiffConfig.KEY);
        try (Git git = new Git(repo)) {
            List<DiffEntry> diffList = git.diff().
                setOldTree(prepareTreeParser(repo, oldCommit)).
                setNewTree(prepareTreeParser(repo, newCommit)).
                setPathFilter(FollowFilter.create(path, diffConfig)).
                call();
            if (diffList.size() == 0)
                return null;
            if (diffList.size() > 1)
                throw new RuntimeException("invalid diff");
            return diffList.get(0);
        }
    }
}
