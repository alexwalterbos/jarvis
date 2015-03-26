package com.awalterbos.jarvis.server.resources;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.awalterbos.jarvis.server.SayingDAO;
import com.awalterbos.jarvis.server.entities.Saying;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/sayings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SayingResource {

	private SayingDAO sayingDAO;

	@Inject
	public SayingResource(SayingDAO sayingDAO){

		this.sayingDAO = sayingDAO;
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
		return sayingDAO.findById(id);
	}

	@GET
	@Path("/say")
	@Timed
	@UnitOfWork
	public String say(@QueryParam("saying_id") Long id, @QueryParam("text") Optional<String> text) {
		Saying saying = sayingDAO.findById(id);
		return saying.format(text);
	}

	@POST
	@Path("/create")
	@UnitOfWork
	public Saying addSaying(Saying saying) {
		return sayingDAO.create(saying);
	}
}
