package com.wsib.nihl;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DocumentServiceApplication {

	public static void main(String[] args) {
		//System.setProperty("user.timezone", "America/New_York");
		TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
		SpringApplication.run(DocumentServiceApplication.class, args);
	}

}
