package com.demoproject.user.bean;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {
	private static final long serialVersionUID = 1L;

	private String auth;

	public Authority(String auth) {
		this.auth = auth;
	}

	public String getAuthority() {
		return this.auth;
	}
}
