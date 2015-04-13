package org.dstadler.jgit;

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

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 * Simple snippet which shows how to create a new repository
 *
 * @author dominik.stadler at gmx.at
 */
public class CreateNewRepository {

    public static void main(String[] args) throws IOException, IllegalStateException, GitAPIException {
        // prepare a new folder
        File localPath = File.createTempFile("TestGitRepository", "");
        localPath.delete();

        // create the directory
        Git git = Git.init().setDirectory(localPath).call();

        System.out.println("Having repository: " + git.getRepository().getDirectory());

        git.close();

        FileUtils.deleteDirectory(localPath);
    }
}
