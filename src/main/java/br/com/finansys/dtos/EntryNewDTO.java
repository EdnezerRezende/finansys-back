package br.com.finansys.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntryNewDTO {

    private Long id;

    private String name;

    private String description;

    private String type;

    private String amount;

    private String date;

    private Boolean paid;

    private Long categoryId;
}