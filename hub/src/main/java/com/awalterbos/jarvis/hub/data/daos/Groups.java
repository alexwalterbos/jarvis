package com.awalterbos.jarvis.hub.data.daos;

import java.util.List;

import com.awalterbos.jarvis.hub.data.entities.Group;
import com.google.inject.Inject;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class Groups extends EntityDao<Group> {

	@Inject
	public Groups(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List findSunsetTriggered() {
		System.out.println("Looking for sunset-triggered groups");
		Criteria criteria = criteria().add(Restrictions.eq("triggerOnSunset", Boolean.TRUE));
		List list = criteria.list();
		System.out.println("List of size: " + list.size());
		return list;
	}
}
