package br.com.finansys.resources;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.com.finansys.config.PBKDF2Encoder;
import br.com.finansys.config.TokenUtils;
import br.com.finansys.dtos.UsuarioDTO;
import br.com.finansys.entidades.AuthRequestDTO;
import br.com.finansys.entidades.AuthResponse;
import br.com.finansys.entidades.Role;
import br.com.finansys.entidades.Usuario;
import br.com.finansys.repositories.UsuarioRepository;

@Path("/login")
public class AuthenticationREST {
    
	@Inject
	PBKDF2Encoder passwordEncoder;

	@Inject
	UsuarioRepository userRepository;

	@ConfigProperty(name = "com.ard333.quarkusjwt.jwt.duration") public Long duration;
	@ConfigProperty(name = "mp.jwt.verify.issuer") public String issuer;

	@PermitAll
	@Transactional
	@Consumes(MediaType.APPLICATION_JSON)
	@POST @Path("/login") @Produces(MediaType.APPLICATION_JSON)
	public Response login(AuthRequestDTO authRequest) {
		Usuario u = userRepository.findByName(authRequest.username);
		if (u != null && u.getPassword().equals(passwordEncoder.encode(authRequest.password))) {
			try {
				UsuarioDTO user = new UsuarioDTO();
				user.setId(u.getId());
				user.setUsername(u.getUsername());
				user.setRoles(u.getRoles());
				user.setToken(TokenUtils.generateToken(u.getUsername(), u.getRoles(), duration, issuer));
				return Response.ok(user).build();
			} catch (Exception e) {
				return Response.status(Status.UNAUTHORIZED).build();
			}
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	@PermitAll
	@POST @Path("/createUser")
	@Transactional
	public void createdUser(){
		List<Usuario> user = new ArrayList<>();
		Usuario usuario1 = new Usuario();
		usuario1.setUsername("ednezer");
		usuario1.setPassword(passwordEncoder.encode("ludileca12"));
		Set<Role> roles = new HashSet<>();
		roles.add(Role.USER);
		roles.add(Role.ADMIN);
		usuario1.setRoles(roles);

		Usuario usuario2 = new Usuario();
		usuario2.setUsername("luciane");
		usuario2.setPassword(passwordEncoder.encode("ludileca12"));
		usuario2.setRoles(roles);

		user.add(usuario1);
		user.add(usuario2);
		userRepository.persist(user);
	}
}