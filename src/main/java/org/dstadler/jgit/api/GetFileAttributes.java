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

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

import java.io.IOException;

/**
 * Snippet which shows how to use RevWalk and TreeWalk to read the file
 * attributes like execution-bit and type of file/directory/...
 *
 * @author dominik.stadler at gmx.at
 */
public class GetFileAttributes {

    public static void main(String[] args) throws IOException {
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            // find the Tree for current HEAD
            RevTree tree = getTree(repository);

            printFile(repository, tree);

            printDirectory(repository, tree);

            // there is also FileMode.SYMLINK for symbolic links, but this is not handled here yet
        }
    }

    private static RevTree getTree(Repository repository) throws IOException {
        ObjectId lastCommitId = repository.resolve(Constants.HEAD);

        // a RevWalk allows to walk over commits based on some filtering
        try (RevWalk revWalk = new RevWalk(repository)) {
            RevCommit commit = revWalk.parseCommit(lastCommitId);

            System.out.println("Time of commit (seconds since epoch): " + commit.getCommitTime());

            // and using commit's tree find the path
            RevTree tree = commit.getTree();
            System.out.println("Having tree: " + tree);
            return tree;
        }
    }

    private static void printFile(Repository repository, RevTree tree) throws IOException {
        // now try to find a specific file
        try (TreeWalk treeWalk = new TreeWalk(repository)) {
            treeWalk.addTree(tree);
            treeWalk.setRecursive(false);
            treeWalk.setFilter(PathFilter.create("README.md"));
            if (!treeWalk.next()) {
                throw new IllegalStateException("Did not find expected file 'README.md'");
            }

            // FileMode specifies the type of file, FileMode.REGULAR_FILE for normal file, FileMode.EXECUTABLE_FILE for executable bit
    // set
            FileMode fileMode = treeWalk.getFileMode(0);
            ObjectLoader loader = repository.open(treeWalk.getObjectId(0));
            System.out.println("README.md: " + getFileMode(fileMode) + ", type: " + fileMode.getObjectType() + ", mode: " + fileMode +
                    " size: " + loader.getSize());
        }
    }

    private static void printDirectory(Repository repository, RevTree tree) throws IOException {
        // look at directory, this has FileMode.TREE
        try (TreeWalk treeWalk = new TreeWalk(repository)) {
            treeWalk.addTree(tree);
            treeWalk.setRecursive(false);
            treeWalk.setFilter(PathFilter.create("src"));
            if (!treeWalk.next()) {
                throw new IllegalStateException("Did not find expected folder 'src'");
            }

            // FileMode now indicates that this is a directory, i.e. FileMode.TREE.equals(fileMode) holds true
            FileMode fileMode = treeWalk.getFileMode(0);
            System.out.println("src: " + getFileMode(fileMode) + ", type: " + fileMode.getObjectType() + ", mode: " + fileMode);
        }
    }

    private static String getFileMode(FileMode fileMode) {
        if (fileMode.equals(FileMode.EXECUTABLE_FILE)) {
            return "Executable File";
        } else if (fileMode.equals(FileMode.REGULAR_FILE)) {
            return "Normal File";
        } else if (fileMode.equals(FileMode.TREE)) {
            return "Directory";
        } else if (fileMode.equals(FileMode.SYMLINK)) {
            return "Symlink";
        } else {
            // there are a few others, see FileMode javadoc for details
            throw new IllegalArgumentException("Unknown type of file encountered: " + fileMode);
        }
    }
}
