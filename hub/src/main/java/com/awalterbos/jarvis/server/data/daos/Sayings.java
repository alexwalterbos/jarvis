package com.awalterbos.jarvis.server.data.daos;

import com.awalterbos.jarvis.server.data.entities.Saying;
import com.google.inject.Inject;
import org.hibernate.SessionFactory;

public class Sayings extends EntityDAO<Saying> {

	@Inject
	public Sayings(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}
