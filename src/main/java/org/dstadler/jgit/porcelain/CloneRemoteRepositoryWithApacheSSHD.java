package org.dstadler.jgit.porcelain;

/*
   Copyright 2013, 2014 Dominik Stadler

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.dstadler.jgit.helper.SimpleProgressMonitor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.sshd.JGitKeyCache;
import org.eclipse.jgit.transport.sshd.SshdSessionFactory;
import org.eclipse.jgit.transport.sshd.SshdSessionFactoryBuilder;
import org.eclipse.jgit.util.FS;

/**
 * Simple snippet which shows how to clone a repository from a remote source
 * using the SSH protocol via the Apache SSHD libraries
 */
public class CloneRemoteRepositoryWithApacheSSHD {

    private static final String REMOTE_URL = "ssh://github.com/github/testrepo.git";

    public static void main(String[] args) throws IOException, GitAPIException {
		// set up the SSH factories. This requires a dependency on
		// 'org.eclipse.jgit.ssh.apache' in the build files
		// 'build.gradle' or 'pom.xml'
		File sshDir = new File(FS.DETECTED.userHome(), ".ssh");
		SshdSessionFactory sshdSessionFactory = new SshdSessionFactoryBuilder()
				.setPreferredAuthentications("publickey,keyboard-interactive,password")
				.setHomeDirectory(FS.DETECTED.userHome())
				.setSshDirectory(sshDir).build(new JGitKeyCache());
		SshSessionFactory.setInstance(sshdSessionFactory);

        // prepare a new folder for the cloned repository
        File localPath = File.createTempFile("TestGitRepository", "");
        if(!localPath.delete()) {
            throw new IOException("Could not delete temporary file " + localPath);
        }

        // then clone
        System.out.println("Cloning from " + REMOTE_URL + " to " + localPath);
        try (Git result = Git.cloneRepository()
                .setURI(REMOTE_URL)
                .setDirectory(localPath)
                .setProgressMonitor(new SimpleProgressMonitor())
                .call()) {
	        // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
	        System.out.println("Having repository: " + result.getRepository().getDirectory());
        }

        // clean up here to not keep using more and more disk-space for these samples
        FileUtils.deleteDirectory(localPath);
    }
}
