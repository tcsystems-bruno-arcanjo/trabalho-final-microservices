package br.com.fit.gateway.handler;

import br.com.fit.gateway.dto.AuthenticationRequest;
import br.com.fit.gateway.dto.AuthenticationResponse;
import br.com.fit.gateway.service.UsuarioService;
import br.com.fit.gateway.util.CustomPasswordEncoder;
import br.com.fit.gateway.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationHandler {
	
	private final JWTUtil jwtUtil;
    private final CustomPasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;

    public Mono<ServerResponse> authenticate(ServerRequest request) {
    	return request.bodyToMono(AuthenticationRequest.class)
    			.flatMap(ar -> usuarioService
						.findByUsername(ar.getUsername())
						.flatMap(userDetails -> Mono.justOrEmpty(userDetails.withRawPassword(ar.getPassword()))))
    			.filter(userDetails -> passwordEncoder.matches(userDetails.getRawPassword(), userDetails.getPassword()))
    			.flatMap(userDetails -> ServerResponse.ok().bodyValue(new AuthenticationResponse(jwtUtil.generateToken(userDetails))))
    			.switchIfEmpty(ServerResponse.status(HttpStatus.UNAUTHORIZED).build());
    }
}
