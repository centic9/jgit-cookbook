package org.dstadler.jgit.porcelain;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Simple snippet which shows how to set a modified tracked file back to its state
 * in the most recent commit. This does not make a new commit that reverts a previous commit;
 * this reverts a modified file back to its unmodified state (according to most recent commit)
 *
 * @author JordanMartinez
 */
public class RevertChanges {

    public static void main(String[] args) throws IOException, GitAPIException {
        try (Repository repository = CookbookHelper.createNewRepository()) {
            System.out.println("Listing local branches:");
            try (Git git = new Git(repository)) {
                // set up a file
                String fileName = "temptFile.txt";
                File tempFile = new File(repository.getDirectory().getParentFile(), fileName);
                tempFile.createNewFile();
                Path tempFilePath = tempFile.toPath();

                // write some initial text to it
                String initialText = "Initial Text";
                Files.write(tempFilePath, initialText.getBytes());

                // add the file and commit it
                git.add().addFilepattern(fileName).call();
                git.commit().setMessage("Added untracked file " + fileName + "to repo").call();

                // modify the file
                Files.write(tempFilePath, "Some modifications".getBytes(), StandardOpenOption.APPEND);

                // assert that file does not equal initialText
                assert !initialText.equals(readFile(tempFilePath, Charset.defaultCharset()));

                // revert the changes
                git.checkout().setStartPoint("HEAD").addPath(fileName).call();

                assert initialText.equals(readFile(tempFilePath, Charset.defaultCharset()));


            }
        }

    }

    private static String readFile(Path file, Charset charset) throws IOException {
        byte[] bytes = Files.readAllBytes(file);
        CharBuffer chars = charset.decode(ByteBuffer.wrap(bytes));
        return chars.toString();
    }
}
