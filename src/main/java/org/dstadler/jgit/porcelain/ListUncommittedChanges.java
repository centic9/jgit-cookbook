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

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.IndexDiff.StageState;
import org.eclipse.jgit.lib.Repository;



/**
 * Simple snippet which shows how to list various types of uncommitted changes
 * of a Git repository
 *
 * @author dominik.stadler at gmx.at
 */
public class ListUncommittedChanges {

    public static void main(String[] args) throws IOException, GitAPIException {
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            System.out.println("Listing uncommitted changes:");
            try (Git git = new Git(repository)) {
                Status status = git.status().call();
                Set<String> conflicting = status.getConflicting();
                for(String conflict : conflicting) {
                    System.out.println("Conflicting: " + conflict);
                }

                Set<String> added = status.getAdded();
                for(String add : added) {
                    System.out.println("Added: " + add);
                }

                Set<String> changed = status.getChanged();
                for(String change : changed) {
                    System.out.println("Change: " + change);
                }

                Set<String> missing = status.getMissing();
                for(String miss : missing) {
                    System.out.println("Missing: " + miss);
                }

                Set<String> modified = status.getModified();
                for(String modify : modified) {
                    System.out.println("Modification: " + modify);
                }

                Set<String> removed = status.getRemoved();
                for(String remove : removed) {
                    System.out.println("Removed: " + remove);
                }

                Set<String> uncommittedChanges = status.getUncommittedChanges();
                for(String uncommitted : uncommittedChanges) {
                    System.out.println("Uncommitted: " + uncommitted);
                }

                Set<String> untracked = status.getUntracked();
                for(String untrack : untracked) {
                    System.out.println("Untracked: " + untrack);
                }

                Set<String> untrackedFolders = status.getUntrackedFolders();
                for(String untrack : untrackedFolders) {
                    System.out.println("Untracked Folder: " + untrack);
                }

                Map<String, StageState> conflictingStageState = status.getConflictingStageState();
                for(Map.Entry<String, StageState> entry : conflictingStageState.entrySet()) {
                    System.out.println("ConflictingState: " + entry);
                }
            }
        }
    }
}
