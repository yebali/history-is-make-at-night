package com.yebali.dev.place.search.controller.model

import com.yebali.dev.place.search.service.model.SearchPlaceModel

data class SearchPlaceRequest(
    val keyword: String,
    val size: Int = 10,
) {
    fun toModel() = SearchPlaceModel(
        keyword = this.keyword,
        size = this.size,
    )
}
