package org.dstadler.jgit.api;

/*
   Copyright 2013, 2014 Dominik Stadler

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

import java.io.IOException;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;

/**
 * Simple snippet which shows how to retrieve the user name and email that is configured in Git.
 */
public class ReadUserConfig {

    public static void main(String[] args) throws IOException {
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            Config config = repository.getConfig();
            String name = config.getString("user", null, "name");
            String email = config.getString("user", null, "email");
            if (name == null || email == null) {
                System.out.println("User identity is unknown!");
            } else {
                System.out.println("User identity is " + name + " <" + email + ">");
            }

            String url = config.getString("remote", "origin", "url");
            if (url != null) {
                    System.out.println("Origin comes from " + url);
            }
        }
    }
}
