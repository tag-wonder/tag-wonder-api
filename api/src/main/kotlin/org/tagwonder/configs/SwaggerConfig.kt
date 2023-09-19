package org.tagwonder.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
@EnableWebMvc
open class SwaggerConfig {
    @Bean
    open fun swaggerApi(): Docket = Docket(DocumentationType.OAS_30)
        .consumes(getConsumeContentTypes())
        .produces(getProduceContentTypes())
        .apiInfo(swaggerInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("org.tagwonder"))
        .paths(PathSelectors.any())
        .build()
        .useDefaultResponseMessages(false)

    private fun swaggerInfo() = ApiInfoBuilder()
        .title("Swagger Api Document")
        .description("Tag Wonder Api Document")
        .version("1.0.0")
        .build()

    private fun getConsumeContentTypes(): Set<String> {
        val consumes = HashSet<String>()
        consumes.add("multipart/form-data")
        return consumes
    }

    private fun getProduceContentTypes(): Set<String> {
        val produces = HashSet<String>()
        produces.add("application/json;charset=UTF-8")
        return produces
    }
}
