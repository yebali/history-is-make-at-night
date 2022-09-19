package com.yebali.dev.place.feignclient.coodinate.kakao.model

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoTransformCoordinateFeignResponseModel(
    val meta: Meta,
    val documents: List<Document>
) {
    data class Meta(
        @JsonProperty("total_count")
        val totalCount: Int,
    )

    data class Document(
        val x: Double,
        val y: Double,
    )
}
