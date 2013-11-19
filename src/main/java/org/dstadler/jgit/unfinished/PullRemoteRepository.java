package org.dstadler.jgit.unfinished;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;



/**
 * Simple snippet which shows how to poull from a remote repository from a remote source
 * 
 * @author dominik.stadler@gmx.at
 */
public class PullRemoteRepository {

    private static final String REMOTE_URL = "https://github.com/github/testrepo.git";

    public static void main(String[] args) throws IOException, InvalidRemoteException, TransportException, GitAPIException {
        Repository repository = cloneRepository();

        System.out.println("Having repository: " + repository.getDirectory() + " with head: " +
                repository.getRef(Constants.HEAD) + "/" + repository.resolve("HEAD") + "/" +
                repository.resolve("refs/heads/master"));

        // TODO: why do we get null here for HEAD?!? See also
// http://stackoverflow.com/questions/17979660/jgit-pull-noheadexception

        PullResult call = new Git(repository).pull().call();

        System.out.println("Pulled from the remote repository: " + call);

        repository.close();
    }

    private static Repository cloneRepository() throws IOException, GitAPIException, InvalidRemoteException, TransportException {
        // prepare a new folder for the cloned repository
        File localPath = File.createTempFile("TestGitRepository", "");
        localPath.delete();

        // then clone
        System.out.println("Cloning from " + REMOTE_URL + " to " + localPath);
        Git.cloneRepository()
                .setURI(REMOTE_URL)
                .setDirectory(localPath)
                .call();

        // now open the created repository
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = builder.setGitDir(localPath)
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();
        return repository;
    }
}
