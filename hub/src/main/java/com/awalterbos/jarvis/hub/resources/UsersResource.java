package com.awalterbos.jarvis.hub.resources;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.awalterbos.jarvis.hub.backends.TokensBackend;
import com.awalterbos.jarvis.hub.data.daos.Users;
import com.awalterbos.jarvis.hub.data.entities.Token;
import com.awalterbos.jarvis.hub.data.entities.User;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import models.UserModel;
import org.assertj.core.util.Strings;

@Path("/users/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {

	private Users users;
	private final TokensBackend tokensBackend;

	@Inject
	public UsersResource(Users users, TokensBackend tokensBackend) {
		this.users = users;
		this.tokensBackend = tokensBackend;
	}

	@GET
	@Path("me")
	@UnitOfWork
	public UserModel me(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization) {
		Token token = tokensBackend.findTokenOrThrow(authorization, false);
		return UserModel.fromUser(token.getUser());
	}

	@POST
	@UnitOfWork
	public UserModel createUser(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, UserModel userModel) {
		tokensBackend.findTokenOrThrow(authorization, false);
		User user = User.createFromModel(userModel);
		if (users.findByName(user.getUsername()) != null) {
			throw new BadRequestException("Username already taken");
		}
		return UserModel.fromUser(users.persistOrMerge(user));
	}

	@POST
	@Path("login")
	@UnitOfWork
	public Token authenticate(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, UserModel userModel) {
		if (!Strings.isNullOrEmpty(authorization)) {
			Token token = tokensBackend.findTokenOrThrow(authorization, false);
			return tokensBackend.createToken(token.getUser()); // Token renewal
		}
		User user = users.findByName(userModel.getUsername());
		if (user == null) {
			throw new NotFoundException("User not found");
		}
		if (users.checkUserPass(userModel)) {
			return tokensBackend.createToken(user);
		}
		else {
			throw new ForbiddenException();
		}
	}

	@DELETE
	@Path("logout")
	@UnitOfWork
	public void logout(@HeaderParam("Authorization") String authorization) {
		tokensBackend.delete(authorization);
	}
}
