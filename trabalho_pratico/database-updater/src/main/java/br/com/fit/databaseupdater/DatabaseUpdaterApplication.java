package br.com.fit.databaseupdater;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DatabaseUpdaterApplication implements CommandLineRunner {

	@Value("${flyway.url}")
	private String url;

	@Value("${flyway.user}")
	private String user;

	@Value("${flyway.password}")
	private String password;

	public static void main(String[] args) {
		SpringApplication.run(DatabaseUpdaterApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Flyway.configure().dataSource(url, user, password).load().migrate();
	}
}
