package com.awalterbos.jarvis.hub.tasks;

import com.google.inject.Inject;
import io.dropwizard.lifecycle.Managed;

public class ManagedNightNightTask implements Managed   {

	private NightNightTask task;

	@Inject
	public ManagedNightNightTask(NightNightTask task) {
		this.task = task;
	}
	public void start() throws Exception {
		task.startAsync().awaitRunning();
	}

	public void stop() throws Exception {
		task.stopAsync().awaitTerminated();
	}
}
