package com.adhunchanchoc.sourcelink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SourcelinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SourcelinkApplication.class, args);
//        custom logging
//        ConfigDev.log.info("current timestamp: " + System.currentTimeMillis());
//        ConfigDev.log.info("current datetime: " + LocalDateTime.now());

    }

}
