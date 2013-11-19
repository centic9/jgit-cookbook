package org.dstadler.jgit.unfinished;

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
public class PushToRemoteRepository {

    private static final String REMOTE_URL = "https://github.com/github/testrepo.git";

    public static void main(String[] args) throws IOException, InvalidRemoteException, TransportException, GitAPIException {
        // prepare a new folder for the cloned repository
        File localPath = File.createTempFile("TestGitRepository", "");
        localPath.delete();

        // then clone
        System.out.println("Cloning from " + REMOTE_URL + " to " + localPath);
        Git.cloneRepository()
                .setURI(REMOTE_URL)
                .setDirectory(localPath)
                .call();

        // prepare a second folder for the 2nd clone
        File localPath2 = File.createTempFile("TestGitRepository", "");
        localPath2.delete();

        // then clone again
        System.out.println("Cloning from file://" + localPath + " to " + localPath2);
        Git.cloneRepository()
                .setURI("file://" + localPath)
                .setDirectory(localPath2)
                .call();

        // now open the created repository
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = builder.setGitDir(localPath2)
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();

        Git git = new Git(repository);
        git.push()
                .call();

        System.out.println("Pushed from repository: " + repository.getDirectory() + " to remote repository at " + REMOTE_URL);

        repository.close();
    }
}
