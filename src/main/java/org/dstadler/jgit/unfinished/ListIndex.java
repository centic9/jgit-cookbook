package org.dstadler.jgit.unfinished;

import java.io.IOException;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.Repository;

/**
 * Snippet which shows how to work with the Index
 *
 * @author dominik.stadler@gmx.at
 */
public class ListIndex {

	public static void main(String[] args) throws IOException, GitAPIException {
		Repository repository = CookbookHelper.openJGitCookbookRepository();

		// DirCache contains all files of the repository
		DirCache index = DirCache.read(repository);
		System.out.println("DirCache has " + index.getEntryCount() + " items");
		for(int i = 0; i < index.getEntryCount();i++) {
			// the number after the AnyObjectId is the "stage", see the constants in DirCacheEntry
			System.out.println("Item " + i + ": " + index.getEntry(i));
		}

		//
		System.out.println("Now printing staged items...");
		for(int i = 0; i < index.getEntryCount();i++) {
			DirCacheEntry entry = index.getEntry(i);
			if(entry.getStage() != DirCacheEntry.STAGE_0) {
				System.out.println("Item " + i + ": " + entry);
			}
		}

		repository.close();
	}
}
