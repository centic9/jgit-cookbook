package org.dstadler.jgit.porcelain;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RevertCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * Simple snippet which shows how to revert a previous commit
 *
 * @author JordanMartinez
 */
public class RevertCommit {

    public static void main(String[] args) throws IOException, GitAPIException {
        final File path;
        try (Repository repository = CookbookHelper.createNewRepository()) {
            try (Git git = new Git(repository)) {
                path = repository.getWorkTree();
                System.out.println("Repository at " + path);

                // Create a new file and add it to the index
                File newFile = new File(path, "file1.txt");
                FileUtils.writeStringToFile(newFile, "Line 1\r\n", "UTF-8", true);
                git.add().addFilepattern("file1.txt").call();
                RevCommit rev1 = git.commit().setAuthor("test", "test@test.com").setMessage("Commit Log 1").call();
                System.out.println("Rev1: " + rev1);

                // commit some changes
                FileUtils.writeStringToFile(newFile, "Line 2\r\n", "UTF-8", true);
                git.add().addFilepattern("file1.txt").call();
                RevCommit rev2 = git.commit().setAll(true).setAuthor("test", "test@test.com").setMessage("Commit Log 2").call();
                System.out.println("Rev2: " + rev2);

                // commit some changes
                FileUtils.writeStringToFile(newFile, "Line 3\r\n", "UTF-8", true);
                git.add().addFilepattern("file1.txt").call();
                RevCommit rev3 = git.commit().setAll(true).setAuthor("test", "test@test.com").setMessage("Commit Log 3").call();
                System.out.println("Rev3: " + rev3);

                // print logs
                Iterable<RevCommit> gitLog = git.log().call();
                for (RevCommit logMessage : gitLog) {
                    System.out.println("Before revert: " + logMessage.getName() + " - " + logMessage.getFullMessage());
                }

                RevertCommand revertCommand = git.revert();
                // revert to revision 2
                revertCommand.include(rev3);
                RevCommit revCommit = revertCommand.call();
                System.out.println("Reverted: " + revCommit);
                System.out.println("Reverted refs: " + revertCommand.getRevertedRefs());
                System.out.println("Unmerged paths: " + revertCommand.getUnmergedPaths());
                System.out.println("Failing results: " + revertCommand.getFailingResult());

                // print logs
                gitLog = git.log().call();
                for (RevCommit logMessage : gitLog) {
                    System.out.println("After revert: " + logMessage.getName() + " - " + logMessage.getFullMessage());
                }

                System.out.println("File contents: " + FileUtils.readFileToString(newFile, "UTF-8"));
            }
        }

        // clean up here to not keep using more and more disk-space for these samples
        FileUtils.deleteDirectory(path);
    }
}
