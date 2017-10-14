package org.dstadler.jgit.porcelain;

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

import java.io.File;
import java.io.IOException;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;



/**
 * Simple snippet which shows how to commit a file
 *
 * @author dominik.stadler at gmx.at
 */
public class CommitFile {

    public static void main(String[] args) throws IOException, GitAPIException {
        // prepare a new test-repository
        try (Repository repository = CookbookHelper.createNewRepository()) {
            try (Git git = new Git(repository)) {
                // create the file
                File myFile = new File(repository.getDirectory().getParent(), "testfile");
                if(!myFile.createNewFile()) {
                    throw new IOException("Could not create file " + myFile);
                }

                // run the add
                git.add()
                        .addFilepattern("testfile")
                        .call();

                // and then commit the changes
                git.commit()
                        .setMessage("Added testfile")
                        .call();

                System.out.println("Committed file " + myFile + " to repository at " + repository.getDirectory());
            }
        }
    }
}
