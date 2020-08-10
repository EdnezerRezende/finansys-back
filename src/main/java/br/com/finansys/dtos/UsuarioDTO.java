package br.com.finansys.dtos;

import java.util.Set;

import br.com.finansys.entidades.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
	private Long Id;
	private String username;
    private String password;
    private Set<Role> roles;
    private String token;
}