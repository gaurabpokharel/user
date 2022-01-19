package com.demoproject.user.configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
	private static final String CLAIM_KEY_USERNAME = "sub";
	private static final String CLAIM_KEY_USERROLE = "role";
	private static final String CLAIM_KEY_CREATED = "created";
	// @Value("${jwt.secret}")
	private String secret = "Gaurab";
	// @Value("${jwt.expiration}")
	private Long expiration = 200L;

	private String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return claims;
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000 * 60 * 60 * 10000);
	}

	public String getUserNameFromToken(String token) {
		String username;
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		String username = getUserNameFromToken(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		Date expiredDate = getExpiredDateFromToken(token);
		return expiredDate.before(new Date());
	}

	private Date getExpiredDateFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		return claims.getExpiration();
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		claims.put(CLAIM_KEY_USERROLE, userDetails.getAuthorities());
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}

	public boolean canRefresh(String token) {
		return !isTokenExpired(token);
	}

	public String refreshToken(String token) {
		Claims claims = getClaimsFromToken(token);
		claims.put(CLAIM_KEY_CREATED, new Date());
		return generateToken(claims);
	}
}
