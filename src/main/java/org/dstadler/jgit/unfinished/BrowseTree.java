package org.dstadler.jgit.unfinished;

import java.io.IOException;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;

/**
 * Simple snippet which shows how to use RevWalk to iterate over items in a file-tree
 *
 * @author dominik.stadler@gmx.at
 */
public class BrowseTree {

	public static void main(String[] args) throws IOException, GitAPIException {
		Repository repository = CookbookHelper.openJGitCookbookRepository();

		ObjectId revId = repository.resolve(Constants.HEAD);
		TreeWalk treeWalk = new TreeWalk(repository);

		treeWalk.addTree(new RevWalk(repository).parseTree(revId));

		while (treeWalk.next())
		{
			System.out.println("---------------------------");
			System.out.append("name: ").println(treeWalk.getNameString());
			System.out.append("path: ").println(treeWalk.getPathString());

			ObjectLoader loader = repository.open(treeWalk.getObjectId(0));

			System.out.append("directory: ").println(loader.getType()
					== Constants.OBJ_TREE);
			System.out.append("size: ").println(loader.getSize());
		}

		repository.close();
	}
}
