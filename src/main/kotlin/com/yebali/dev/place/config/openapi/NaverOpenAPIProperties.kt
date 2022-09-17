package com.yebali.dev.place.config.openapi

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("open-api.naver")
class NaverOpenAPIProperties(
    val xNaverClientId: String,
    val xNaverClientSecret: String,
)
