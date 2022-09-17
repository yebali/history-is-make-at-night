package com.yebali.dev.place.feignclient.location.naver

import com.yebali.dev.place.feignclient.location.naver.model.NaverSearchPlaceFeignResponseModel
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

/**
 * [네이버 지역 검색 API](https://developers.naver.com/docs/serviceapi/search/local/local.md#%ED%8C%8C%EB%9D%BC%EB%AF%B8%ED%84%B0)
 * */
@FeignClient(
    name = "NaverSearchLocationFeignClient",
    url = "\${open-api.naver.base-url}"
)
interface NaverSearchPlaceFeign {
    @GetMapping("\${open-api.naver.search-place-url}")
    fun searchPlace(
        @RequestHeader("X-Naver-Client-Id") clientId: String,
        @RequestHeader("X-Naver-Client-Secret") clientSecret: String,
        @RequestParam query: String,
        @RequestParam display: Int = 5, // API가 제공하는 최대값 == 5
        @RequestParam start: Int = 1, // API가 제공하는 기본 및 최대 값
        @RequestParam sort: String = SortCondition.RANDOM.value,
    ): NaverSearchPlaceFeignResponseModel

    enum class SortCondition(val value: String) {
        /** 정확도순으로 내림차순(기본 값) */
        RANDOM("random"),
        /** 카페, 블로그 리뷰 개수순 내림차순 */
        COMMENT("comment")
    }
}
