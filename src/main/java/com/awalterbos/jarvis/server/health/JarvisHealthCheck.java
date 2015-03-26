package com.awalterbos.jarvis.server.health;

import com.awalterbos.jarvis.server.JarvisApplication;
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
