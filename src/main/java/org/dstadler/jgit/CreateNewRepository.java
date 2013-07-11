package org.dstadler.jgit;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

/**
 * Simple snippet which shows how to create a new repository
 *
 * @author dominik.stadler@gmx.at
 */
public class CreateNewRepository {
	public static void main(String[] args) throws IOException {
		// prepare a new folder
		File localPath = File.createTempFile("TestGitRepository", "");
		localPath.delete();

		// create the directory
        Repository repository = FileRepositoryBuilder.create(new File(localPath, ".git"));
        repository.create();

		System.out.println("Having repository: " + repository.getDirectory());

		repository.close();

		FileUtils.deleteDirectory(localPath);
	}
}
