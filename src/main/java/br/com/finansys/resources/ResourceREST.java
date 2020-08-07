package br.com.finansys.resources;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/resource")
public class ResourceREST {

	@RolesAllowed("USER")
	@GET @Path("/user") 
	public Response user() {
		return Response.ok(new Message("Content for user")).build();
	}
	
	@RolesAllowed("ADMIN")
	@GET @Path("/admin") 
	public Response admin() {
		return Response.ok(new Message("Content for admin")).build();
	}
	
	@RolesAllowed({"USER", "ADMIN"})
	@GET @Path("/user-or-admin") 
	public Response userOrAdmin() {
		return Response.ok(new Message("Content for user or admin")).build();
	}
}