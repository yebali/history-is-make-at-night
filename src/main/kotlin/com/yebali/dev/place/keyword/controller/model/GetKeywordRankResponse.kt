package com.yebali.dev.place.keyword.controller.model

class GetKeywordRankResponse(
    val content: List<KeywordRankDto>,
    val size: Int,
) {
    data class KeywordRankDto(
        val rank: Int,
        val keyword: String,
        val views: Long,
    )

    companion object {
        fun from(mostViewedKeywords: List<Pair<String, Long>>) = GetKeywordRankResponse(
            content = mostViewedKeywords
                .sortedByDescending { it.second } // it.second == score
                .mapIndexed { index, (keyword, views) ->
                    KeywordRankDto(
                        rank = index + 1,
                        keyword = keyword,
                        views = views,
                    )
                },
            size = mostViewedKeywords.size
        )
    }
}
