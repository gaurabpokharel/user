package com.demoproject.user.entity;

import com.demoproject.user.bean.Authority;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	@JsonProperty("blogId")
	private Long blogId;
	@Column(unique = true,nullable = false)
	@JsonProperty("username")
	private String username;
	@Column(nullable = false)
	@JsonProperty("password")
	private String password;
	//private boolean active=true;
	@Column(nullable = false)
	@JsonProperty("role")
	private String role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Authority> auth = new HashSet<Authority>();
		auth.add(new Authority(role));
		return auth;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
