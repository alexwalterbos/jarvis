package com.awalterbos.jarvis.server.bundles;

import javax.persistence.Entity;

import com.awalterbos.jarvis.server.JarvisConfiguration;
import com.awalterbos.jarvis.server.JarvisApplication;
import com.google.common.collect.ImmutableList;
import com.google.inject.Singleton;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;
import org.reflections.Reflections;

@Singleton
public class JarvisHibernateBundle extends HibernateBundle<JarvisConfiguration> implements
		ConfiguredBundle<JarvisConfiguration> {

	public JarvisHibernateBundle() {
		super(myDbEntities(), new SessionFactoryFactory());
	}

	private static ImmutableList<Class<?>> myDbEntities() {
		Reflections reflections = new Reflections(JarvisApplication.class.getPackage().getName());
		return ImmutableList.copyOf(reflections.getTypesAnnotatedWith(Entity.class));
	}

	@Override
	public DataSourceFactory getDataSourceFactory(JarvisConfiguration jarvisConfiguration) {
		return jarvisConfiguration.getDataSourceFactory();
	}
}
