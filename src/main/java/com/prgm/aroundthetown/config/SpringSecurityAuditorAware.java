package com.prgm.aroundthetown.config;

import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    // Note : Spring Security를 사용하지 않아서 BaseEntity의 필드들의 값들에 임시로 random값을 넣어줌
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(UUID.randomUUID().toString());
    }

}
