package org.dstadler.jgit.porcelain;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffConfig;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.FollowFilter;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import java.io.IOException;
import java.util.List;

// Simple example that shows how to diff a single file between two commits when
// the file may have been renamed.
public class DiffRenamedFile {
	private static AbstractTreeIterator prepareTreeParser(Repository repository, String objectId) throws IOException {
		// from the commit we can build the tree which allows us to construct the TreeParser
		//noinspection Duplicates
		try (RevWalk walk = new RevWalk(repository)) {
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

	public static void main(String args[])
		throws IOException, GitAPIException {
		try (Repository repo = CookbookHelper.openJGitCookbookRepository()) {
    		// Diff README.md between two commits. The file is named README.md in
    		// the new commit (5a10bd6e), but was named "jgit-cookbook README.md" in
    		// the old commit (2e1d65e4).
    		DiffEntry diff = diffFile(repo,
    			"2e1d65e4cf6c5e267e109aa20fd68ae119fa5ec9",
    			"5a10bd6ee431e362facb03cfe763b9a3d9dfd02d",
    			"README.md");

    		// Display the diff.
    		try (DiffFormatter formatter = new DiffFormatter(System.out)) {
        		formatter.setRepository(repo);
				//noinspection ConstantConditions
				formatter.format(diff);
    		}
		}
	}
}
