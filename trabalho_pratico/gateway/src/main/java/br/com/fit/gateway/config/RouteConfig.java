package br.com.fit.gateway.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RouteConfig {

	private final List<Endpoint> endpoints = List.of(
			new Endpoint("conta-corrente", "/api/conta-corrente", "http://localhost:8000", "http://conta-corrente:8000"),
			new Endpoint("investimento", "/api/investimento", "http://localhost:8100", "http://investimento:8100"),
			new Endpoint("cartao-credito", "/api/cartao-credito", "http://localhost:8200", "http://cartao-credito:8200")
	);

	public RouteLocator createRoutes(final RouteLocatorBuilder builder, final String profile) {
		RouteLocatorBuilder.Builder routes = builder.routes();

		for (Endpoint endpoint : endpoints) {
			routes = routes.route(endpoint.getId(), r -> r.path(endpoint.getPath().concat("/**"))
					.filters(f -> f.rewritePath(endpoint.getPath(), ""))
					.uri(endpoint.getURI(profile)));
		}

		return routes.build();
	}

	@Bean
	@Profile("!dev")
	public RouteLocator routesProduction(RouteLocatorBuilder builder) {
		return createRoutes(builder, Endpoint.PRODUCTION_PROFILE);
	}

	@Bean
	@Profile("dev")
	public RouteLocator routesDevelopment(RouteLocatorBuilder builder) {
		return createRoutes(builder, Endpoint.DEVELOPMENT_PROFILE);
	}

	@AllArgsConstructor
	@Getter
	static
	class Endpoint {

		private static final String DEVELOPMENT_PROFILE = "development";
		private static final String PRODUCTION_PROFILE = "production";

		private String id;
		private String path;
		private String developmentUri;
		private String productionUri;

		public String getURI(final String profile) {
			if (profile.equalsIgnoreCase(DEVELOPMENT_PROFILE)) {
				return this.developmentUri;
			}
			return this.productionUri;
		}
	}
}
