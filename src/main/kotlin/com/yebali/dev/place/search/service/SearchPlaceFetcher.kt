package com.yebali.dev.place.search.service

import com.yebali.dev.place.search.service.model.SearchPlaceModel
import com.yebali.dev.place.search.service.model.SearchPlaceResultModel

interface SearchPlaceFetcher {
    fun fetchLocations(model: SearchPlaceModel): List<SearchPlaceResultModel>
}
