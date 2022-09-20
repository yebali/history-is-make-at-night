package com.yebali.dev.place.keyword.controller.model

import com.yebali.dev.place.keyword.service.model.GetMostViewedKeywordsModel

class GetKeywordRankRequest(
    val size: Long = 10,
) {
    fun toModel() = GetMostViewedKeywordsModel(
        size = this.size
    )
}
