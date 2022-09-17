package com.yebali.dev.place.search.service

import com.yebali.dev.place.search.httpclient.KakaoSearchPlaceHttpClient
import com.yebali.dev.place.search.httpclient.NaverSearchPlaceHttpClient
import com.yebali.dev.place.search.service.model.SearchPlaceModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class SearchPlaceService(
    private val kakaoPlaceSearchHttpClient: KakaoSearchPlaceHttpClient,
    private val naverPlaceSearchHttpClient: NaverSearchPlaceHttpClient,
) {
    fun searchLocation(model: SearchPlaceModel) {
        val (locationsFromKakao, locationsFromNaver) = runBlocking(Dispatchers.IO) {
            val locationsFromKakao = async {
                kakaoPlaceSearchHttpClient.fetchLocations(model)
            }

            val locationsFromNaver = async {
                naverPlaceSearchHttpClient.fetchLocations(model)
            }

            locationsFromKakao.await() to locationsFromNaver.await()
        }

        println("================kakao================")
        locationsFromKakao.forEach {
            println(it)
        }
        println("================naver================")
        locationsFromNaver.forEach {
            println(it)
        }
    }
}
