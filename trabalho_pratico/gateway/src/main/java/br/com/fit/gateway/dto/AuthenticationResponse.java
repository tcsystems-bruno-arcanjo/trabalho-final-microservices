package br.com.fit.gateway.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AuthenticationResponse {
	
	private final String token;
	private final String type = "Bearer ";
	
	public AuthenticationResponse(String token) {
		this.token = token;
	}
}
