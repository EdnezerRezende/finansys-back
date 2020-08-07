package br.com.finansys.resources;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.com.finansys.config.PBKDF2Encoder;
import br.com.finansys.config.TokenUtils;
import br.com.finansys.entidades.AuthRequest;
import br.com.finansys.entidades.AuthResponse;
import br.com.finansys.entidades.User;

public class AuthenticationREST {
    
	@Inject
	PBKDF2Encoder passwordEncoder;

	@ConfigProperty(name = "com.ard333.quarkusjwt.jwt.duration") public Long duration;
	@ConfigProperty(name = "mp.jwt.verify.issuer") public String issuer;

	@PermitAll
	@POST @Path("/login") @Produces(MediaType.APPLICATION_JSON)
	public Response login(AuthRequest authRequest) {
		User u = User.findByUsername(authRequest.username);
		if (u != null && u.password.equals(passwordEncoder.encode(authRequest.password))) {
			try {
				return Response.ok(new AuthResponse(TokenUtils.generateToken(u.username, u.roles, duration, issuer))).build();
			} catch (Exception e) {
				return Response.status(Status.UNAUTHORIZED).build();
			}
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
}