package com.yebali.dev.place.search.httpclient

import com.yebali.dev.place.config.openapi.KakaoOpenAPIProperties
import com.yebali.dev.place.feignclient.place.kakao.KakaoSearchPlaceFeign
import com.yebali.dev.place.feignclient.place.kakao.model.KakaoSearchPlaceFeignResponseModel
import com.yebali.dev.place.search.service.SearchPlaceFetcher
import com.yebali.dev.place.search.service.model.SearchPlaceModel
import com.yebali.dev.place.search.service.model.SearchPlaceResultModel
import com.yebali.dev.place.util.Logging
import org.springframework.stereotype.Component

@Component
class KakaoSearchPlaceHttpClient(
    private val kakaoSearchPlaceFeign: KakaoSearchPlaceFeign,
    private val kakaoOpenAPIProperties: KakaoOpenAPIProperties
) : SearchPlaceFetcher {
    override fun fetchLocations(model: SearchPlaceModel): List<SearchPlaceResultModel> {
        return try {
            kakaoSearchPlaceFeign.searchPlace(
                restAPIKey = kakaoOpenAPIProperties.restAPIKey,
                query = model.keyword
            ).toResultModel()
        } catch (e: Exception) {
            logger.error(e.message)
            emptyList()
        }
    }

    private fun KakaoSearchPlaceFeignResponseModel.toResultModel(): List<SearchPlaceResultModel> {
        return this.documents.map {
            SearchPlaceResultModel(
                placeName = it.placeName,
                address = it.addressName,
                roadAddress = it.roadAddress,
                phoneNumber = it.phone,
            )
        }
    }

    companion object : Logging()
}
