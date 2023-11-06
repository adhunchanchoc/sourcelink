package com.adhunchanchoc.sourcelink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class SourcelinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SourcelinkApplication.class, args);

		Config.log.info("current timestamp: "+System.currentTimeMillis());
		Config.log.info("current datetime: "+ LocalDateTime.now());
	}

}
