package com.innovationlabs.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by cv on 4/5/17.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/v1/shops"))
                .build();
    }

    @Bean
    public ApiInfo apiInfo() {
        ApiInfo info = new ApiInfo("Asset Management API Backend",
                "REST API Backend for Asset Management Portal",
                "v1",
                null,
                "s.chandravadan@gmail.com", "MIT License", null);
        return info;
    }
}

