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
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revplot.PlotCommitList;
import org.eclipse.jgit.revplot.PlotLane;
import org.eclipse.jgit.revplot.PlotWalk;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * Snippet which shows how to use PlotWalk to read from a specific commit.
 *
 * @author dominik.stadler at gmx.at
 */
public class ListChildrenOfCommit {

    public static void main(String[] args) throws IOException {
        Repository repository = CookbookHelper.openJGitCookbookRepository();

        PlotWalk revWalk = new PlotWalk(repository);
        ObjectId rootId = repository.resolve("refs/heads/master");
        RevCommit root = revWalk.parseCommit(rootId);
        revWalk.markStart(root);
        PlotCommitList<PlotLane> plotCommitList = new PlotCommitList<>();
        plotCommitList.source(revWalk);
        plotCommitList.fillTo(Integer.MAX_VALUE);

        System.out.println("Printing children of commit " + root);
        for (RevCommit com : revWalk) {
            System.out.println("Child: " + com);
        }

        System.out.println("Printing with next()");
        System.out.println("next: " + revWalk.next());

        repository.close();
    }
}
