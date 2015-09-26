package com.awalterbos.jarvis.server.data.daos;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.persistence.EntityNotFoundException;

import java.util.Collection;

import com.awalterbos.jarvis.server.data.entities.EntityWithID;
import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLInput;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;

public class EntityDAO<T> extends AbstractDAO<T> {

	public EntityDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Collection<T> listAll() {
		Criteria criteria = criteria().addOrder(Order.asc("id"));
		return list(criteria);
	}

	public T findById(long id) {
		T t = get(id);
		if (t == null) {
			throw new EntityNotFoundException("Entity not found for id '" + id + "'");
		}
		return t;
	}

	public T merge(T t) {
		currentSession().merge(t);
		return findById(((EntityWithID) t).getId());
	}

	public T persistOrMerge(T t) {
		if (currentSession().contains(t)) {
			currentSession().merge(t);
		}
		else {
			currentSession().persist(t);
		}
		return findById(((EntityWithID) t).getId());
	}

	public void delete(T t) {
		currentSession().delete(checkNotNull(t));
	}

	public void delete(long id) {
		currentSession().delete(checkNotNull(findById(id)));
	}
}
