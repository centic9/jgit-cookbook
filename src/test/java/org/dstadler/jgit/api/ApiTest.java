package org.dstadler.jgit.api;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Test;


public class ApiTest {
    @Test
    public void runSamples() throws Exception {
        // simply call all the samples to see any severe problems with the samples
        CheckMergeStatusOfCommit.main(null);
        GetCommitMessage.main(null);
        GetFileAttributes.main(null);
        GetRefFromName.main(null);
        GetRevCommitFromObjectId.main(null);
        GetRevTreeFromObjectId.main(null);
        PrintRemotes.main(null);
        ReadBlobContents.main(null);
        ReadFileFromCommit.main(null);
        ReadTagFromName.main(null);
        ReadUserConfig.main(null);
        ResolveRef.main(null);
        ShowBranchTrackingStatus.main(null);
        WalkAllCommits.main(null);
        WalkRev.main(null);
        WalkFromToRev.main(null);
        WalkTreeNonRecursive.main(null);
        WalkTreeRecursive.main(null);
    }

    @Test
    public void testFailure() throws IOException {
        // perform a specific check for things that seem to fail in github/travis checking
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            try (RevWalk revWalk = new RevWalk( repository )) {
                ObjectId resolve = repository.resolve( "refs/heads/master" );
                assertNotNull("Did not find refs/heads/master", resolve);

                RevCommit masterHead = revWalk.parseCommit( resolve);

                // first a commit that was merged
                ObjectId id = repository.resolve("05d18a76875716fbdbd2c200091b40caa06c713d");
                System.out.println("Had id: " + id);
                assertNotNull("Did not find specific commit", resolve);

                RevCommit otherHead = revWalk.parseCommit( id );
                if( revWalk.isMergedInto( otherHead, masterHead ) ) {
                    System.out.println("Commit " + otherHead + " is merged into master");
                } else {
                    System.out.println("Commit " + otherHead + " is NOT merged into master");
                }
            }
        }
    }
}
