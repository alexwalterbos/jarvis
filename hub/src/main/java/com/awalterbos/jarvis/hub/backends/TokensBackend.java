package com.awalterbos.jarvis.hub.backends;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;

import java.util.UUID;

import com.awalterbos.jarvis.hub.data.daos.Tokens;
import com.awalterbos.jarvis.hub.data.entities.Token;
import com.awalterbos.jarvis.hub.data.entities.User;
import com.google.inject.Inject;

public class TokensBackend {

	private final Tokens tokens;
	private final String AUTHORIZATION_PREFIX = "Token ";

	@Inject
	public TokensBackend(Tokens tokens) {
		this.tokens = tokens;
	}

	public Token findTokenOrThrow(String authorization, boolean requiresAdmin) {
		if (authorization == null) {
			throw new NotAuthorizedException("No authentication provided.");
		}
		Token token;
		if (authorization.startsWith(AUTHORIZATION_PREFIX)) {
			String sToken = authorization.substring(6);
			token = tokens.findByKey(sToken);
		}
		else {
			throw new BadRequestException("Malformed authetication.");
		}
		if (token == null) {
			throw new NotAuthorizedException("Authentication failed.");
		}
		if (!token.getUser().isAdmin() && requiresAdmin) {
			throw new NotAuthorizedException("Admin rights required");
		}
		return token;
	}

	public Token createToken(User user) {

		// TODO delete old token!!

		UUID uuid = UUID.randomUUID();
		Token token = Token.fromUUID(uuid, user);
		tokens.persistOrMerge(token);
		return token;
	}

	public void delete(String tokenString) {
		tokens.delete(findTokenOrThrow(tokenString, false));
	}
}
