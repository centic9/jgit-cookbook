package org.dstadler.jgit.porcelain;

import java.io.IOException;
import java.util.List;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.notes.Note;



/**
 * Simple snippet which shows how to load Notes in a Git repository
 * 
 * @author dominik.stadler@gmx.at
 */
public class ListNotes {

    public static void main(String[] args) throws IOException, GitAPIException {
        Repository repository = CookbookHelper.openJGitCookbookRepository();

        List<Note> call = new Git(repository).notesList().call();
        System.out.println("Listing " + call.size() + " notes");
        for (Note note : call) {
            System.out.println("Note: " + note + " " + note.getName() + " " + note.getData().getName() + "\nContent: ");

            // displaying the contents of the note is done via a simple blob-read
            ObjectLoader loader = repository.open(note.getData());
            loader.copyTo(System.out);
        }

        repository.close();
    }
}
