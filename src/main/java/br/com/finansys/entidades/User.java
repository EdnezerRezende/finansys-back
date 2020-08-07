package br.com.finansys.entidades;

import java.util.Collections;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor @ToString @EqualsAndHashCode(callSuper = false)
public class User  extends PanacheEntity{

	private String username;
    private String password;
    
    @CollectionTable(name="Role")
	private Set<Role> roles;

}