package com.awalterbos.jarvis.server;

import com.awalterbos.jarvis.server.entities.Saying;
import com.google.inject.Inject;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class SayingDAO extends AbstractDAO<Saying> {

	@Inject
	public SayingDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Saying findById(Long id) {
		return get(id);
	}

	public Saying create(Saying saying) {
		return persist(saying);
	}
}
