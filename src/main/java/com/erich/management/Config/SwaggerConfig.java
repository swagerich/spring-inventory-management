package com.erich.management.Config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openDetails(){
        return new OpenAPI().info(new Info().title("Spring Inventory API Documentation")
                        .description("Spring Inventory REST API")
                        .version("v3.0.1")
                        .license(new License().name("Apache 2.0").url("https://spring.io")))
                        .externalDocs(new ExternalDocumentation()
                        .description("Spring Inventory Proyect Documentation ")
                        .url("https://github.com/swagerich/"));
    }


}
