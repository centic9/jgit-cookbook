package org.dstadler.jgit.api;

import java.io.IOException;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;



/**
 * Simple snippet which shows how to retrieve a Ref for some reference string.
 *
 * @author dominik.stadler@gmx.at
 */
public class GetRef {
	public static void main(String[] args) throws IOException {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder
		  .readEnvironment() // scan environment GIT_* variables
		  .findGitDir() // scan up the file system tree
		  .build();

		// the Ref holds an ObjectId for any type of object (tree, commit, blob, tree)
		Ref head = repository.getRef("refs/heads/master");
		System.out.println("Ref of refs/heads/master: " + head);

		repository.close();
	}
}
