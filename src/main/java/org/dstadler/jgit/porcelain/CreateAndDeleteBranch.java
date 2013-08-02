package org.dstadler.jgit.porcelain;

import java.io.IOException;
import java.util.List;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;



/**
 * Simple snippet which shows how to create and delete branches
 *
 * @author dominik.stadler@gmx.at
 */
public class CreateAndDeleteBranch {

	public static void main(String[] args) throws IOException, GitAPIException {
		// prepare test-repository
		Repository repository = CookbookHelper.openJGitCookbookRepository();
		Git git = new Git(repository);

		List<Ref> call = new Git(repository).branchList().call();
		for(Ref ref : call) {
			System.out.println("Branch-Before: " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());
		}

        // run the add-call
        git.branchCreate()
			.setName("testbranch")
			.call();

		call = new Git(repository).branchList().call();
		for(Ref ref : call) {
			System.out.println("Branch-Created: " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());
		}

		// run the delete-call
		git.branchDelete()
			.setBranchNames("testbranch")
			.call();

		call = new Git(repository).branchList().call();
		for(Ref ref : call) {
			System.out.println("Branch-After: " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());
		}

		repository.close();
	}
}
