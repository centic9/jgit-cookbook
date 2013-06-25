package org.dstadler.jgit.unfinished;

import java.io.IOException;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;



/**
 * Simple snippet which shows how to list log entries
 *
 * @author dominik.stadler@gmx.at
 */
public class ShowLog {

	public static void main(String[] args) throws IOException, GitAPIException {
		Repository repository = CookbookHelper.openJGitCookbookRepository();

		Iterable<RevCommit> logs = new Git(repository).log()
			.all()
			.call();
		for(RevCommit rev : logs) {
			System.out.println("Commit: " + rev + " " + rev.getName() + " " + rev.getId().getName());
		}

		repository.close();
	}
}
