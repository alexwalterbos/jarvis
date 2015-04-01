package com.awalterbos.jarvis.server.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.awalterbos.jarvis.server.data.daos.Groups;
import com.awalterbos.jarvis.server.data.entities.Group;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/groups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroupResource {

	@Inject
	private Groups groups;

	@GET
	@Path("/{id}")
	@UnitOfWork
	public Group get(@PathParam("id") long id) {
		return groups.findById(id);
	}
}
