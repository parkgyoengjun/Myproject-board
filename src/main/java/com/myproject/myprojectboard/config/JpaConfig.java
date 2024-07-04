package com.myproject.myprojectboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing // 엔티티 객체가 생성이 되거나 변경이 되었을 때 @EnableJpaAuditing 을 활용하여 자동으로 값을 등록
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("jun"); // TODO  : 스프링 시큐리티오 이능 기능을 부이게 될 때, 수정하자
    }
}
