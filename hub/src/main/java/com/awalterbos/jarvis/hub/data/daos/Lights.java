package com.awalterbos.jarvis.hub.data.daos;

import com.awalterbos.jarvis.hub.data.entities.Light;
import com.google.inject.Inject;
import org.hibernate.SessionFactory;

public class Lights extends EntityDao<Light> {

	@Inject
	public Lights(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}
