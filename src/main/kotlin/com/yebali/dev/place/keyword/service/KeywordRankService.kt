package com.yebali.dev.place.keyword.service

import com.yebali.dev.place.keyword.service.model.AccumulateKeywordViewsModel
import com.yebali.dev.place.keyword.service.model.GetMostViewedModelKeywords
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
        keywordAccumulator.accumulateKeyword(model.keyword)
    }

    fun fetchMostViewedKeywords(model: GetMostViewedModelKeywords): List<Pair<String, Long>> {
        return keywordAccumulator.fetchKeywordsTopN(model.size)
    }
}
