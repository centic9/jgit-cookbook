package org.dstadler.jgit;

import java.io.IOException;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;



/**
 * Simple snippet which shows how to retrieve an ObjectId for some name.
 *
 * @author dominik.stadler@gmx.at
 */
public class ResolveRef {
	public static void main(String[] args) throws IOException {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder
		  .readEnvironment() // scan environment GIT_* variables
		  .findGitDir() // scan up the file system tree
		  .build();

		ObjectId head = repository.resolve("HEAD");
		System.out.println("ObjectId of HEAD: " + head);
		repository.close();
	}
}
