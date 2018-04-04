package org.dstadler.jgit.porcelain;

/*
 * Copyright 2013, 2014 Dominik Stadler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.archive.ArchiveFormats;
import org.eclipse.jgit.lib.Repository;



/**
 * Simple snippet which shows how to package the contents of a branch into an archive file
 * using a format provided by the org.eclipse.jgit.archive jar.
 *
 * @author dominik.stadler at gmx.at
 */
public class CreateArchive {
    public static void main(String[] args) throws IOException, GitAPIException {
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            // make the included archive formats known
            ArchiveFormats.registerAll();
            try {
                write(repository, ".zip", "zip");
                write(repository, ".tar.gz", "tgz");
                write(repository, ".tar.bz2", "tbz2");
                write(repository, ".tar.xz", "txz");
            } finally {
                ArchiveFormats.unregisterAll();
            }
        }
    }

    private static void write(Repository repository, String suffix, String format) throws IOException, GitAPIException {
        // this is the file that we write the archive to
        File file = File.createTempFile("test", suffix);
        try (OutputStream out = new FileOutputStream(file)) {
            // finally call the ArchiveCommand to write out using the various supported formats
            try (Git git = new Git(repository)) {
                git.archive()
                        .setTree(repository.resolve("master"))
                        .setFormat(format)
                        .setOutputStream(out)
                        .call();
            }
        }

        System.out.println("Wrote " + file.length() + " bytes to " + file);

        // clean up here to not keep using more and more disk-space for these samples
        FileUtils.forceDelete(file);
    }
}
