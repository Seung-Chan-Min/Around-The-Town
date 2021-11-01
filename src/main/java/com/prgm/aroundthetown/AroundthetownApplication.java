package com.prgm.aroundthetown;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class AroundthetownApplication {

    public static void main(final String[] args) {
        SpringApplication.run(AroundthetownApplication.class, args);
    }

}
