package org.secspike.todo.auth;

import static java.util.Arrays.asList;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import org.omnifaces.security.jaspic.user.UsernamePasswordIdentityStore;

@RequestScoped
public class TestUsernamePasswordIdentityStore implements UsernamePasswordIdentityStore {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean authenticate(String username, String password) {
		return true;
	}
	
	@Override
	public String getUserName() {
		return "testuser";
	}
	
	@Override
	public List<String> getApplicationRoles() {
		return asList("USER");
	}
	
}
