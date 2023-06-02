package com.hsproject.envmarket.util

import com.hsproject.envmarket.config.JwtConfiguration
import com.hsproject.envmarket.repository.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.nio.file.attribute.UserPrincipal
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@Component
class JwtProvider(val jwtConfiguration: JwtConfiguration, val userRepository: UserRepository) {

    private val logger = LoggerFactory.getLogger(JwtProvider::class.java)

    fun generateToken(authentication: Authentication): String {
        val email = authentication.name
        val user = userRepository.findByEmail(email) ?: throw UsernameNotFoundException("User with email: $email not found")
        val now = ZonedDateTime.now(ZoneId.systemDefault())
        val tokenExpiration = now.plusSeconds(jwtConfiguration.tokenValidity)

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date())
                .setExpiration(Date.from(tokenExpiration.toInstant()))
                .signWith(SignatureAlgorithm.HS512, jwtConfiguration.secret)
                .compact()
    }

}


