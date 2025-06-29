package com.atosalves.park_api.config;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

@EnableJpaAuditing
@Configuration
public class SpringAuditingConfig implements AuditorAware<String> {

        @Override
        public Optional<String> getCurrentAuditor() {
                var authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null && authentication.isAuthenticated()) {
                        return Optional.of(authentication.getName());
                }
                return Optional.empty();
        }

}
