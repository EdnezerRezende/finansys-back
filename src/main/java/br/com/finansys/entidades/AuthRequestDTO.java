package br.com.finansys.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor @ToString
public class  AuthRequestDTO {
        
    public String username;
    public String password;
}
