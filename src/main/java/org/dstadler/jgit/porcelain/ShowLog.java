package org.dstadler.jgit.porcelain;

import java.io.IOException;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;



/**
 * Simple snippet which shows how to get the commit-ids for a file to provide log information.
 *
 * @author dominik.stadler@gmx.at
 */
public class ShowLog {

	public static void main(String[] args) throws IOException, GitAPIException {
		Repository repository = CookbookHelper.openJGitCookbookRepository();

		Iterable<RevCommit> logs = new Git(repository).log()
				.all()
				.call();
		int count = 0;
		for(RevCommit rev : logs) {
			System.out.println("Commit: " + rev /*+ ", name: " + rev.getName() + ", id: " + rev.getId().getName()*/);
			count++;
		}
		System.out.println("Had " + count + " commits overall on current branch");

		logs = new Git(repository).log()
				// for all log.all()
				.addPath("README.md")
				.call();
		count = 0;
		for(RevCommit rev : logs) {
			System.out.println("Commit: " + rev /*+ ", name: " + rev.getName() + ", id: " + rev.getId().getName()*/);
			count++;
		}
		System.out.println("Had " + count + " commits on README.md");

		logs = new Git(repository).log()
			// for all log.all()
			.addPath("pom.xml")
			.call();
		count = 0;
		for(RevCommit rev : logs) {
			System.out.println("Commit: " + rev /*+ ", name: " + rev.getName() + ", id: " + rev.getId().getName()*/);
			count++;
		}
		System.out.println("Had " + count + " commits on pom.xml");

		repository.close();
	}
}
