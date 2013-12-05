package org.dstadler.jgit.porcelain;

import java.io.IOException;
import java.util.List;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.notes.Note;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;



/**
 * Simple snippet which shows how to load Notes in a Git repository
 *
 * @author dominik.stadler@gmx.at
 */
public class AddAndListNoteOfCommit {

	public static void main(String[] args) throws IOException, GitAPIException {
		Repository repository = CookbookHelper.openJGitCookbookRepository();

		Ref head = repository.getRef("refs/heads/master");
		System.out.println("Found head: " + head);

        RevWalk walk = new RevWalk(repository);
        RevCommit commit = walk.parseCommit(head.getObjectId());
        System.out.println("Found Commit: " + commit);

        new Git(repository).notesAdd().setMessage("some note message").setObjectId(commit).call();
        System.out.println("Added Note to commit " + commit);

		List<Note> call = new Git(repository).notesList().call();
		System.out.println("Listing " + call.size() + " notes");
		for(Note note : call) {
			// check if we found the note for this commit
			if(!note.getName().equals(head.getObjectId().getName())) {
				System.out.println("Note " + note + " did not match commit " + head);
				continue;
			}
			System.out.println("Found note: " + note + " for commit " + head);

			// displaying the contents of the note is done via a simple blob-read
			ObjectLoader loader = repository.open(note.getData());
			loader.copyTo(System.out);
		}

		repository.close();
	}
}
