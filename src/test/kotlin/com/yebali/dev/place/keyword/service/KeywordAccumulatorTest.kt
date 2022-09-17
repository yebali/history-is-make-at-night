package com.yebali.dev.place.keyword.service

import com.yebali.dev.config.RedisContainerBaseTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KeywordAccumulatorTest : RedisContainerBaseTest() {
    @Autowired
    private lateinit var stringRedisTemplate: StringRedisTemplate

    @Autowired
    private lateinit var keywordAccumulator: KeywordAccumulator

    @Test
    fun `키워드 검색 횟수를 레디스에 누적할 수 있다`() {
        // given
        val views = 5
        val keyword = "피자"

        // when
        repeat(views) {
            keywordAccumulator.accumulateKeyword(keyword)
        }

        // then
        val expectedMostViewedKeyword = keyword to views.toLong()
        val mostViewedKeyword = keywordAccumulator.fetchKeywordsTopN(1).first()

        assertThat(mostViewedKeyword.first).isEqualTo(expectedMostViewedKeyword.first)
        assertThat(mostViewedKeyword.second).isEqualTo(expectedMostViewedKeyword.second)
    }
}
