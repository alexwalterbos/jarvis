package com.awalterbos.jarvis.server.bundles;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import java.util.Set;

import com.awalterbos.jarvis.server.JarvisConfiguration;
import com.awalterbos.jarvis.server.JarvisApplication;
import com.awalterbos.jarvis.server.data.entities.EntityWithID;
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
		Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(Entity.class);
		entityClasses.remove(EntityWithID.class);
		return ImmutableList.copyOf(entityClasses);
	}

	@Override
	public DataSourceFactory getDataSourceFactory(JarvisConfiguration jarvisConfiguration) {
		return jarvisConfiguration.getDataSourceFactory();
	}
}
