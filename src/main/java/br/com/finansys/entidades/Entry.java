package br.com.finansys.entidades;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import br.com.finansys.dtos.EntryDTO;
import br.com.finansys.utils.DataUtil;
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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper=false, exclude = {"name", "description", "type", "amount", "date",
"paid", "category"})
public class Entry extends PanacheEntity {
    
    private String name;

    private String description;

    private String type;

    private String amount;

    private LocalDate date;

    private Boolean paid;

    private Long repeticao;

    private Long quantidadeRepeticoes;

    @ManyToOne
    private Category category;

    public Entry(EntryDTO entry){
        setAmount(entry.getAmount());
        setCategory(entry.getCategory());
        setDate(DataUtil.dataStringForLocalDate(entry.getDate()));
        setDescription(entry.getDescription());
        id = entry.getId();
        setName(entry.getName());
        setPaid(entry.getPaid());
        setType(entry.getType());
        setRepeticao(entry.getRepeticao());
        setQuantidadeRepeticoes(entry.getQuantidadeRepeticoes());
    }
}