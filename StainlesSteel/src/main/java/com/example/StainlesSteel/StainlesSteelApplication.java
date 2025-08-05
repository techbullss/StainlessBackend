package com.example.StainlesSteel;

import com.example.StainlesSteel.Configurations.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class StainlesSteelApplication {

	public static void main(String[] args) {
		SpringApplication.run(StainlesSteelApplication.class, args);
	}

}
