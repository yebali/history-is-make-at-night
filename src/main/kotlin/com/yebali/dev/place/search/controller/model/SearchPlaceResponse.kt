package com.yebali.dev.place.search.controller.model

import com.yebali.dev.place.search.service.model.SearchPlaceResultModel

class SearchPlaceResponse(
    val content: List<SearchPlaceResponseDto>,
    val size: Int,
) {
    data class SearchPlaceResponseDto(
        val placeName: String,
        val address: String,
        val roadAddress: String,
        val phoneNumber: String,
    )
    companion object {
        fun from(models: List<SearchPlaceResultModel>) =
            SearchPlaceResponse(
                content = models.map {
                    SearchPlaceResponseDto(
                        placeName = it.placeName,
                        address = it.address,
                        roadAddress = it.roadAddress,
                        phoneNumber = it.phoneNumber,
                    )
                },
                size = models.size,
            )
    }
}
