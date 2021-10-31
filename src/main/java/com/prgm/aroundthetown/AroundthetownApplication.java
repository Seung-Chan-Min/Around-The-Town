package com.prgm.aroundthetown;

import java.util.Optional;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AroundthetownApplication {

	public static void main(String[] args) {
		SpringApplication.run(AroundthetownApplication.class, args);
	}

	// Note : Spring Security를 사용하지 않아서 BaseEntity의 필드들의 값들에 임시로 random값을 넣어줌
	@Bean
	public AuditorAware<String> auditorProvider(){
		return () -> Optional.of(UUID.randomUUID().toString());
	}

}
