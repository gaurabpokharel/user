package com.demoproject.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private Long blogId;
	private String username;
	private String password;
	private boolean active=true;
	private String roles;

}
