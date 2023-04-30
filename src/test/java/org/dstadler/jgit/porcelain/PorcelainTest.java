package org.dstadler.jgit.porcelain;

import org.eclipse.jgit.api.errors.TransportException;
import org.junit.Test;


public class PorcelainTest {
    @Test
    public void runSamples() throws Exception {
        // simply call all the samples to see any severe problems with the samples
        AddAndListNoteOfCommit.main(null);
        AddFile.main(null);
        BlameFile.main(null);
        CheckoutGitHubPullRequest.main(null);
        CleanUntrackedFiles.main(null);
        CloneRemoteRepository.main(null);
		try {
			CloneRemoteRepositoryWithApacheSSHD.main(null);
		} catch (TransportException e) {
			// this can happen when SSH is not set up properly against github.com
			System.out.println("Cloning with Apache SSHD failed");
			e.printStackTrace();
		}
        // does not run without changes: CloneRemoteRepositoryWithAuthentication.main(null);
        // TODO: sometimes fails because there are still files open?!: CollectGarbage.main(null);
        CommitAll.main(null);
        CommitFile.main(null);
        CreateAndDeleteBranch.main(null);
        CreateAndDeleteTag.main(null);
        CreateArchive.main(null);
        CreateCustomFormatArchive.main(null);
        CreateListApplyAndDropStash.main(null);
        DiffFilesInCommit.main(null);
        DiffRenamedFile.main(null);
        FetchRemoteCommits.main(null);
        FetchRemoteCommitsWithPrune.main(null);
        InitRepository.main(null);
        ListBranches.main(null);
        ListNotes.main(null);
        ListRemoteRepository.main(null);
        ListRemotes.main(null);
        ListTags.main(null);
        ListUncommittedChanges.main(null);
        MergeChanges.main(null);
        PushToRemoteRepository.main(null);
        RebaseToOriginMaster.main(null);
        RevertChanges.main(null);
        RevertCommit.main(null);
        ShowBlame.main(null);
        ShowBranchDiff.main(null);
        ShowChangedFilesBetweenCommits.main(null);
        ShowFileDiff.main(null);
        ShowLog.main(null);
        ShowStatus.main(null);
        TrackMaster.main(null);
        WalkAllCommits.main(null);
    }
}
