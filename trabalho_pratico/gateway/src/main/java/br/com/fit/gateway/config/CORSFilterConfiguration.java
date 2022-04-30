package br.com.fit.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class CORSFilterConfiguration {
	
	@Value("${cors.allowed-origin}")
	private String corsAllowedOrigin;
	
	@Bean
	public CorsWebFilter corsWebFilter() {
	    CorsConfiguration corsConfig = new CorsConfiguration();
	    corsConfig.setAllowedOrigins(getAllowedOrigin());
	    corsConfig.setMaxAge(8000L);
	    corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
	    corsConfig.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
	    corsConfig.addExposedHeader("Content-Disposition");

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", corsConfig);

	    return new CorsWebFilter(source);
	}
	
	public List<String> getAllowedOrigin() {
		if (corsAllowedOrigin.contains(",")) {
			return Arrays.asList(corsAllowedOrigin.split(","));
		}
		return Collections.singletonList(corsAllowedOrigin);
	}
}
