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

import com.awalterbos.antenna.Antenna;
import com.awalterbos.jarvis.server.data.daos.Groups;
import com.awalterbos.jarvis.server.data.entities.Group;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import org.assertj.core.util.Strings;

@Path("/groups/")
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
	@UnitOfWork
	public Collection<Group> get() {
		return groups.listAll();
	}

	@GET
	@Path("{group_id}")
	@UnitOfWork
	public Group get(@PathParam("group_id") long id) {
		return groups.findById(id);
	}

	@PUT
	@UnitOfWork
	public Group update(Group group) {
		Group fromDB = groups.findById(group.getId());

		if (!Strings.isNullOrEmpty(group.getName())) {
			fromDB.setName(group.getName());
		}

		if (group.getDescription() != null) {
			if(group.getDescription().isEmpty()){
				fromDB.setDescription(null);
			}
			else {
				fromDB.setDescription(group.getDescription());
			}
		}

		if (group.getSignalOff() != null) {
			fromDB.setSignalOff(group.getSignalOff());
		}

		if (group.getSignalOn() != null) {
			fromDB.setSignalOn(group.getSignalOn());
		}

		if (group.getTriggerOnSunset() != null) {
			fromDB.setTriggerOnSunset(group.getTriggerOnSunset());
		}

		return groups.merge(group);
	}

	@POST
	@Path("create")
	@UnitOfWork
	public Group create(Group group) {
		return groups.persistOrMerge(group);
	}

	@PUT
	@Path("activate/{group_id}")
	@UnitOfWork
	public Response activate(@PathParam("group_id") long id) {
		Group group = groups.findById(id);
		try {
			group.activate(antenna);
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
		Group group = groups.findById(id);
		try {
			group.deactivate(antenna);
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
		boolean success = true;
		Collection<Group> groups = this.groups.listAll();
		for (Group group : groups) {
			try {
				group.activate(antenna);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
				success = false;
			}
		}

		if (success) {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("deactivate_all")
	@UnitOfWork
	public Response deactivateAll() {
		boolean success = true;
		Collection<Group> groups = this.groups.listAll();
		for (Group group : groups) {
			try {
				group.deactivate(antenna);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
				success = false;
			}
		}

		if (success) {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
