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
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.Repository;

/**
 * Snippet which shows how to work with the Index
 *
 * @author dominik.stadler at gmx.at
 */
public class ListIndex {

    public static void main(String[] args) throws IOException {
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            // DirCache contains all files of the repository
            DirCache index = DirCache.read(repository);
            System.out.println("DirCache has " + index.getEntryCount() + " items");
            for (int i = 0; i < index.getEntryCount(); i++) {
                // the number after the AnyObjectId is the "stage", see the constants in DirCacheEntry
                System.out.println("Item " + i + ": " + index.getEntry(i));
            }

            //
            System.out.println("Now printing staged items...");
            for (int i = 0; i < index.getEntryCount(); i++) {
                DirCacheEntry entry = index.getEntry(i);
                if (entry.getStage() != DirCacheEntry.STAGE_0) {
                    System.out.println("Item " + i + ": " + entry);
                }
            }
        }
    }
}
