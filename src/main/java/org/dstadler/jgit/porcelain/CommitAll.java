package org.dstadler.jgit.porcelain;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.io.FileUtils;
import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;



/**
 * Simple snippet which shows how to commit all files
 *
 * @author dominik.stadler@gmx.at
 */
public class CommitAll {

    public static void main(String[] args) throws IOException, GitAPIException {
        final File localPath;
        // prepare a new test-repository
        try (Repository repository = CookbookHelper.createNewRepository()) {
            localPath = repository.getWorkTree();

            try (Git git = new Git(repository)) {
                // create the file
                File myFile = new File(repository.getDirectory().getParent(), "testfile");
                if(!myFile.createNewFile()) {
                    throw new IOException("Could not create file " + myFile);
                }

                // Stage all files in the repo including new files, excluding deleted files
                git.add().addFilepattern(".").call();

                // Stage all changed files, including deleted files, excluding new files
                git.add().addFilepattern(".").setUpdate(true).call();

                // and then commit the changes.
                git.commit()
                        .setMessage("Commit all changes including additions")
                        .call();

                try(PrintWriter writer = new PrintWriter(myFile)) {
                    writer.append("Hello, world!");
                }

                // Stage all changed files, omitting new files, and commit with one command
                git.commit()
                        .setAll(true)
                        .setMessage("Commit changes to all files")
                        .call();


                System.out.println("Committed all changes to repository at " + repository.getDirectory());
            }
        }

        // clean up here to not keep using more and more disk-space for these samples
        FileUtils.deleteDirectory(localPath);
    }
}
