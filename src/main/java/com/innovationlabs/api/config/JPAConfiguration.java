package com.innovationlabs.api.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by cv on 4/5/17.
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.innovationlabs.api")
@EntityScan(basePackages = "com.innovationlabs.api")
public class JPAConfiguration {
}
