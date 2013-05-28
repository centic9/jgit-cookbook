package org.dstadler.jgit.helper;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;


public class CookbookHelper {

	public static Repository openJGitCookbookRepository() throws IOException {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder
		  .readEnvironment() // scan environment GIT_* variables
		  .findGitDir() // scan up the file system tree
		  .build();
		return repository;
	}

	public static Repository createNewRepository() throws IOException {
		// prepare a new folder
		File localPath = File.createTempFile("TestGitRepository", "");
		localPath.delete();

		// create the directory
        Repository repository = new FileRepository(localPath + "/.git");
        repository.create();

        return repository;
	}
}
