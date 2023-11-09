package com.adhunchanchoc.sourcelink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Config {

    static final Logger log = LoggerFactory.getLogger(Config.class); //access left package-private

    @Bean
    public CommandLineRunner initialise(LinkRepository linkRepository){
        return args -> {
            // mock data to populate database
//            log.info("Saving "+ linkRepository.save(new Link("https://www.fogcam.org/","fogcam2.jpg") ));
//            log.info("Saving "+ linkRepository.save(new Link("https://www.fogcam.org/","fogcam3.jpg") ));
//            log.info("Saving "+ linkRepository.save(new Link("https://www.fogcam.org/","fogcam4.jpg") ));
//            log.info("Saving "+ linkRepository.save(new Link("https://validator.w3.org/","Valid XHTML 1.0.svg") ));
        };
    }
}
