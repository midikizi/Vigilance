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

		// Définir les variables dans le système
		System.setProperty("twilio.account.sid", Objects.requireNonNull(dotenv.get("TWILIO_ACCOUNT_SID")));
		System.setProperty("twilio.auth.token", Objects.requireNonNull(dotenv.get("TWILIO_AUTH_TOKEN")));
		System.setProperty("twilio.phone.number", Objects.requireNonNull(dotenv.get("TWILIO_PHONE_NUMBER")));

		SpringApplication.run(VigiLanceApplication.class, args);
	}

}
