package br.com.finansys.entidades;

import java.util.Collections;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor @AllArgsConstructor @ToString @EqualsAndHashCode(callSuper = false)
public class User  extends PanacheEntity{

	public String username;
    public String password;
    
    @CollectionTable(name="Role")
	public Set<Role> roles;

	public static User findByUsername(String username) {

		String userUsername = "user";

		String userPassword = "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=";

		String adminUsername = "admin";

		String adminPassword = "dQNjUIMorJb8Ubj2+wVGYp6eAeYkdekqAcnYp+aRq5w=";
		
		if (username.equals(userUsername)) {
			return new User(userUsername, userPassword, Collections.singleton(Role.USER));
		} else if (username.equals(adminUsername)) {
			return new User(adminUsername, adminPassword, Collections.singleton(Role.ADMIN));
		} else {
			return null;
		}
	}

}