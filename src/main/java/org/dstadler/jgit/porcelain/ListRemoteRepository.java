package org.dstadler.jgit.porcelain;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Ref;



/**
 * Simple snippet which shows how to list heads/tags of remote repositories without
 * a local repository
 * 
 * @author dominik.stadler@gmx.at
 */
public class ListRemoteRepository {

    private static final String REMOTE_URL = "https://github.com/github/testrepo.git";

    public static void main(String[] args) throws IOException, InvalidRemoteException, TransportException, GitAPIException {
        // then clone
        System.out.println("Listing remote repository " + REMOTE_URL);
        Collection<Ref> refs = Git.lsRemoteRepository()
                .setHeads(true)
                .setTags(true)
                .setRemote(REMOTE_URL)
                .call();

        for (Ref ref : refs) {
            System.out.println("Ref: " + ref);
        }
    }
}
