package br.com.finansys.repositories;

import javax.enterprise.context.ApplicationScoped;

import br.com.finansys.entidades.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {
    public Usuario findByName(final String name) {
        return find("LOWER(username) = :name ", Parameters.with("name", name.toLowerCase())).firstResult();
    };

}