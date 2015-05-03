package com.awalterbos.jarvis.server.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Collection;

import com.awalterbos.antenna.Antenna;
import com.awalterbos.jarvis.server.data.daos.Groups;
import com.awalterbos.jarvis.server.data.entities.Group;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;

@Path("/groups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroupsResource {

	private Groups groups;
	private Antenna antenna;

	@Inject
	public GroupsResource(Groups groups, Antenna antenna) {
		this.groups = groups;
		this.antenna = antenna;
	}

	@GET
	@Path("/")
	@UnitOfWork
	public Collection<Group> get() {
		return groups.listAll();
	}

	@GET
	@Path("/{group_id}")
	@UnitOfWork
	public Group get(@PathParam("group_id") long id) {
		return groups.findById(id);
	}

	// TODO PUT method

	@POST
	@Path("/create")
	@UnitOfWork
	public Group create(Group group) {
		return groups.persistOrMerge(group);
	}

	@PUT
	@Path("/activate/{group_id}")
	@UnitOfWork
	public Response activate(@PathParam("group_id") long id) {
		Group group = groups.findById(id);
		group.activate(antenna);

		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@DELETE
	@Path("/deactivate/{group_id}")
	@UnitOfWork
	public Response deactivate(@PathParam("group_id") long id) {
		Group group = groups.findById(id);
		group.deactivate(antenna);

		return Response.status(Response.Status.NO_CONTENT).build();
	}
}
