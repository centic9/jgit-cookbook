package org.dstadler.jgit.porcelain;

import java.io.IOException;
import java.util.Collection;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

/**
 * Snippet which shows how to iterate remotes, i.e. "git ls-remote"
 * 
 * @author dominik.stadler@gmx.at
 */
public class ListRemotes {

    public static void main(String[] args) throws IOException, GitAPIException {
        Repository repository = CookbookHelper.openJGitCookbookRepository();

        // all refs
        Collection<Ref> refs = new Git(repository).lsRemote().call();
        for (Ref ref : refs) {
            System.out.println("Ref: " + ref);
        }

        // heads only
        refs = new Git(repository).lsRemote().setHeads(true).call();
        for (Ref ref : refs) {
            System.out.println("Head: " + ref);
        }

        // tags only
        refs = new Git(repository).lsRemote().setTags(true).call();
        for (Ref ref : refs) {
            System.out.println("Remote tag: " + ref);
        }

        repository.close();
    }
}
