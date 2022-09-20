package com.yebali.dev.place.keyword.service

import com.yebali.dev.place.util.Logging
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class KeywordAccumulator(
    private val stringRedisTemplate: StringRedisTemplate,
) {
    fun accumulateKeyword(keyword: String) {
        runCatching {
            stringRedisTemplate.opsForZSet().incrementScore(REDIS_KEYWORD_ACCUMULATOR_KEY, keyword, 1.0)
        }.onFailure {
            // Todo 에러 메세지
            logger.error(it.message)
        }
    }

    fun fetchMostViewedKeywords(size: Long): List<Pair<String, Long>> {
        return fetchKeywordsOrderByScoreInDesc(size)
    }

    private fun fetchKeywordsOrderByScoreInDesc(size: Long): List<Pair<String, Long>> {
        val zSetRecordCount = getZSetRecordCount()
            ?: return emptyList()

        return stringRedisTemplate.opsForZSet()
            .rangeWithScores(REDIS_KEYWORD_ACCUMULATOR_KEY, zSetRecordCount - size, zSetRecordCount)
            ?.let {
                it.mapNotNull { typedTuple ->
                    if (typedTuple.value == null || typedTuple.score == null)
                        null
                    else
                        typedTuple.value!! to typedTuple.score!!.toLong()
                }
            }
            ?: return emptyList()
    }

    private fun getZSetRecordCount() = stringRedisTemplate.opsForZSet().size(REDIS_KEYWORD_ACCUMULATOR_KEY)

    companion object : Logging() {
        const val REDIS_KEYWORD_ACCUMULATOR_KEY = "PlaceSearchService:KeywordAccumulation"
    }
}
