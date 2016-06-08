package com.awalterbos.jarvis.hub.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Collection;

import com.awalterbos.jarvis.hub.backends.GroupsBackend;
import com.awalterbos.jarvis.hub.backends.TokensBackend;
import com.awalterbos.jarvis.hub.data.entities.Group;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/groups/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroupsResource {

	private GroupsBackend groupsBackend;
	private final TokensBackend tokensBackend;

	@Inject
	public GroupsResource(GroupsBackend groupsBackend, TokensBackend tokensBackend) {
		this.groupsBackend = groupsBackend;
		this.tokensBackend = tokensBackend;
	}

	@GET
	@UnitOfWork
	public Collection<Group> get() {
		return groupsBackend.listAll();
	}

	@GET
	@Path("{group_id}")
	@UnitOfWork
	public Group get(@PathParam("group_id") long id) {
		return groupsBackend.findById(id);
	}

	@PUT
	@UnitOfWork
	public Group update(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, Group group) {
		tokensBackend.findTokenOrThrow(authorization, true);
		return groupsBackend.updateGroup(group);
	}

	@POST
	@Path("create")
	@UnitOfWork
	public Group create(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, Group group) {
		tokensBackend.findTokenOrThrow(authorization, true);
		return groupsBackend.create(group);
	}

	@GET
	@Path("activate/{group_id}")
	@UnitOfWork
	public Response activate(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
			@PathParam("group_id") long id) {
		tokensBackend.findTokenOrThrow(authorization, false);
		try {
			groupsBackend.activate(id);
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DELETE
	@Path("deactivate/{group_id}")
	@UnitOfWork
	public Response deactivate(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
			@PathParam("group_id") long id) {
		tokensBackend.findTokenOrThrow(authorization, false);
		try {
			groupsBackend.deactivate(id);
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("activate_all")
	@UnitOfWork
	public Response activateAll(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization) {
		tokensBackend.findTokenOrThrow(authorization, false);
		try {
			groupsBackend.activateAll();
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DELETE
	@Path("deactivate_all")
	@UnitOfWork
	public Response deactivateAll(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization) {
		tokensBackend.findTokenOrThrow(authorization, false);
		try {
			groupsBackend.deactivateAll();
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
