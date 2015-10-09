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

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;



/**
 * Simple snippet which shows how to initialize a new repository
 * 
 * @author dominik.stadler at gmx.at
 */
public class InitRepository {

    public static void main(String[] args) throws IOException, GitAPIException {
        // run the init-call
        File dir = File.createTempFile("gitinit", ".test");
        dir.delete();

        Git.init()
                .setDirectory(dir)
                .call();

        try (Repository repository = FileRepositoryBuilder.create(new File(dir.getAbsolutePath(), ".git"))) {
            System.out.println("Created a new repository at " + repository.getDirectory());
        }
    }
}
