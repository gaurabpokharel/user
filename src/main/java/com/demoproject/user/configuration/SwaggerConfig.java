package com.demoproject.user.configuration;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String title = "User Rest API";
    private static final String description = "API details of User";
    private static final String SWAGGer_VERSION = "1.0";

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(title).description(description).version(SWAGGer_VERSION).build();
    }

//    @Bean
//    public Docket docket() {
//        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any()).build();
//    }
}
