package com.devgabriel.mercadolivro.security

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
}