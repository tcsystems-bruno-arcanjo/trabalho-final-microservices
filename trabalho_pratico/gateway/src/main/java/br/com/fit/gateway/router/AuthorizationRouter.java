package br.com.fit.gateway.router;

import br.com.fit.gateway.handler.AuthorizationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class AuthorizationRouter {

	private final AuthorizationHandler authorizationHandler;

	@Bean
	public RouterFunction<ServerResponse> customAuthorizationRouter() {
		return RouterFunctions
				.route(RequestPredicates.GET("/api/authorization"), authorizationHandler::authorize);
	}

}
