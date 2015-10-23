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
import java.util.List;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RebaseCommand.InteractiveHandler;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.IllegalTodoFileModification;
import org.eclipse.jgit.lib.RebaseTodoLine;
import org.eclipse.jgit.lib.RebaseTodoLine.Action;
import org.eclipse.jgit.lib.Repository;

/**
 * Snippet which shows how to rebase local changes onto a remote branch.
 *
 * See also http://stackoverflow.com/questions/22945257/jgit-how-to-squash-commits
 *
 * @author dominik.stadler at gmx.at
 */
public class RebaseToOriginMaster {

    public static void main(String[] args) throws IOException, GitAPIException {
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            // all refs
            try (Git git = new Git(repository)) {
                InteractiveHandler handler = new InteractiveHandler() {
                    @Override
                    public void prepareSteps(List<RebaseTodoLine> steps) {
                        for(RebaseTodoLine step : steps) {
                            try {
                                step.setAction(Action.EDIT);
                            } catch (IllegalTodoFileModification e) {
                                throw new IllegalStateException(e);
                            }
                        }
                    }

                    @Override
                    public String modifyCommitMessage(String oldMessage) {
                        return oldMessage;
                    }
                };

                git.rebase().setUpstream("origin/master").runInteractively(handler).call();
                System.out.println("Rebased..");
            }
        }
    }
}
