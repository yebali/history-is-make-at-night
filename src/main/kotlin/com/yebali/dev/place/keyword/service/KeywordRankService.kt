package com.yebali.dev.place.keyword.service

import com.yebali.dev.place.keyword.service.model.AccumulateKeywordViewsModel
import com.yebali.dev.place.keyword.service.model.GetMostViewedKeywordsModel
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class KeywordRankService(
    private val keywordAccumulator: KeywordAccumulator,
) {
    @Async
    fun accumulateKeywordViews(model: AccumulateKeywordViewsModel) {
        keywordAccumulator.accumulateKeyword(
            keyword = model.keyword,
        )
    }

    fun getMostViewedKeywords(model: GetMostViewedKeywordsModel): List<Pair<String, Long>> {
        return keywordAccumulator.fetchMostViewedKeywords(
            size = model.size.coerceAtMost(MAX_SEARCH_SIZE)
        )
    }

    private companion object {
        const val MAX_SEARCH_SIZE = 10L
    }
}
