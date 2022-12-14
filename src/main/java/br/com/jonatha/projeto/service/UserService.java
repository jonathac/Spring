package br.com.jonatha.projeto.service;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.jonatha.projeto.security.UserSS;

public class UserService {

	public static UserSS authenticated() {
		try {
		return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
	
}
