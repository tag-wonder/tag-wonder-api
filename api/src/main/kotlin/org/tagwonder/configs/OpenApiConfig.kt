package org.tagwonder.configs

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
open class OpenApiConfig {
    @Bean
    open fun openAPI(
        @Value("\${api.url}") url: String
    ): OpenAPI {
        return OpenAPI()
            .servers(listOf(Server().apply { setUrl(url) }))
            .info(
                Info()
                    .version("v1.0.0")
                    .title("Tag Wonder Api Document")
            )
    }
}
