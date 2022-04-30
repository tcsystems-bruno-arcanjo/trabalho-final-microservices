package br.com.fit.gateway.handler;

import br.com.fit.gateway.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthorizationHandler {
	
	private final JWTUtil jwtUtil;
	
	public Mono<ServerResponse> authorize(ServerRequest request) {
		String header = request.headers().firstHeader("Authorization");
		
		if (!Strings.hasText(header)) {
			return ServerResponse.status(HttpStatus.FORBIDDEN).build();
		}

		try {
			if (!jwtUtil.validateToken(header.substring(7))) {
				return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
			}
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
			return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		return ServerResponse.ok().build();
	}
}
