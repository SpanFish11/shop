package com.spanfish.shop.config;

import static java.util.Collections.singletonList;
import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;
import static springfox.documentation.spi.DocumentationType.OAS_30;
import static springfox.documentation.spi.service.contexts.SecurityContext.builder;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfiguration {

  @Bean
  public Docket api() {
    return new Docket(OAS_30)
        .select()
        .apis(withClassAnnotation(RestController.class))
        .paths(any())
        .build()
        .securityContexts(singletonList(securityContext()))
        .securitySchemes(singletonList(apiKey()));
  }

  private ApiKey apiKey() {
    return new ApiKey("Authorization", "JWT", "header");
  }

  private SecurityContext securityContext() {
    return builder().securityReferences(defaultAuth()).build();
  }

  private List<SecurityReference> defaultAuth() {
    final AuthorizationScope authorizationScope =
        new AuthorizationScope("global", "accessEverything");
    final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return singletonList(new SecurityReference("Authorization", authorizationScopes));
  }
}
