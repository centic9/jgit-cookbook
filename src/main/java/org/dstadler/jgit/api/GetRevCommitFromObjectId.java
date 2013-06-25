package org.dstadler.jgit.api;

import java.io.IOException;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

/**
 * Simple snippet which shows how to use RevWalk to iterate over objects
 */
public class GetRevCommitFromObjectId {
	public static void main(String[] args) throws IOException {
		Repository repository = CookbookHelper.openJGitCookbookRepository();

		Ref head = repository.getRef("refs/heads/master");
		System.out.println("Found head: " + head);

		// a RevWalk allows to walk over commits based on some filtering that is defined
		RevWalk walk = new RevWalk(repository);
		RevCommit commit = walk.parseCommit(head.getObjectId());
		System.out.println("Found Commit: " + commit);

		repository.close();
	}
}
