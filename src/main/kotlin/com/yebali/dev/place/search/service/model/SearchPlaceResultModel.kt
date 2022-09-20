package com.yebali.dev.place.search.service.model

data class SearchPlaceResultModel(
    val placeName: String,
    val address: String,
    val roadAddress: String,
    val phoneNumber: String,
    val x: Double,
    val y: Double,
)
