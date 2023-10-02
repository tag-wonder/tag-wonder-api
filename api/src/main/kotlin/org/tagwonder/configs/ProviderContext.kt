package org.tagwonder.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.tagwonder.jwt.AuthTokenGenerator
import org.tagwonder.jwt.JwtTokenProvider

@Configuration
open class ProviderContext {
    @Bean
    open fun jwtTokenProvider(
        @Value("\${jwt.secret-key}") secretKey: String
    ): JwtTokenProvider {
        return JwtTokenProvider(secretKey)
    }

    @Bean
    open fun authTokenGenerator(
        jwtTokenProvider: JwtTokenProvider
    ): AuthTokenGenerator {
        return AuthTokenGenerator(jwtTokenProvider)
    }
}
