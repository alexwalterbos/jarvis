package com.awalterbos.jarvis.server.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Collection;

import com.awalterbos.jarvis.server.GroupsBackend;
import com.awalterbos.jarvis.server.data.entities.Group;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/api/groups/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroupsResource {

	private GroupsBackend groupsBackend;
	@Inject

	public GroupsResource(GroupsBackend groupsBackend) {
		this.groupsBackend = groupsBackend;
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
	public Group update(Group group) {
		return groupsBackend.updateGroup(group);
	}

	@POST
	@Path("create")
	@UnitOfWork
	public Group create(Group group) {
		return groupsBackend.create(group);
	}

	@PUT
	@Path("activate/{group_id}")
	@UnitOfWork
	public Response activate(@PathParam("group_id") long id) {
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
	public Response deactivate(@PathParam("group_id") long id) {
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
	public Response activateAll() {
		try {
			groupsBackend.activateAll();
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("deactivate_all")
	@UnitOfWork
	public Response deactivateAll() {
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
