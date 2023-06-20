package com.hsproject.envmarket.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "storage")
data class StorageProperties(
        var uploadDir: String = ""
)