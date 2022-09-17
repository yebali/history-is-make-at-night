package com.yebali.dev.place.feignclient.place.kakao

import com.yebali.dev.place.feignclient.place.kakao.model.KakaoSearchPlaceFeignResponseModel
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

/**
 * [카카오 장소 검색 API](https://developers.kakao.com/docs/latest/ko/local/dev-guide#search-by-keyword)
 * */
@FeignClient(
    name = "KakaoSearchLocationFeignClient",
    url = "\${open-api.kakao.base-url}"
)
interface KakaoSearchPlaceFeign {
    @GetMapping("\${open-api.kakao.search-place-url}")
    fun searchPlace(
        @RequestHeader("Authorization") restAPIKey: String,
        @RequestParam query: String,
        @RequestParam page: Int = 1,
        @RequestParam size: Int = 10,
    ): KakaoSearchPlaceFeignResponseModel
}
