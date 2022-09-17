package com.yebali.dev.place.keyword.controller.model

import com.yebali.dev.place.keyword.service.model.GetMostViewedModelKeywords

class GetKeywordRankRequest(
    val size: Long = 10,
) {
    fun toModel() = GetMostViewedModelKeywords(
        size = this.size
    )
}
