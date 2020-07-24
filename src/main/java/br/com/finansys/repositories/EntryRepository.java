package br.com.finansys.repositories;

import java.time.LocalDate;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import br.com.finansys.entidades.Entry;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class EntryRepository implements PanacheRepository<Entry> {
    
    public Boolean existeByName(final String name) {
		  return find("name = :name ", Parameters.with("name", name)).firstResult() != null;
    };

    public List<Entry> getAllByDateStartAndDateFinish(final LocalDate dateStart, final LocalDate dateFinish){
      return find("date between :dateStart and :dateFinish", Parameters.with("dateStart", dateStart).and("dateFinish", dateFinish)).list();
    }
    
}