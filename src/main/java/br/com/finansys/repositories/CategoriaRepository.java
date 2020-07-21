package br.com.finansys.repositories;

import javax.enterprise.context.ApplicationScoped;

import br.com.finansys.entidades.Category;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class CategoriaRepository implements PanacheRepository<Category> {
    
    public Boolean existeByName(final String name) {
		return find("name = :name ", Parameters.with("name", name)).firstResult() != null;
    };
    
}