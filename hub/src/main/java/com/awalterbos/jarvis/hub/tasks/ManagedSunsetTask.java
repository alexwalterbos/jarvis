package com.awalterbos.jarvis.hub.tasks;

import com.google.common.util.concurrent.AbstractScheduledService;
import com.google.inject.Inject;
import io.dropwizard.lifecycle.Managed;

public class ManagedSunsetTask implements Managed {

	private AbstractScheduledService sunsetTask;

	@Inject
	public ManagedSunsetTask(SunsetTask task) {
		this.sunsetTask = task;
	}

	public void start() throws Exception {
		sunsetTask.startAsync().awaitRunning();
	}

	public void stop() throws Exception {
		sunsetTask.stopAsync().awaitTerminated();
	}
}
