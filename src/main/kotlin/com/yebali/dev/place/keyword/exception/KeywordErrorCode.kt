package com.yebali.dev.place.keyword.exception

import com.yebali.dev.place.util.ErrorCode
import org.springframework.http.HttpStatus

// 해당 과제에서 사용되는 곳은 없지만 아래처럼 에러 코드를 별도로 모아 관리
enum class KeywordErrorCode(override val code: String, override val status: HttpStatus, override val message: String): ErrorCode {
    KeywordError("KEY-1001", HttpStatus.INTERNAL_SERVER_ERROR, "키워드 관련 에러가 발생했습니다")
}