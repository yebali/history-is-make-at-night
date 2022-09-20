package com.yebali.dev.place.search.service

import com.yebali.dev.place.search.service.model.SearchPlaceModel
import com.yebali.dev.place.search.service.model.SearchPlaceResultModel

interface SearchPlaceFetcher {
    fun searchPlaces(model: SearchPlaceModel): List<SearchPlaceResultModel>
}
