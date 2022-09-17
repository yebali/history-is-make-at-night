package com.yebali.dev.place.keyword.service

import com.yebali.dev.place.util.Logging
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ZSetOperations
import org.springframework.stereotype.Component

@Component
class KeywordAccumulator(
    private val stringRedisTemplate: StringRedisTemplate,
) {
    fun accumulateKeyword(keyword: String) {
        runCatching {
            stringRedisTemplate.opsForZSet().incrementScore(REDIS_KEYWORD_ACCUMULATOR_KEY, keyword, 1.0)
        }.onFailure {
            logger.error(it.message)
        }
    }

    fun fetchKeywordsTopN(n: Long): List<Pair<String, Long>> {
        return stringRedisTemplate.opsForZSet().fetchBiggestNWithScores(n)
    }

    private fun ZSetOperations<String, String>.fetchBiggestNWithScores(n: Long): List<Pair<String, Long>> {
        val zSetSize = stringRedisTemplate.opsForZSet().size(REDIS_KEYWORD_ACCUMULATOR_KEY)
            ?: return emptyList()

        return stringRedisTemplate.opsForZSet()
            .rangeWithScores(REDIS_KEYWORD_ACCUMULATOR_KEY, zSetSize - n, zSetSize)
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

    companion object : Logging() {
        const val REDIS_KEYWORD_ACCUMULATOR_KEY = "LocationSearchService:KeywordAccumulation"
    }
}
