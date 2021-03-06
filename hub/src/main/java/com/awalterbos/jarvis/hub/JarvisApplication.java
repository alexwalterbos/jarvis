package com.awalterbos.jarvis.hub;

import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class JarvisApplication extends Application<JarvisConfiguration> {

	public static final String NAME = "Jarvis";

	public static void main(String[] args) throws Exception {
		new JarvisApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<JarvisConfiguration> bootstrap) {
		GuiceBundle<JarvisConfiguration> guiceBundle = GuiceBundle.<JarvisConfiguration>newBuilder()
				.addModule(new JarvisModule())
				.enableAutoConfig(getClass().getPackage().getName())
				.setConfigClass(JarvisConfiguration.class)
				.build();

		bootstrap.addBundle(guiceBundle);
		bootstrap.addBundle(new AssetsBundle("/app", "/", "index.html"));
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void run(JarvisConfiguration jarvisConfiguration, Environment environment) throws Exception {
		// Everything is auto-configured in initialize()
	}
}
