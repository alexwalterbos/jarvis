package com.awalterbos.jarvis.server;

import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.SneakyThrows;

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
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void run(JarvisConfiguration jarvisConfiguration, Environment environment) throws Exception {
		// Configuration handles by bundle in {@initialize}
	}
}
