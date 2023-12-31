package com.example.clmp.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class SwaggerConfig {

    @Bean
    //DESCRIPTION: Configuring and initializing swagger
    public Docket api() {
        return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo())
            .securityContexts(Arrays.asList(securityContext()))
            .securitySchemes(Arrays.asList(apiKey()))
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any()).build();
    }

    //DESCRIPTION: Providing information about the api - title, description, license, etc.
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Contact List Management Application")
            .description("API Documentation for Contact List Management Application REST API")
            .termsOfServiceUrl("https://github.com/Suzyy/Contact-List-Manager-Application")
            .license("Suzy_Lee License")
            .licenseUrl("https://github.com/Suzyy/Contact-List-Manager-Application").version("1.0").build();
    }

    //DESCRIPTION: API expects 'Authorization' header with jwt token
    private ApiKey apiKey() {
        return new ApiKey("Authorization", "JWT", "header");
    }

    //DESCRIPTION: Specifiying security context for API
    private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(defaultAuth()).build();
    }

    //Creating security reference where authorization w/ scope "global" "accessEverything"
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }
}
