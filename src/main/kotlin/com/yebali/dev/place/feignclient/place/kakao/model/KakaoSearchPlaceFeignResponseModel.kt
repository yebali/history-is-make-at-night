package com.yebali.dev.place.feignclient.place.kakao.model

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoSearchPlaceFeignResponseModel(
    val documents: List<Document>,
    val meta: Meta,
) {
    data class Document(
        @JsonProperty("address_name")
        val addressName: String,
        @JsonProperty("category_group_code")
        val categoryGroupCode: String,
        @JsonProperty("category_group_name")
        val categoryGroupName: String,
        @JsonProperty("category_name")
        val categoryName: String,
        val distance: String,
        val id: String,
        val phone: String,
        @JsonProperty("place_name")
        val placeName: String,
        @JsonProperty("place_url")
        val placeUrl: String,
        @JsonProperty("road_address_name")
        val roadAddress: String,
        val x: String,
        val y: String,
    )

    data class Meta(
        @JsonProperty("is_end")
        val isEnd: Boolean,
        @JsonProperty("pageable_count")
        val pageableCount: Int,
        @JsonProperty("same_name")
        val sameName: SameName,
        @JsonProperty("total_count")
        val totalCount: Int,
    ) {
        data class SameName(
            val keyword: String,
            val region: List<String>,
            @JsonProperty("selected_region")
            val selectedRegion: String,
        )
    }
}
