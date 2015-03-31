package com.awalterbos.jarvis.server.data.daos;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class EntityDAO<T> extends AbstractDAO<T> {

	public EntityDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public T findById(long id) {
		return get(id);
	}

	public T createOrUpdate(T t) {
		return persist(checkNotNull(t));
	}

	public void delete(T t) {
		currentSession().delete(checkNotNull(t));
	}

	public void delete(long id) {
		currentSession().delete(checkNotNull(findById(id)));
	}

}
