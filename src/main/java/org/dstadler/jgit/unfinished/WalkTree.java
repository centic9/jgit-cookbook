package org.dstadler.jgit.unfinished;

import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;



/**
 * Simple snippet which shows how to use RevWalk to iterate over objects
 *
 * @author dominik.stadler@gmx.at
 */
public class WalkTree {

	public static void main(String[] args) throws IOException, GitAPIException {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder
		  .readEnvironment() // scan environment GIT_* variables
		  .findGitDir() // scan up the file system tree
		  .build();

		Ref head = repository.getRef("HEAD");

		// a RevWalk allows to walk over commits based on some filtering that is defined
		RevWalk walk = new RevWalk(repository);

		RevCommit commit = walk.parseCommit(head.getObjectId());
		RevTree tree = commit.getTree();
		System.out.println("Having tree: " + tree);

		// TODO: how to look at all files/sub-trees of this Tree now?

		repository.close();
	}
}
