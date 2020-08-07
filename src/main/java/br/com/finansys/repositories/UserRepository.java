package br.com.finansys.repositories;

import javax.enterprise.context.ApplicationScoped;

import br.com.finansys.entidades.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    public User findByName(final String name) {
        return find("LOWER(name) = :name ", Parameters.with("name", name.toLowerCase())).firstResult();
    };

}