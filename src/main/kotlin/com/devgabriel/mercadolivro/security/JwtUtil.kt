package com.devgabriel.mercadolivro.security

import com.devgabriel.mercadolivro.exception.AuthenticationException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil(
    @Value("\${jwt.duration}")
    private val jwtDuration: Long,

    @Value("\${jwt.secret-key}")
    private val jwtSecretKey: String,
) {

    fun generateToke(id: Long): String {
        return Jwts.builder()
            .setSubject(id.toString())
            .setExpiration(Date(System.currentTimeMillis() + jwtDuration))
            .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
            .compact()
    }

    fun isValidToken(token: String): Boolean {
        val claims = getClaims(token)
        if (claims.subject.isNullOrBlank() || claims.expiration == null || Date().after(claims.expiration)) {
            return false;
        }
        return true;
    }

    private fun getClaims(token: String): Claims {
        try {
            return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).body
        } catch (ex: Exception) {
            throw AuthenticationException("Token inválido", "9999")
        }
    }

    fun getSubject(token: String): String {
        return getClaims(token).subject
    }
}