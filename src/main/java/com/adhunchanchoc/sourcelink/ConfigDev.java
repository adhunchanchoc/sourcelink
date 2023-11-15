package com.adhunchanchoc.sourcelink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
@Profile("dev")
class ConfigDev {

    private static final Logger log = LoggerFactory.getLogger(ConfigDev.class); // logging repository initialisation

    @Bean
    public CommonsRequestLoggingFilter httpLoggingFilter() { // logging all HTTP requests (at the DEBUG level)

        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
//        filter.setBeforeMessagePrefix("HTTP log: [");
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setIncludeHeaders(false);
        return filter;
    }

    @Bean
    public CommandLineRunner initialise(LinkRepository linkRepository) {
        return args -> {
//             mock data to populate database
            log.info("Saving " + linkRepository.save(new Weblink("https://www.fogcam.org/", "fogcam2.jpg")));
            log.info("Saving " + linkRepository.save(new Weblink("https://www.fogcam.org/", "fogcam3.jpg")));
            log.info("Saving " + linkRepository.save(new Weblink("https://www.fogcam.org/", "fogcam4.jpg")));
            log.info("Saving " + linkRepository.save(new Weblink("https://validator.w3.org/", "Valid XHTML 1.0.svg")));
        };
    }
}
