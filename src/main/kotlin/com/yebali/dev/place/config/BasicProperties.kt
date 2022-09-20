package com.yebali.dev.place.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("basic")
class BasicProperties(
    val serviceName: String,
)
