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

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.GitCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.Repository;

/**
 * Note: This snippet is not done and likely does not show anything useful yet
 *
 * Snippet which shows how to mark files as assumed-unchanged (git update-index --assume-unchanged)
 */
public class UpdateIndex {

	public static void main(String[] args) throws IOException, GitAPIException {
		try (final Repository repo = CookbookHelper.openJGitCookbookRepository()) {
    		try (final Git git = new Git(repo)) {
    		    final String testFile = "README.md";

    			// Modify the file
    			FileUtils.write(new File(testFile), new Date().toString(), "UTF-8");
    			System.out.println("Modified files: " + getModifiedFiles(git));

    			new AssumeChangedCommand(repo, testFile, true).call();
    			System.out.println("Modified files after assume-changed: " + getModifiedFiles(git));

    			new AssumeChangedCommand(repo, testFile, false).call();
    			System.out.println("Modified files after no-assume-changed: " + getModifiedFiles(git));

    			git.checkout().addPath(testFile).call();
    			System.out.println("Modified files after reset: " + getModifiedFiles(git));
    		}
		}
	}

	private static Set<String> getModifiedFiles(Git git) throws NoWorkTreeException, GitAPIException {
		Status status = git.status().call();
		return status.getModified();
	}

	private static class AssumeChangedCommand extends GitCommand<String> {
		private final String fileName;
		private final boolean assumeUnchanged;

		AssumeChangedCommand(Repository repository, String fileName, boolean assumedUnchanged) {
			super(repository);

			this.fileName = fileName;
			this.assumeUnchanged = assumedUnchanged;
		}

		@Override
		public String call() throws GitAPIException {
			try {
				DirCache index = repo.lockDirCache();
				DirCacheEntry entry = index.getEntry(fileName);

				if (entry != null) {
					entry.setAssumeValid(assumeUnchanged);
					index.write();
					index.commit();
					return entry.getPathString();
				}
			} catch (IOException e) {
				throw new JGitInternalException(e.getMessage(), e);
			}

			return null;
		}
	}
}
