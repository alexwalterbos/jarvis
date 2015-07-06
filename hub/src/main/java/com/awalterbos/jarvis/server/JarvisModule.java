package com.awalterbos.jarvis.server;

import com.awalterbos.antenna.Antenna;
import com.awalterbos.jarvis.server.bundles.JarvisHibernateBundle;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.hibernate.SessionFactory;

public class JarvisModule extends AbstractModule {

	@Provides
	public SessionFactory provideSessionFactory(JarvisHibernateBundle hibernateBundle) {
		return hibernateBundle.getSessionFactory();
	}

	@Override
	public void configure() {
	}

	@Singleton
	@Provides
	public Antenna antenna() {
		return new Antenna();
	}
}
