package org.dstadler.jgit.porcelain;

import java.io.File;
import java.io.IOException;

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
        // prepare a new test-repository
        try (Repository repository = CookbookHelper.createNewRepository()) {
            try (Git git = new Git(repository)) {
                // create the file
                File myfile = new File(repository.getDirectory().getParent(), "testfile");
                myfile.createNewFile();
        
                // and then commit the changes
                git.commit()
                		.setAll(true)
                        .setMessage("Commit all")
                        .call();
        
                System.out.println("Committed all changes to repository at " + repository.getDirectory());
            }
        }
    }
}
