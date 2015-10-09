package org.dstadler.jgit;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Test;

/**
 * Tests which show issues with JGit that we reported upstream.
 */
public class JGitBugTest {
    @Test
    public void testRevWalkDisposeClosesReader() throws IOException {
        try (Repository repo = CookbookHelper.openJGitCookbookRepository()) {
            try (ObjectReader reader = repo.newObjectReader()) {
                try (RevWalk walk = new RevWalk(reader)) {
                    walk.dispose();

                    Ref head = repo.getRef("refs/heads/master");
                    System.out.println("Found head: " + head);

                    ObjectLoader loader = reader.open(head.getObjectId());
                    assertNotNull(loader);
                }
            }
        }
    }
}
