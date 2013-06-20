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
		for(RevCommit rev : walk) {
			System.out.println("Commit: " + rev);
			count++;
		}
		System.out.println(count);

		repository.close();
	}
}
