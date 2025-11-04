package com.tiquetera.catalog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    /**
     * OpenAPI configuration bean.
     *
     * The returned OpenAPI object provides general API metadata used by the
     * Swagger UI and /v3/api-docs JSON endpoint.
     *
     * Keep the title, description and version up to date when creating
     * backwards-incompatible changes.
     */
    @Bean
    public OpenAPI catalogOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Catalog API")
                        .description("API for managing events and venues")
                        .version("1.0"));
    }
}
