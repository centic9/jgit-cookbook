package org.dstadler.jgit.porcelain;

import java.io.IOException;
import java.util.List;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;



/**
 * Simple snippet which shows how to list all Branches in a Git repository
 * 
 * @author dominik.stadler@gmx.at
 */
public class ListBranches {

    public static void main(String[] args) throws IOException, GitAPIException {
        Repository repository = CookbookHelper.openJGitCookbookRepository();

        List<Ref> call = new Git(repository).branchList().call();
        for (Ref ref : call) {
            System.out.println("Branch: " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());
        }

        repository.close();
    }
}
