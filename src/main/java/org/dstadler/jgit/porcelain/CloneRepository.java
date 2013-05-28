package org.dstadler.jgit.porcelain;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;



/**
 * Simple snippet which shows how to clone a repository from a remote source
 *
 * @author dominik.stadler@gmx.at
 */
public class CloneRepository {
	public static void main(String[] args) throws IOException, InvalidRemoteException, TransportException, GitAPIException {
		// prepare a new folder for the cloned repository
		File localPath = File.createTempFile("TestGitRepository", "");
		localPath.delete();

		// then clone
		System.out.println("Cloning directory");
        Git.cloneRepository()
        .setURI("https://github.com/github/testrepo.git")
        .setDirectory(localPath)
        .call();

        // now open the created repository
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder.setGitDir(localPath)
		  .readEnvironment() // scan environment GIT_* variables
		  .findGitDir() // scan up the file system tree
		  .build();

		System.out.println("Having repository: " + repository.getDirectory());

		repository.close();
	}
}
