package com.devgabriel.mercadolivro.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfiguration {

    @Bean
    fun customOpenApi(): OpenAPI {
        return OpenAPI()
            .components(Components())
            .info(Info()
                .title("Mercado Livro API")
                .description("Api para testes em ambiente de desenvolvimento")
                .version("0.0.1-SNAPSHOT"))
    }
}