package br.com.finansys.entidades;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

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

    @ManyToOne
    private Category category;

}