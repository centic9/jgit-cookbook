package org.dstadler.jgit.porcelain;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;



/**
 * Simple snippet which shows how to list all Tags
 *
 * @author dominik.stadler@gmx.at
 */
public class CleanUntrackedFiles {

    public static void main(String[] args) throws IOException, GitAPIException {
        Repository repository = CookbookHelper.createNewRepository();

        System.out.println("Repository at " + repository.getWorkTree());

        File untrackedFile = File.createTempFile("untracked", ".txt", repository.getWorkTree());
        File untrackedDir = File.createTempFile("untrackedDir", "", repository.getWorkTree());
        untrackedDir.delete();
        untrackedDir.mkdirs();

        System.out.println("Untracked exists: " + untrackedFile.exists() + " Dir: " + untrackedDir.exists() + "/" + untrackedDir.isDirectory());

        Set<String> removed = new Git(repository).clean().setCleanDirectories(true).call();
        for(String item : removed) {
        	System.out.println("Removed: " + item);
        }
        System.out.println("Removed " + removed.size() + " items");

        System.out.println("Untracked after: " + untrackedFile.exists() + " Dir: " + untrackedDir.exists() + "/" + untrackedDir.isDirectory());

        repository.close();
    }
}
