package com.awalterbos.jarvis.server.data.daos;

import com.awalterbos.jarvis.server.data.entities.Light;
import com.google.inject.Inject;
import org.hibernate.SessionFactory;

public class Lights extends EntityDAO<Light> {

	@Inject
	public Lights(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}
