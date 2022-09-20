package com.yebali.dev.place.keyword.controller

import com.yebali.dev.place.keyword.controller.model.GetKeywordRankRequest
import com.yebali.dev.place.keyword.controller.model.GetKeywordRankResponse
import com.yebali.dev.place.keyword.service.KeywordRankService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/place/keywords")
class RankKeywordController(
    private val keywordRankService: KeywordRankService,
) {
    @GetMapping("/ranks")
    fun getKeywordRank(
        @ModelAttribute request: GetKeywordRankRequest,
    ) = GetKeywordRankResponse.from(
        keywordRankService.getMostViewedKeywords(request.toModel())
    )
}
