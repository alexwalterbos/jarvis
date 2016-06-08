package com.awalterbos.jarvis.hub.health;

import com.awalterbos.jarvis.hub.JarvisApplication;
import com.hubspot.dropwizard.guice.InjectableHealthCheck;

public class JarvisHealthCheck extends InjectableHealthCheck {
	@Override
	public String getName() {
		return JarvisApplication.NAME;
	}

	@Override
	protected Result check() throws Exception {
		return Result.healthy();
	}
}
