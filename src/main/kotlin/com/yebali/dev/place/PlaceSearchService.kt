package com.yebali.dev.place

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@ConfigurationPropertiesScan("com.yebali.dev.place")
@EnableAsync
@SpringBootApplication
class PlaceSearchService

fun main(args: Array<String>) {
    runApplication<PlaceSearchService>(*args)
}
