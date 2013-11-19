package org.dstadler.jgit.api;

import java.io.IOException;
import java.util.Set;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;

/**
 * Simple snippet which shows how to retrieve the list of remotes from the configuration
 */
public class PrintRemotes {

    public static void main(String[] args) throws IOException {
        Repository repository = CookbookHelper.openJGitCookbookRepository();

        Config storedConfig = repository.getConfig();
        Set<String> remotes = storedConfig.getSubsections("remote");

        for (String remoteName : remotes) {
            String url = storedConfig.getString("remote", remoteName, "url");
            System.out.println(remoteName + " " + url);
        }

        repository.close();
    }
}
