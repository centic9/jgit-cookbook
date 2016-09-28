package org.dstadler.jgit.porcelain;

/*
   Copyright 2015 Dominik Stadler

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

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.URIish;

import java.io.File;
import java.io.IOException;



/**
 * Simple snippet which shows how to clone a repository from a remote source
 * via ssh protocol and username/password authentication.
 *
 * @author dominik.stadler at gmx.at
 */
public class CloneRemoteRepositoryWithAuthentication {
    private static final String REMOTE_URL = "ssh://<user>:<pwd>@<host>:22/<path-to-remote-repo>/";

    public static void main(String[] args) throws IOException, GitAPIException {
        // this is necessary when the remote host does not have a valid certificate, ideally we would install the certificate in the JVM
        // instead of this unsecure workaround!
        CredentialsProvider allowHosts = new CredentialsProvider() {

            @Override
            public boolean supports(CredentialItem... items) {
                for(CredentialItem item : items) {
                    if((item instanceof CredentialItem.YesNoType)) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean get(URIish uri, CredentialItem... items) throws UnsupportedCredentialItem {
                for(CredentialItem item : items) {
                    if(item instanceof CredentialItem.YesNoType) {
                        ((CredentialItem.YesNoType)item).setValue(true);
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean isInteractive() {
                return false;
            }
        };

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
                .setCredentialsProvider(allowHosts)
                .call()) {
	        // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
	        System.out.println("Having repository: " + result.getRepository().getDirectory());
        }
    }
}
