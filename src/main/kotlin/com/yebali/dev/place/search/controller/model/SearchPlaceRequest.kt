package com.yebali.dev.place.search.controller.model

import com.yebali.dev.place.search.service.model.SearchPlaceModel

class SearchPlaceRequest(
    val keyword: String,
) {
    fun toModel() = SearchPlaceModel(
        keyword = this.keyword
    )
}
