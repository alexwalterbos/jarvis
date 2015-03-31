package com.awalterbos.jarvis.server.data.daos;

import com.awalterbos.jarvis.server.data.entities.Group;
import com.google.inject.Inject;
import org.hibernate.SessionFactory;

public class Groups extends EntityDAO<Group> {

	@Inject
	public Groups(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}
