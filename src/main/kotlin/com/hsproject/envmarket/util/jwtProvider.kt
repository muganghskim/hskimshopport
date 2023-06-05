package com.hsproject.envmarket.util

import com.hsproject.envmarket.config.JwtConfiguration
import com.hsproject.envmarket.repository.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import lombok.extern.slf4j.Slf4j
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
@Slf4j
@Component
class JwtProvider(val jwtConfiguration: JwtConfiguration, val userRepository: UserRepository) {

    val logger: Logger = LoggerFactory.getLogger(JwtProvider::class.java)

    fun generateToken(authentication: Authentication): String {
        val email = authentication.name
        val user = userRepository.findByEmail(email) ?: throw UsernameNotFoundException("User with email: $email not found")
        val now = ZonedDateTime.now(ZoneId.systemDefault())
        val tokenExpiration = now.plusSeconds(jwtConfiguration.tokenValidity)

        logger.info("email: $email")
        logger.info("user: $user")
        logger.info("now: $now")
        logger.info("tokenExpiration: $tokenExpiration")

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date())
                .setExpiration(Date.from(tokenExpiration.toInstant()))
                .signWith(SignatureAlgorithm.HS512, jwtConfiguration.secret)
                .compact()
    }

}


