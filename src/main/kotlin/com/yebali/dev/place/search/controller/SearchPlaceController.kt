package com.yebali.dev.place.search.controller

import com.yebali.dev.place.keyword.service.KeywordRankService
import com.yebali.dev.place.keyword.service.model.AccumulateKeywordViewsModel
import com.yebali.dev.place.search.controller.model.SearchPlaceRequest
import com.yebali.dev.place.search.service.SearchPlaceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/place/search")
class SearchPlaceController(
    private val searchPlaceService: SearchPlaceService,
    private val keywordRankService: KeywordRankService,
) {
    @GetMapping
    fun searchLocation(
        @ModelAttribute request: SearchPlaceRequest
    ) {
        searchPlaceService.searchLocation(request.toModel())
        keywordRankService.accumulateKeywordViews(
            model = AccumulateKeywordViewsModel(keyword = request.keyword)
        )
    }
}
