package com.example.VigiLance;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class VigiLanceApplication {

	public static void main(String[] args) {
		// Charger le fichier .env
		Dotenv dotenv = Dotenv.load();

		System.setProperty("jwt.secret", Objects.requireNonNull(dotenv.get("JWT_SECRET")));
		System.setProperty("jwt.expiration", Objects.requireNonNull(dotenv.get("JWT_EXPIRATION")));

		SpringApplication.run(VigiLanceApplication.class, args);
	}

}
