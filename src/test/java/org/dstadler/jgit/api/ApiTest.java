package org.dstadler.jgit.api;

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
        ReadUserConfig.main(null);
        ResolveRef.main(null);
        ShowBranchTrackingStatus.main(null);
        WalkRev.main(null);
    }
}
