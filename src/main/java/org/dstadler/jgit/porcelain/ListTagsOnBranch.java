package org.dstadler.jgit.porcelain;

/*
   Copyright 2021 Dominik Stadler

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
import java.util.Map;
import java.util.stream.Collectors;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * A snippet which fetches all the tags of the repository and then walks
 * commits on a given branch and prints out whenever a tag is found for
 * a commit.
 *
 * I.e. it prints all tags that are applied to commits on the branch.
 *
 * @author dominik.stadler at gmx.at
 */
public class ListTagsOnBranch {

	private static final String BRANCH = "remotes/origin/master";

	public static void main(String[] args) throws IOException, GitAPIException {
		long start = System.currentTimeMillis();
		try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
			try (Git git = new Git(repository)) {
				// create a map of commit-refs and corresponding list of tags
				Map<ObjectId, List<Ref>> tagsMap = git.tagList().call().stream()
						.collect(Collectors.groupingBy(ref -> {
							if (ref.getPeeledObjectId() == null) {
								return ref.getObjectId();
							} else {
								return ref.getPeeledObjectId();
							}
						}, Collectors.mapping(ref -> ref, Collectors.toList())));

				Iterable<RevCommit> logs = git.log()
						.add(repository.resolve(BRANCH))
						.call();
				int count = 0;

				for (RevCommit rev : logs) {
					//System.out.println("Commit: " + rev /* + ", name: " + rev.getName() + ", id: " + rev.getId().getName() */);
					count++;

					if (tagsMap.containsKey(rev.getId())) {
						System.out.println("Found tags " + tagsMap.get(rev.getId()) + " for " + rev);
					}
				}
                System.out.println("Had " + count + " commits overall on branch " + BRANCH
						+ ", iteration took " + (System.currentTimeMillis() - start) + "ms");
			}
		}
	}
}
