package br.com.finansys.entidades;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

//for login endpoint
@NoArgsConstructor @AllArgsConstructor @ToString
public class AuthResponse {
	
	public String token;
}