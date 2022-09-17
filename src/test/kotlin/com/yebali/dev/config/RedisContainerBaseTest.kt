package com.yebali.dev.config

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.TestConstructor
import org.testcontainers.containers.GenericContainer

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
abstract class RedisContainerBaseTest {
    companion object {
        @JvmStatic
        @DynamicPropertySource
        fun redisConfig(registry: DynamicPropertyRegistry) {
            registry.add("spring.redis.host", redisContainer::getHost)
            registry.add("spring.redis.port", redisContainer::getFirstMappedPort)
        }

        @JvmStatic
        protected val redisContainer = GenericContainer<Nothing>("redis:6.2.5-alpine").apply {
            withExposedPorts(6379)
        }
    }

    init {
        redisContainer.start()
    }
}
