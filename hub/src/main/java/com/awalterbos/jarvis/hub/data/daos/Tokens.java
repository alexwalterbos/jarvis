package com.awalterbos.jarvis.hub.data.daos;

import com.awalterbos.jarvis.hub.data.entities.Token;
import com.google.inject.Inject;
import org.hibernate.SessionFactory;

public class Tokens extends EntityDao<Token> {

	@Inject
	public Tokens(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Token persistOrMerge(Token token) {
		if (currentSession().contains(token)) {
			currentSession().merge(token);
		}
		else {
			currentSession().persist(token);
		}
		return findByKeyOrThrow(token.getToken());
	}
}
