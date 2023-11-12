package org.tagwonder.configs

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
open class OpenApiConfig {
    @Bean
    open fun openAPI(): OpenAPI {
        val info: Info = Info()
            .version("v1.0.0")
            .title("Tag Wonder Api Document")
        return OpenAPI()
            .info(info)
    }
}
