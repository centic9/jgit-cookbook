package org.dstadler.jgit.api;

import java.io.IOException;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

/**
 * Simple snippet which shows how to retrieve a Ref for some reference string.
 */
public class GetRefFromName {
	public static void main(String[] args) throws IOException {
		Repository repository = CookbookHelper.openJGitCookbookRepository();

		// the Ref holds an ObjectId for any type of object (tree, commit, blob, tree)
		Ref head = repository.getRef("refs/heads/master");
		System.out.println("Ref of refs/heads/master: " + head);

		repository.close();
	}
}
