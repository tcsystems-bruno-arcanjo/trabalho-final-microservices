package br.com.fit.gateway.router;

import br.com.fit.gateway.handler.AuthenticationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class AuthenticationRouter {
	
	private final AuthenticationHandler authenticationHandler;
	
	@Bean
	public RouterFunction<ServerResponse> customAuthenticationRouter() {
		return RouterFunctions
				.route(RequestPredicates.POST("/api/login"), authenticationHandler::authenticate);
	}
}
