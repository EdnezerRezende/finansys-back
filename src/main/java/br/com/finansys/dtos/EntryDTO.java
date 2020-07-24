package br.com.finansys.dtos;

import br.com.finansys.entidades.Category;
import br.com.finansys.entidades.Entry;
import br.com.finansys.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntryDTO {

    private Long id;

    private String name;

    private String description;

    private String type;

    private String amount;

    private String date;

    private Boolean paid;

    private Long categoryId;

    private Category category;

    private Long repeticao;

    private Long quantidadeRepeticoes;

    public EntryDTO(Entry entry){
        setAmount(entry.getAmount());
        setCategory(entry.getCategory());
        setCategoryId(entry.getCategory().id);
        setDate(DataUtil.dataLocalDateForString(entry.getDate()));
        setDescription(entry.getDescription());
        setId(entry.id);
        setName(entry.getName());
        setPaid(entry.getPaid());
        setType(entry.getType());
        setRepeticao(entry.getRepeticao());
        setQuantidadeRepeticoes(entry.getQuantidadeRepeticoes());
    }
}