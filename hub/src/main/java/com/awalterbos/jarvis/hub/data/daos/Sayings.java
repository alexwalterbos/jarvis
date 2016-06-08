package com.awalterbos.jarvis.hub.data.daos;

import com.awalterbos.jarvis.hub.data.entities.Saying;
import com.google.inject.Inject;
import org.hibernate.SessionFactory;

public class Sayings extends EntityDao<Saying> {

	@Inject
	public Sayings(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}
