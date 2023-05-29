package com.hsproject.envmarket.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "jwt")
class JwtConfiguration {
    lateinit var secret: String
    var tokenValidity: Long = 0
}
