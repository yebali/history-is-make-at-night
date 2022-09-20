package com.yebali.dev.place.search.service

import com.yebali.dev.place.search.httpclient.KakaoSearchPlaceHttpClient
import com.yebali.dev.place.search.httpclient.NaverSearchPlaceHttpClient
import com.yebali.dev.place.search.service.model.SearchPlaceModel
import com.yebali.dev.place.search.service.model.SearchPlaceResultModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class SearchPlaceServiceTest {
    @MockK
    private lateinit var kakaoSearchPlaceHttpClient: KakaoSearchPlaceHttpClient

    @MockK
    private lateinit var naverSearchPlaceHttpClient: NaverSearchPlaceHttpClient

    @InjectMockKs
    private lateinit var searchPlaceService: SearchPlaceService

    @Test
    fun `좌표가 오차 범위 내에 있고 상호명(placeName)이 같다면 같은 장소로 판단한다`() {
        // given
        val searchModel = SearchPlaceModel(keyword = "치킨", size = 1)
        every { kakaoSearchPlaceHttpClient.searchPlaces(searchModel) } returns listOf(
            SearchPlaceResultModel(
                placeName = "땅땅치킨",
                address = "강동구",
                roadAddress = "도로명_강동구",
                phoneNumber = "010-1234-5678",
                x = 20.0,
                y = 20.0
            )
        )
        every { naverSearchPlaceHttpClient.searchPlaces(searchModel) } returns listOf(
            SearchPlaceResultModel(
                placeName = "땅땅치킨",
                address = "강동구",
                roadAddress = "도로명_강동구",
                phoneNumber = "010-1234-5678",
                x = 25.0,
                y = 25.0
            )
        )

        // when
        val searched = searchPlaceService.searchPlace(searchModel)

        // then
        val expected = SearchPlaceResultModel(
            placeName = "땅땅치킨",
            address = "강동구",
            roadAddress = "도로명_강동구",
            phoneNumber = "010-1234-5678",
            x = 20.0,
            y = 20.0
        )

        Assertions.assertThat(searched.size).isEqualTo(1)
        Assertions.assertThat(searched.first()).isEqualTo(expected)
    }

    @Test
    fun `좌표가 오차 범위 내에 있어도 상호명(placeName)이 다르다면 다른 장소로 판단한다`() {
        // given
        val searchModel = SearchPlaceModel(keyword = "치킨", size = 2)
        val searchedByKakao = SearchPlaceResultModel(
            placeName = "땅땅치킨",
            address = "강동구",
            roadAddress = "도로명_강동구",
            phoneNumber = "010-1234-5678",
            x = 20.0,
            y = 20.0
        )

        val searchedByNaver = SearchPlaceResultModel(
            placeName = "네네치킨",
            address = "강동구",
            roadAddress = "도로명_강동구",
            phoneNumber = "010-1234-5678",
            x = 25.0,
            y = 25.0
        )

        every { kakaoSearchPlaceHttpClient.searchPlaces(searchModel) } returns listOf(searchedByKakao)
        every { naverSearchPlaceHttpClient.searchPlaces(searchModel) } returns listOf(searchedByNaver)

        // when
        val searched = searchPlaceService.searchPlace(searchModel)

        // then
        val expected = listOf(
            searchedByKakao,
            searchedByNaver,
        )

        Assertions.assertThat(searched.size).isEqualTo(2)
        Assertions.assertThat(searched).containsSequence(expected)
    }

    @Test
    fun `좌표가 오차 범위 밖에 있다면 상호명(placeName)이 같아도 다른 장소로 판단한다`() {
        // given
        val searchModel = SearchPlaceModel(keyword = "치킨", size = 2)
        val searchedByKakao = SearchPlaceResultModel(
            placeName = "땅땅치킨",
            address = "강동구",
            roadAddress = "도로명_강동구",
            phoneNumber = "010-1234-5678",
            x = 20.0,
            y = 20.0
        )

        val searchedByNaver = SearchPlaceResultModel(
            placeName = "땅땅치킨",
            address = "강동구",
            roadAddress = "도로명_강동구",
            phoneNumber = "010-1234-5678",
            x = 200.0,
            y = 200.0
        )

        every { kakaoSearchPlaceHttpClient.searchPlaces(searchModel) } returns listOf(searchedByKakao)
        every { naverSearchPlaceHttpClient.searchPlaces(searchModel) } returns listOf(searchedByNaver)

        // when
        val searched = searchPlaceService.searchPlace(searchModel)

        // then
        val expected = listOf(
            searchedByKakao,
            searchedByNaver,
        )

        Assertions.assertThat(searched.size).isEqualTo(2)
        Assertions.assertThat(searched).containsSequence(expected)
    }
}
