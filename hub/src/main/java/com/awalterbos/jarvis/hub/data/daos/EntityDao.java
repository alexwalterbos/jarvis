package com.awalterbos.jarvis.hub.data.daos;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.EntityNotFoundException;

import java.io.Serializable;
import java.util.Collection;

import com.awalterbos.jarvis.hub.data.entities.EntityWithId;
import com.google.common.base.Preconditions;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;

public class EntityDao<T> extends AbstractDAO<T> {

	public EntityDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Collection<T> listAll() {
		Criteria criteria = criteria().addOrder(Order.asc("id"));
		return list(criteria);
	}

	public T findById(long id) {
		return get(id);
	}

	public T findByKey(Serializable key) {
		return get(key);
	}

	public T findByIdOrThrow(long id) {
		T t = get(id);
		if (t == null) {
			throw new EntityNotFoundException(String.format("Entity with id '%d' not found", id));
		}
		return t;
	}

	public T findByKeyOrThrow(Serializable key) {
		T t = get(key);
		if (t == null) {
			throw new EntityNotFoundException(String.format("Entity with key '%s' not found.", key));
		}
		return t;
	}

	public T merge(T t) {
		currentSession().merge(t);
		return findByIdOrThrow(((EntityWithId) t).getId());
	}

	public T persistOrMerge(T t) {
		if (currentSession().contains(t)) {
			currentSession().merge(t);
		}
		else {
			currentSession().persist(t);
		}
		return findByIdOrThrow(((EntityWithId) t).getId());
	}

	public T persist(T t) {
		return super.persist(t);
	}

	public void delete(T t) {
		currentSession().delete(checkNotNull(t));
	}

	public void delete(long id) {
		currentSession().delete(checkNotNull(findByIdOrThrow(id)));
	}

	public void delete(Serializable key) {
		currentSession().delete(Preconditions.checkNotNull(findByKeyOrThrow(key)));
	}
}
