package org.dstadler.jgit.unfinished;

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
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;

/**
 * Simple snippet which shows how to use RevWalk to iterate over items in a file-tree
 * 
 * @author dominik.stadler at gmx.at
 */
public class BrowseTree {

    public static void main(String[] args) throws IOException, GitAPIException {
        Repository repository = CookbookHelper.openJGitCookbookRepository();

        ObjectId revId = repository.resolve(Constants.HEAD);
        TreeWalk treeWalk = new TreeWalk(repository);

        treeWalk.addTree(new RevWalk(repository).parseTree(revId));

        while (treeWalk.next())
        {
            System.out.println("---------------------------");
            System.out.append("name: ").println(treeWalk.getNameString());
            System.out.append("path: ").println(treeWalk.getPathString());

            ObjectLoader loader = repository.open(treeWalk.getObjectId(0));

            System.out.append("directory: ").println(loader.getType()
                    == Constants.OBJ_TREE);
            System.out.append("size: ").println(loader.getSize());
        }

        repository.close();
    }
}
