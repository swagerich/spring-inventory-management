package com.erich.management.Config;

import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//OTRA MANERA DE PONER LOS JWT COMO ANOTACION
//@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER, bearerFormat = "JWT", scheme = "bearer")
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openDetails() {
        final String securityScheme = "bearerAuth";
        final String bearerFormat = "JWT";
        final String bearer = "bearer";

        return new OpenAPI().info(new Info().title("Spring Inventory API Documentation")
                        .description("Spring Inventory REST API")
                        .version("v3.0.1")
                        .license(new License().name("Apache 2.0").url("https://spring.io")))
                .externalDocs(new ExternalDocumentation()
                        .description("Spring Inventory Proyect Documentation ")
                        .url("https://github.com/swagerich/"))
                .addSecurityItem(new SecurityRequirement().addList(securityScheme))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name(securityScheme)
                                        .type(SecurityScheme.Type.HTTP)
                                        .in(SecurityScheme.In.HEADER)
                                        .scheme(bearer)
                                        .bearerFormat(bearerFormat)));
    }


}
