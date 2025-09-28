package com.sps.electronic.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;


@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(getApiInfo());  //we have to create this method to return api info


        docket.securityContexts(Arrays.asList(getSecurityContext()));  //create this method
        docket.securitySchemes(Arrays.asList(getSchemes())); //create this method

        ApiSelectorBuilder select = docket.select();
        select.apis(RequestHandlerSelectors.any());
        select.paths(PathSelectors.any());   //for all the apis
        Docket build = select.build();

        return build;
    }


    private SecurityContext getSecurityContext() {

        SecurityContext context = SecurityContext   //SecurityContext is commming from springfox while importing make sure that
                .builder()
                .securityReferences(getSecurityReferences())  //create this method
                .build();
        return context;
    }

    private List<SecurityReference> getSecurityReferences() {
        AuthorizationScope[] scopes = {new AuthorizationScope("Global", "Access Every Thing")};
        return Arrays.asList(new SecurityReference("JWT", scopes));

    }

    private ApiKey getSchemes() {
        return new ApiKey("JWT", "Authorization", "header");
        //Authorization -key   header- as a header
    }


    private ApiInfo getApiInfo() {

        ApiInfo apiInfo = new ApiInfo(      //open ApiInfo and see which parameter we have to pass
                "Electronic Store Backend : APIS ",  //title
                "This is backend project created by SPS",  //description
                "1.0.0V",     //version
                "https://www.learncodewithdurgesh.com",   //termsOfServiceUrl
                new Contact("Shakil", "https://www.instagram.com/shakil6050", "shakilpatel2396@gamil.com"),
                "License of APIS",     //contact...just open this and see which parameter we have to pass
                "https://www.learncodewithdurgesh.com/about",   //licence
                new ArrayDeque<>()
        );

        return apiInfo;
    }
}

