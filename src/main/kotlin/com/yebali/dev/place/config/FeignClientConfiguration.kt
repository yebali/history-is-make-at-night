package com.yebali.dev.place.config

import com.yebali.dev.place.PlaceSearchService
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackageClasses = [PlaceSearchService::class])
class FeignClientConfiguration
