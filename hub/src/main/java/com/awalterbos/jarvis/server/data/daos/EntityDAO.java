package com.awalterbos.jarvis.server.data.daos;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.EntityNotFoundException;

import java.util.Collection;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class EntityDAO<T> extends AbstractDAO<T> {

	public EntityDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Collection<T> list() {
		return list();
	}

	public T findById(long id) {
		T t = get(id);
		if (t == null) {
			throw new EntityNotFoundException("Entity not found for id '" + id + "'");
		}
		return t;
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
