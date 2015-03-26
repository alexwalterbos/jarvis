package com.awalterbos.jarvis.server.bundles;

import com.awalterbos.jarvis.server.JarvisConfiguration;
import com.google.inject.Singleton;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;

@Singleton
public class JarvisMigrationsBundle extends MigrationsBundle<JarvisConfiguration> {
	@Override
	public DataSourceFactory getDataSourceFactory(JarvisConfiguration configuration) {
		return configuration.getDataSourceFactory();
	}
}
