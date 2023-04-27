package org.dstadler.jgit.helper;

import org.eclipse.jgit.lib.ProgressMonitor;

public class SimpleProgressMonitor implements ProgressMonitor {
    @Override
    public void start(int totalTasks) {
        System.out.println("Starting work on " + totalTasks + " tasks");
    }

    @Override
    public void beginTask(String title, int totalWork) {
        System.out.println("Start " + title + ": " + totalWork);
    }

    @Override
    public void update(int completed) {
        System.out.print(completed + "-");
    }

    @Override
    public void endTask() {
        System.out.println("Done");
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

	@Override
	public void showDuration(boolean enabled) {
		// ignored here
	}
}
