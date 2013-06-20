package org.dstadler.jgit.unfinished;

import java.io.IOException;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;

/**
 * Simple snippet which shows how to use RevWalk to iterate over items in a file-tree
 *
 * @author dominik.stadler@gmx.at
 */
public class WalkTreeRecursive {

	public static void main(String[] args) throws IOException, GitAPIException {
		Repository repository = CookbookHelper.openJGitCookbookRepository();

		Ref head = repository.getRef("HEAD");

		// a RevWalk allows to walk over commits based on some filtering that is defined
		RevWalk walk = new RevWalk(repository);

		RevCommit commit = walk.parseCommit(head.getObjectId());
		RevTree tree = commit.getTree();
		System.out.println("Having tree: " + tree);

		// now use a TreeWalk to iterate over all files in the Tree recursively
		// you can set Filters to narrow down the results if needed
		TreeWalk treeWalk = new TreeWalk(repository);
		treeWalk.addTree(tree);
		treeWalk.setRecursive(true);
		while(treeWalk.next()) {
			System.out.println("found: " + treeWalk.getPathString());
		}

		repository.close();
	}
}
