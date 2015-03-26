package com.awalterbos.jarvis.server;

import com.awalterbos.jarvis.server.bundles.JarvisHibernateBundle;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.hibernate.SessionFactory;

public class JarvisModule extends AbstractModule {

	@Provides
	public SessionFactory provideSessionFactory(JarvisHibernateBundle hibernateBundle) {
		return hibernateBundle.getSessionFactory();
	}

	@Override
	public void configure() {
	}
}
