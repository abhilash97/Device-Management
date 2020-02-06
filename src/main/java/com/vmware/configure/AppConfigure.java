package com.vmware.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collection;

import static com.google.common.collect.Lists.newArrayList;


@Configuration
@EnableSwagger2
public class AppConfigure {

    @Bean
    public Docket api(){
//        return new Docket(DocumentationType.SWAGGER_2).select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any()).build().securityContexts(Collections.singletonList(securityContext()))
//                .securitySchemes(Arrays.asList(apiKey()))
//                .useDefaultResponseMessages(false);

        AuthorizationScope[] authScopes = new AuthorizationScope[1];
        authScopes[0] = new AuthorizationScopeBuilder().scope("global").description("full access").build();
        SecurityReference securityReference = SecurityReference.builder().reference("Authorization-Key").scopes(authScopes).build();

        ArrayList<SecurityContext> securityContexts = newArrayList(
                SecurityContext.builder().securityReferences(newArrayList(securityReference)).build());
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(newArrayList(new ApiKey("Authorization-Key", "Authorization", "header")))
                .securityContexts(securityContexts).select().build();
    }
//    private SecurityContext securityContext() {
//        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("/.*")).build();
//    }
//    private List<SecurityReference> defaultAuth() {
//        final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
//        return Collections.singletonList(new SecurityReference("API_KEY", authorizationScopes));
//    }

    @Bean
    public SecurityConfiguration security() {
        return new springfox.documentation.swagger.web.SecurityConfiguration(
                null, null, null,
                "deviceTracker",
                "access_token",
                ApiKeyVehicle.QUERY_PARAM,
                "access_token",
                null);
    }
//  @Bean
    public ApiKey apiKey() {
        return new ApiKey("apikey", "Authorization", "header");
    }
}
