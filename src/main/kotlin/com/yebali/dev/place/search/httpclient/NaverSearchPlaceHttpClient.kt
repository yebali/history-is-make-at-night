package com.yebali.dev.place.search.httpclient

import com.yebali.dev.place.config.openapi.NaverOpenAPIProperties
import com.yebali.dev.place.feignclient.place.naver.NaverSearchPlaceFeign
import com.yebali.dev.place.feignclient.place.naver.model.NaverSearchPlaceFeignResponseModel
import com.yebali.dev.place.search.service.SearchPlaceFetcher
import com.yebali.dev.place.search.service.model.SearchPlaceModel
import com.yebali.dev.place.search.service.model.SearchPlaceResultModel
import com.yebali.dev.place.util.Logging
import com.yebali.dev.place.util.removeHtmlTags
import org.springframework.stereotype.Component

@Component
class NaverSearchPlaceHttpClient(
    private val naverSearchPlaceFeign: NaverSearchPlaceFeign,
    private val naverOpenAPIProperties: NaverOpenAPIProperties,
) : SearchPlaceFetcher {
    override fun fetchLocations(model: SearchPlaceModel): List<SearchPlaceResultModel> {
        return try {
            naverSearchPlaceFeign.searchPlace(
                clientId = naverOpenAPIProperties.xNaverClientId,
                clientSecret = naverOpenAPIProperties.xNaverClientSecret,
                query = model.keyword,
            ).toResultModel()
        } catch (e: Exception) {
            logger.error(e.message)
            emptyList()
        }
    }

    private fun NaverSearchPlaceFeignResponseModel.toResultModel(): List<SearchPlaceResultModel> {
        return this.items.map {
            SearchPlaceResultModel(
                placeName = it.title.removeHtmlTags(),
                address = it.address,
                roadAddress = it.roadAddress,
                phoneNumber = it.telephone,
            )
        }
    }

    companion object : Logging()
}
