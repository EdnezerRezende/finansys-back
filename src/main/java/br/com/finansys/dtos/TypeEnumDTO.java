package br.com.finansys.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeEnumDTO {

    private String key;

    private String descricao;

    private Integer codigo;
}