package com.yebali.dev.place.feignclient.coodinate.kakao

import com.yebali.dev.place.feignclient.location.kakao.model.KakaoSearchPlaceFeignResponseModel
import com.yebali.dev.place.util.CoordinateSystem
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "KaKaoTransformCoordinateFeignClient",
    url = "\${open-api.kakao.base-url}"
)
interface KaKaoTransformCoordinateFeign {
    @GetMapping("\${open-api.kakao.transform-coordinate-url}")
    fun transformCoordinate(
        @RequestHeader("Authorization") auth: String,
        @RequestParam x: Double,
        @RequestParam y: Double,
        @RequestParam input_coord: String? = CoordinateSystem.WGS84.value,
        @RequestParam output_coord: String
    ): KakaoSearchPlaceFeignResponseModel
}
