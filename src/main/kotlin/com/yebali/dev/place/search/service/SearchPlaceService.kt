package com.yebali.dev.place.search.service

import com.yebali.dev.place.search.httpclient.KakaoSearchPlaceHttpClient
import com.yebali.dev.place.search.httpclient.NaverSearchPlaceHttpClient
import com.yebali.dev.place.search.service.model.SearchPlaceModel
import com.yebali.dev.place.search.service.model.SearchPlaceResultModel
import com.yebali.dev.place.util.removeWhitespace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import kotlin.math.abs

@Service
class SearchPlaceService(
    private val kakaoPlaceSearchHttpClient: KakaoSearchPlaceHttpClient,
    private val naverPlaceSearchHttpClient: NaverSearchPlaceHttpClient,
) {
    fun searchPlace(model: SearchPlaceModel): List<SearchPlaceResultModel> {
        val (placesFromKakao, placesFromNaver) = runBlocking(Dispatchers.IO) {
            val placesFromKakao = async {
                kakaoPlaceSearchHttpClient.searchPlaces(model)
            }

            val placesFromNaver = async {
                naverPlaceSearchHttpClient.searchPlaces(model)
            }

            placesFromKakao.await() to placesFromNaver.await()
        }

        val duplicatedPlaces = placesFromKakao
            .take(5)
            .filter { it.isExistIn(placesFromNaver) }

        val searchedOnlyNaver = placesFromNaver.filter { !it.isExistIn(duplicatedPlaces) }
        val searchedOnlyKakao = placesFromKakao.filter { !it.isExistIn(duplicatedPlaces) }
            .take(model.size - (duplicatedPlaces.size + searchedOnlyNaver.size))

        return duplicatedPlaces + searchedOnlyKakao + searchedOnlyNaver
    }

    private fun SearchPlaceResultModel.isExistIn(others: List<SearchPlaceResultModel>): Boolean {
        others.forEach { other ->
            if (this.isWithInMarginOfError(other) && this.isSamePlaceName(other))
                return true
        }

        return false
    }

    /** 좌표를 비교하여 오차 범위내에서 같은 위치에 있는지 확인 */
    private fun SearchPlaceResultModel.isWithInMarginOfError(other: SearchPlaceResultModel): Boolean {
        return abs(this.x - other.x) < MARGIN_OF_ERROR && abs(this.y - other.y) < MARGIN_OF_ERROR
    }

    /** 상호명이 같은지 확인*/
    private fun SearchPlaceResultModel.isSamePlaceName(other: SearchPlaceResultModel): Boolean {
        return this.placeName.removeWhitespace() == other.placeName.removeWhitespace()
    }

    companion object {
        const val MARGIN_OF_ERROR = 75
    }
}
