package org.tagwonder.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.security.Key
import java.util.*

class JwtTokenProvider(
    secretKey: String
) {
    private val key: Key
    init {
        val keyBytes = Base64.getDecoder().decode(secretKey)
        this.key = Keys.hmacShaKeyFor(keyBytes)
    }

    fun generate(subject: String, expiredAt: Date): String {
        return Jwts.builder()
            .setSubject(subject)
            .setExpiration(expiredAt)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun extractSubject(accessToken: String): String {
        val claims = parseClaims(accessToken)
        return claims.subject
    }

    private fun parseClaims(accessToken: String): Claims {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .body
        } catch (e: ExpiredJwtException) {
            return e.claims
        }
    }
}
