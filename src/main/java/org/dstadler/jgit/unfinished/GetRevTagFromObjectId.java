package org.dstadler.jgit.unfinished;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;



/**
 * Simple snippet which shows how to use RevWalk to iterate over objects
 *
 * @author dominik.stadler@gmx.at
 */
public class GetRevTagFromObjectId {

	public static void main(String[] args) throws IOException, GitAPIException {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder
		  .readEnvironment() // scan environment GIT_* variables
		  .findGitDir() // scan up the file system tree
		  .build();

		List<Ref> call = new Git(repository).tagList().call();
		for(Ref rev : call) {
			System.out.println("Tag: " + rev);
		}

		//Ref head = repository.getRef("refs/tags/initialtag");

		// a RevWalk allows to walk over commits based on some filtering that is defined
		RevWalk walk = new RevWalk(repository);

		RevObject any = walk.parseAny(call.get(0).getObjectId());
		System.out.println("Any: " + any);

		RevTag tag = walk.parseTag(call.get(0).getObjectId());
		System.out.println("Found Tag: " + tag);

		repository.close();
	}
}
