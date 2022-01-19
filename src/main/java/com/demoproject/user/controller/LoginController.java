package com.demoproject.user.controller;

import com.demoproject.user.bean.JwtRequest;
import com.demoproject.user.bean.JwtResponse;
import com.demoproject.user.configuration.JwtUtils;
import com.demoproject.user.exceptionalHandling.BadCredentialException1;
import com.demoproject.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/")
@Slf4j

public class LoginController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userDetailService;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/login")
	@ApiOperation(value = "this api is used for login")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully login"),
			@ApiResponse(code = 500, message = "Unable to login"),
			@ApiResponse(code=401,message = "Unauthorized")
	})
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		try {
			boolean authenticated = authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
			if (authenticated) {
				UserDetails user = (UserDetails) userDetailService.loadUserByUsername(jwtRequest.getUsername());
				if (user.isEnabled()) {
					System.out.println(user.isEnabled());
					String token = jwtUtils.generateToken(user);
					return ResponseEntity.ok(new JwtResponse(token));
				} else {
					return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
				}

			} else {
				return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
			}
		} catch (UsernameNotFoundException e) {
			throw new Exception("User not authorized");
		}

	}

	private boolean authenticate(String username, String password) {

		boolean authenticated = false;
		try {
			authenticated = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)).isAuthenticated();
		} catch (DisabledException e) {
			throw new BadCredentialException1("Disabled Account ");
		} catch (BadCredentialsException e) {
			throw new BadCredentialException1("Wrong Credential");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return authenticated;
	}
}
