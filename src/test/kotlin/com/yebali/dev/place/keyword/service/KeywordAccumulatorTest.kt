package com.yebali.dev.place.keyword.service

import com.yebali.dev.config.RedisContainerBaseTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class KeywordAccumulatorTest : RedisContainerBaseTest() {
    @Autowired
    private lateinit var keywordAccumulator: KeywordAccumulator

    @Test
    fun `누적된 키워드의 검색 횟수들을 내림차순으로 N개 조회 할 수 있다`() {
        // given
        val rankSizeToFetch = 2
        val keywordAndViews = listOf(
            "치킨" to 12L,
            "곱창" to 9L,
            "피자빵" to 17L,
            "갓카오" to 7L,
        )

        // when
        keywordAndViews.forEach { (keyword, views) ->
            repeat(views.toInt()) {
                keywordAccumulator.accumulateKeyword(keyword)
            }
        }

        val fetched = keywordAccumulator.fetchMostViewedKeywords(rankSizeToFetch.toLong())

        // then
        val expected = keywordAndViews.sortedByDescending { it.second }.take(rankSizeToFetch)

        assertThat(fetched).containsExactlyInAnyOrderElementsOf(expected)
    }
}
