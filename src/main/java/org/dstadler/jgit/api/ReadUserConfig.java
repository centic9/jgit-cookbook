package org.dstadler.jgit.api;

import java.io.IOException;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;

/**
 * Simple snippet which shows how to retrieve the user name and email that is configured in Git.
 */
public class ReadUserConfig {

    public static void main(String[] args) throws IOException {
        Repository repository = CookbookHelper.openJGitCookbookRepository();

        Config config = repository.getConfig();
        String name = config.getString("user", null, "name");
        String email = config.getString("user", null, "email");
        if (name == null || email == null) {
            System.out.println("User identity is unknown!");
        } else {
            System.out.println("User identity is " + name + " <" + email + ">");
        }
        repository.close();
    }
}
