package org.secspike.todo.auth;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import org.omnifaces.security.jaspic.user.TokenIdentityStore;

@RequestScoped
public class TestTokenIdentityStore implements TokenIdentityStore {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean authenticate(String token) {
		return true;
	}
	
	@Override
	public List<String> getApplicationRoles() {
		return Arrays.asList("user");
	}

	@Override
	public String getUserName() {
		return "testuser";
	}
	
}
