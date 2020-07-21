package br.com.finansys.resources;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.finansys.dtos.CategoriaNewDTO;
import br.com.finansys.entidades.Category;
import br.com.finansys.repositories.CategoriaRepository;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    public CategoriaRepository categoryRepository;

    @GET
    public List<Category> getAll(){
        return categoryRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Category getById(@PathParam(value="id") Long id){
        return categoryRepository.findById(id);
    }
    

    @POST
    @Transactional
    public void create(CategoriaNewDTO dto){
        Boolean existeByName = categoryRepository.existeByName(dto.getName());
        if (existeByName){
            throw new RuntimeException("Categoria já está cadastrada");
        }

        createResourceAndPersist(dto);
    }

    private void createResourceAndPersist(CategoriaNewDTO dto) {
        Category categoria = new Category();
        if (dto.getId() != null){
            categoria = categoryRepository.findById(dto.getId());
        }

        categoria.setName(dto.getName());
        categoria.setDescription(dto.getDescription());
        categoryRepository.persist(categoria);
    }

    @PUT
    @Transactional
    public void update(CategoriaNewDTO dto){
        createResourceAndPersist(dto);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam(value="id") Long id){
        Category categoria = categoryRepository.findById(id);
        categoria.delete();
    }
}