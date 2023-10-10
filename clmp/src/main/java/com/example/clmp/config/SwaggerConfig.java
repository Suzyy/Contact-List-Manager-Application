package com.example.clmp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    //Informing the swagger that we want documentation of type swagger2
    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("CLMP").apiInfo(apiInfo()).select()
            .paths(regex("/v1/contacts.*")).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Contact Service")
            .description("Contact API Documentation Generated Using Swagger2 for our REST API")
            .termsOfServiceUrl("https://github.com/Suzyy/Contact-List-Manager-Application")
            .license("Suzy_Lee License")
            .licenseUrl("https://github.com/Suzyy/Contact-List-Manager-Application").version("1.0").build();
    }
    
}
