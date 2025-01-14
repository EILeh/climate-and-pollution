package fi.tuni.compse110.SyntaxSquad.climate_and_pollution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ClimateAndPollutionApplication {

	public static void main(String[] args) {

		
		SpringApplication.run(ClimateAndPollutionApplication.class, args);
	}
}
