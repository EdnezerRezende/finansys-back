package br.com.finansys.resources;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
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
import br.com.finansys.resources.exceptions.NegocioException;
import br.com.finansys.utils.DataUtil;

@Path("/entries")
@RolesAllowed("USER")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EntryResource {

    /**
     *
     */
    private static final long QUANTIDADE_REPETICAO_UPDATE = 1L;

    @Inject
    public EntryRepository entryRepository;

    @Inject
    public CategoriaRepository categoryRepository;

    @GET
    @RolesAllowed("USER")
    public List<EntryDTO> getAll(){
        List<EntryDTO> dtoRetorno = new ArrayList<>();
        entryRepository.listAll().forEach(entry -> {
            EntryDTO dto = new EntryDTO(entry);
            dtoRetorno.add(dto);
        });
        return dtoRetorno;
    }

    @GET
    @Path("/dateStart/{dateStart}/dateFinish/{dateFinish}")
    @RolesAllowed("USER")
    public List<EntryDTO> getAllByDateStartAndFinish(
        @PathParam(value="dateStart") final String dateStart, 
        @PathParam(value="dateFinish") final String dateFinish
    ){
        List<EntryDTO> dtoRetorno = new ArrayList<>();
        entryRepository.getAllByDateStartAndDateFinish(
            DataUtil.dataStringForLocalDate(dateStart, "dd-MM-yyyy"),
            DataUtil.dataStringForLocalDate(dateFinish, "dd-MM-yyyy")
        ).forEach(entry -> {
            EntryDTO dto = new EntryDTO(entry);
            dtoRetorno.add(dto);
        });
        return dtoRetorno;
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("USER")
    public EntryDTO getById(@PathParam(value="id") final Long id) {
        EntryDTO dto = new EntryDTO(entryRepository.findById(id));
        return dto;
    }

    @POST
    @Transactional
    @RolesAllowed("USER")
    public void create(final EntryNewDTO dto) throws NegocioException {
        final Boolean existeByName = entryRepository.existeByName(dto.getName());
        if (existeByName) {
            throw new NegocioException("Lançamento já está cadastrado");
        }

        createResourceAndPersist(dto);
    }

    private void createResourceAndPersist(final EntryNewDTO dto) throws NegocioException {
        Entry entry = new Entry();
        Long quantidadeRepet = dto.getQuantidadeRepeticoes();

        if (dto.getId() != null) {
            entry = entryRepository.findById(dto.getId());
            try {
                dto.setDate(DataUtil.converterStringLocalDate(dto.getDate()));
            } catch (DateTimeParseException e) {
                System.out.println("Sem conversão, utilizando formato original");
            } catch (Exception e){
                throw new NegocioException("Não foi possível realizar a conversão de data");
            }

            gerarEntry(dto, entry, QUANTIDADE_REPETICAO_UPDATE);
        } else{
            for(int i = 1; i <= quantidadeRepet; i++){
                entry = new Entry();
                gerarEntry(dto, entry, Integer.valueOf(i).longValue());
            }
        }
    }

    private void gerarEntry(final EntryNewDTO dto, Entry entry, Long i) {
        entry.setName(dto.getName());
        entry.setDescription(dto.getDescription());
        entry.setAmount(dto.getAmount());
        entry.setCategory(categoryRepository.findById(dto.getCategoryId()));
        if (i == 1){
            entry.setDate(DataUtil.dataStringForLocalDate(dto.getDate()));
        } else {
            entry.setDate(DataUtil.dataStringForLocalDate(dto.getDate()).plusMonths(i-1));
        }
        entry.setPaid(dto.getPaid());
        entry.setType(dto.getType());
        entry.setRepeticao(i);
        entry.setQuantidadeRepeticoes(dto.getQuantidadeRepeticoes());

        entryRepository.persist(entry);
    }

    @PUT
    @Transactional
    @RolesAllowed("USER")
    public void update(final EntryNewDTO dto) throws NegocioException {
        createResourceAndPersist(dto);
    }

    @PUT
    @Path("/paid")
    @Transactional
    @RolesAllowed("USER")
    public void paid(final EntryDTO dto) {
        Entry entry = entryRepository.findById(dto.getId());
        entry.setPaid(true);
        entryRepository.persist(entry);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("USER")
    public void delete(@PathParam(value = "id") final Long id) {
        final Entry entry = entryRepository.findById(id);
        entry.delete();
    }

    @GET
    @Path("/typesEnum")
    @RolesAllowed("USER")
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