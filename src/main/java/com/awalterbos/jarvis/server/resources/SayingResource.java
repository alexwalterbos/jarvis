package com.awalterbos.jarvis.server.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.awalterbos.jarvis.server.data.daos.Sayings;
import com.awalterbos.jarvis.server.data.entities.Saying;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/sayings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SayingResource {

	private Sayings sayings;

	@Inject
	public SayingResource(Sayings sayings){

		this.sayings = sayings;
	}

	@GET
	@Path("/health")
	public Response health() {
		return Response.ok().build();
	}

	@GET
	@Path("/{id}")
	@UnitOfWork
	public Saying get(@PathParam("id") Long id) {
		return sayings.findById(id);
	}

	@GET
	@Path("/say")
	@Timed
	@UnitOfWork
	public String say(@QueryParam("saying_id") Long id, @QueryParam("text") Optional<String> text) {
		Saying saying = sayings.findById(id);
		return saying.format(text);
	}

	@POST
	@Path("/create")
	@UnitOfWork
	public Saying addSaying(Saying saying) {
		return sayings.createOrUpdate(saying);
	}

	@DELETE
	@Path("/delete/{id}")
	@UnitOfWork
	public Response delete(@PathParam("id") Long id) {
		try {
			sayings.delete(id);
			return Response.ok().build();
		}
		catch (NullPointerException e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
}
