package com.yebali.dev.place.feignclient.location.naver.model

import com.fasterxml.jackson.annotation.JsonProperty

data class NaverSearchPlaceFeignResponseModel(
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<Item>
) {
    data class Item(
        val title: String,
        val link: String,
        val category: String,
        val description: String,
        val telephone: String,
        val address: String,
        val roadAddress: String,
        @JsonProperty("mapx")
        val mapX: Int,
        @JsonProperty("mapy")
        val mapY: Int,
    )
}
