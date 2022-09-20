package com.yebali.dev.place.search.controller

import com.ninjasquad.springmockk.MockkBean
import com.yebali.dev.place.config.BasicProperties
import com.yebali.dev.place.keyword.service.KeywordRankService
import com.yebali.dev.place.search.service.SearchPlaceService
import com.yebali.dev.place.search.service.model.SearchPlaceResultModel
import io.mockk.Runs
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.just
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(SearchPlaceController::class)
@ExtendWith(MockKExtension::class)
@EnableConfigurationProperties(BasicProperties::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class SearchPlaceControllerTest(
    private val mockMvc: MockMvc,
) {
    @MockkBean
    private lateinit var searchPlaceService: SearchPlaceService

    @MockkBean
    private lateinit var keywordRankService: KeywordRankService

    @Test
    fun `장소 검색 및 조회`() {
        every { keywordRankService.accumulateKeywordViews(any()) } just Runs
        every { searchPlaceService.searchPlace(any()) } returns listOf(
            SearchPlaceResultModel(
                placeName = "장소",
                address = "강동구",
                roadAddress = "도로명_강동구",
                phoneNumber = "010-1234-5678",
                x = 123.45,
                y = 678.9
            )
        )

        mockMvc.get("/place/search?keyword=장소")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                jsonPath("$.content[0].placeName") { value("장소") }
                jsonPath("$.content[0].address") { value("강동구") }
                jsonPath("$.content[0].roadAddress") { value("도로명_강동구") }
                jsonPath("$.content[0].phoneNumber") { value("010-1234-5678") }

                jsonPath("$.size") { value(1) }
            }
    }
}
