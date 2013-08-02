package org.dstadler.jgit.unfinished;

import java.io.IOException;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revplot.PlotCommitList;
import org.eclipse.jgit.revplot.PlotLane;
import org.eclipse.jgit.revplot.PlotWalk;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * Snippet which shows how to use RevWalk and TreeWalk to read the contents
 * of a specific file from a specific commit.
 *
 * @author dominik.stadler@gmx.at
 */
public class ListChildrenOfCommit {

	public static void main(String[] args) throws IOException, GitAPIException {
		Repository repository = CookbookHelper.openJGitCookbookRepository();

		PlotWalk revWalk = new PlotWalk(repository);
		ObjectId rootId = repository.resolve("refs/heads/master");
		RevCommit root = revWalk.parseCommit(rootId);
		revWalk.markStart(root);
		PlotCommitList<PlotLane> plotCommitList = new PlotCommitList<PlotLane>();
		plotCommitList.source(revWalk);
		plotCommitList.fillTo(Integer.MAX_VALUE);

		System.out.println("Printing children of commit " + root);
		for(RevCommit com : revWalk) {
			System.out.println("Child: " + com);
		}

		System.out.println("Printing with next()");
		System.out.println("next: " + revWalk.next());

		repository.close();
	}
}
