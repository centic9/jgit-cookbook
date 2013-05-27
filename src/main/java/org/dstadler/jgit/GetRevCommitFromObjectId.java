package org.dstadler.jgit;

import java.io.IOException;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;



/**
 * Simple snippet which shows how to use RevWalk to iterate over objects
 *
 * @author dominik.stadler@gmx.at
 */
public class GetRevCommitFromObjectId {
	public static void main(String[] args) throws IOException {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder
		  .readEnvironment() // scan environment GIT_* variables
		  .findGitDir() // scan up the file system tree
		  .build();

		Ref head = repository.getRef("refs/heads/master");

		// a RevWalk allows to walk over commits based on some filtering that is defined
		RevWalk walk = new RevWalk(repository);
		RevCommit commit = walk.parseCommit(head.getObjectId());
		System.out.println("Found Commit: " + commit);

		repository.close();
	}
}
