package com.jolin.conf;


import cn.hutool.core.collection.ListUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2WebMvc
public class Swagger2Config implements WebMvcConfigurer {
    private final String apiKeyName = "Bearer Token obtained after normal login or OAuth2 login";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("swagger-ui.html", "doc.html");
    }

    /**
     * That's the main method. The other methods are drawn out
     * In basePackage, write the controller path to which the document needs to be generated
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jolin.api")
                        .or(RequestHandlerSelectors.basePackage("com.jolin.log.api"))
                        .or(RequestHandlerSelectors.basePackage("com.jolin.api"))
                        .or(RequestHandlerSelectors.basePackage("com.jolin.backend"))
                        .or(RequestHandlerSelectors.basePackage("com.jolin.security.api"))
                        .or(RequestHandlerSelectors.basePackage("com.jolin.security.jwt.api")))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securityContexts(ListUtil.of(securityContextNormal()))
                .securitySchemes(ListUtil.of(normalApiKey()));
    }


    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Development framework",
                "Get a token for swagger：" +
                        "curl -i -X POST -d \"username=admin&password=123456&grant_type=password&client_id=swagger&client_secret=swagger\" http://localhost:8769/oauth/token " +
                        "You can make a post request using the curl command or something like postman to get tokens, and then paste it on the swagger Authorize with Bearer prefixes and spacing down",
                "1.0.0",
                "http://localhost:7779/",
                new Contact("", "xxx", "xxx@email.com.cn"),
                "License of API", "API license URL", Collections.emptyList());
    }


    //Log on to swagger
    private ApiKey normalApiKey() {
        return new ApiKey(apiKeyName, "Authorization", "header");
    }

    private SecurityContext securityContextNormal() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = {new AuthorizationScope("global", "accessEverything")};
        return ListUtil.of(new SecurityReference(apiKeyName, authorizationScopes));
    }
}
