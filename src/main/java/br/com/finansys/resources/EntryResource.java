package br.com.finansys.resources;

import java.util.ArrayList;
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

import br.com.finansys.dtos.EntryDTO;
import br.com.finansys.dtos.EntryNewDTO;
import br.com.finansys.dtos.TypeEnumDTO;
import br.com.finansys.entidades.Entry;
import br.com.finansys.enus.TypeEnum;
import br.com.finansys.repositories.CategoriaRepository;
import br.com.finansys.repositories.EntryRepository;
import br.com.finansys.utils.DataUtil;

@Path("/entries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EntryResource {

    @Inject
    public EntryRepository entryRepository;

    @Inject
    public CategoriaRepository categoryRepository;

    @GET
    public List<EntryDTO> getAll(){
        List<EntryDTO> dtoRetorno = new ArrayList<>();
        entryRepository.listAll().forEach(entry -> {
            EntryDTO dto = new EntryDTO(entry);
            dtoRetorno.add(dto);
        });
        return dtoRetorno;
    }

    @GET
    @Path("/{id}")
    public EntryDTO getById(@PathParam(value="id") final Long id) {
        EntryDTO dto = new EntryDTO(entryRepository.findById(id));
        return dto;
    }

    @POST
    @Transactional
    public void create(final EntryNewDTO dto) {
        final Boolean existeByName = entryRepository.existeByName(dto.getName());
        if (existeByName) {
            throw new RuntimeException("Lançamento já está cadastrado");
        }

        createResourceAndPersist(dto);
    }

    private void createResourceAndPersist(final EntryNewDTO dto) {
        Entry entry = new Entry();
        if (dto.getId() != null) {
            entry = entryRepository.findById(dto.getId());
        }

        entry.setName(dto.getName());
        entry.setDescription(dto.getDescription());
        entry.setAmount(dto.getAmount());
        entry.setCategory(categoryRepository.findById(dto.getCategoryId()));
        entry.setDate(DataUtil.dataStringForLocalDate(dto.getDate()));
        entry.setPaid(dto.getPaid());
        entry.setType(dto.getType());

        entryRepository.persist(entry);
    }

    @PUT
    @Transactional
    public void update(final EntryNewDTO dto) {
        createResourceAndPersist(dto);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam(value = "id") final Long id) {
        final Entry entry = entryRepository.findById(id);
        entry.delete();
    }

    @GET
    @Path("/typesEnum")
    public List<TypeEnumDTO> getTypes() {
        List<TypeEnumDTO> types = new ArrayList<>();

        for(TypeEnum x : TypeEnum.values()){
            TypeEnumDTO dto = new TypeEnumDTO();
            dto.setCodigo(x.getCod());
            dto.setDescricao(x.getDescricao());
            dto.setKey(x.toString());
            types.add(dto);
        }
        return types;
    }
}