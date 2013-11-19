package org.dstadler.jgit.porcelain;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;



/**
 * Simple snippet which shows how to initialize a new repository
 * 
 * @author dominik.stadler@gmx.at
 */
public class InitRepository {

    public static void main(String[] args) throws IOException, GitAPIException {
        // run the init-call
        File dir = File.createTempFile("gitinit", ".test");
        dir.delete();

        Git.init()
                .setDirectory(dir)
                .call();

        Repository repository = FileRepositoryBuilder.create(new File(dir.getAbsolutePath(), ".git"));

        System.out.println("Created a new repository at " + repository.getDirectory());

        repository.close();
    }
}
