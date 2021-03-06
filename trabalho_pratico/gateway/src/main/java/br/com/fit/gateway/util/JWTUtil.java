package br.com.fit.gateway.util;

import br.com.fit.gateway.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtil {

	@Value("${jwt.jjwt.secret}")
	private String secret;

	@Value("${jwt.jjwt.expiration}")
	private String expirationTime;

	private Key key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
	}

	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	public String getUsernameFromToken(String token) {
		return getAllClaimsFromToken(token).getSubject();
	}

	public Date getExpirationDateFromToken(String token) {
		return getAllClaimsFromToken(token).getExpiration();
	}

	private Boolean isTokenExpired(String token) {
		return getExpirationDateFromToken(token).before(new Date());
	}

	public String generateToken(Usuario usuario) {
		return doGenerateToken(Collections.singletonMap("role", usuario.getAuthorities()), usuario.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String username) {
		Long expirationTimeLong = Long.parseLong(expirationTime);
		final Date createdDate = new Date();
		final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

		return Jwts
				.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(createdDate)
				.setExpiration(expirationDate)
				.signWith(key)
				.compact();
	}

	public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}
}
