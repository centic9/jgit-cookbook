package org.dstadler.jgit.api;

import java.io.IOException;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;



/**
 * Simple snippet which shows how to retrieve an ObjectId for some name.
 */
public class ResolveRef {

    public static void main(String[] args) throws IOException {
        Repository repository = CookbookHelper.openJGitCookbookRepository();

        // basic syntax is similar to getRef()
        ObjectId id = repository.resolve("HEAD");
        System.out.println("ObjectId of HEAD: " + id);

        // however resolve() supports almost all of the git-syntax, where getRef() only works on names
        id = repository.resolve("HEAD^1");
        System.out.println("ObjectId of HEAD: " + id);

        repository.close();
    }
}
