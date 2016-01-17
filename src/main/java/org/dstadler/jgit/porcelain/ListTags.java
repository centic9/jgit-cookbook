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
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;



/**
 * Simple snippet which shows how to list all Tags
 *
 * @author dominik.stadler at gmx.at
 */
public class ListTags {

    public static void main(String[] args) throws IOException, GitAPIException {
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            try (Git git = new Git(repository)) {
                List<Ref> call = git.tagList().call();
                for (Ref ref : call) {
                    System.out.println("Tag: " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());

                    // fetch all commits for this tag
                    LogCommand log = git.log();

                    Ref peeledRef = repository.peel(ref);
                    if(peeledRef.getPeeledObjectId() != null) {
                    	log.add(peeledRef.getPeeledObjectId());
                    } else {
                    	log.add(ref.getObjectId());
                    }

        			Iterable<RevCommit> logs = log.call();
        			for (RevCommit rev : logs) {
        				System.out.println("Commit: " + rev /* + ", name: " + rev.getName() + ", id: " + rev.getId().getName() */);
        			}
                }
            }
        }
    }
}
