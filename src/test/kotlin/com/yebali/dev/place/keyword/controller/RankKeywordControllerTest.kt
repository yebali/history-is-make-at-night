package com.yebali.dev.place.keyword.controller

import com.ninjasquad.springmockk.MockkBean
import com.yebali.dev.place.config.BasicProperties
import com.yebali.dev.place.keyword.service.KeywordRankService
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(RankKeywordController::class)
@ExtendWith(MockKExtension::class)
@EnableConfigurationProperties(BasicProperties::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class RankKeywordControllerTest(
    private val mockMvc: MockMvc,
) {
    @MockkBean
    private lateinit var keywordRankService: KeywordRankService

    @Test
    fun `많이 검색된 키워드 내림차순 조회`() {
        every { keywordRankService.getMostViewedKeywords(any()) } returns listOf(
            "곱창" to 9L,
            "치킨" to 12L,
        )

        mockMvc.get("/place/keyword/rank")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                jsonPath("$.content[0].rank") { value(1) }
                jsonPath("$.content[0].keyword") { value("치킨") }
                jsonPath("$.content[0].views") { value(12L) }

                jsonPath("$.content[1].rank") { value(2) }
                jsonPath("$.content[1].keyword") { value("곱창") }
                jsonPath("$.content[1].views") { value(9L) }

                jsonPath("$.size") { 2 }
            }
    }
}
