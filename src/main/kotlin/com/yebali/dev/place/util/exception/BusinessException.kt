package com.yebali.dev.place.util.exception

import com.yebali.dev.place.util.ErrorCode
import org.springframework.http.HttpStatus

open class BusinessException(
    val code: String,
    val status: HttpStatus,
    override val message: String = "",
    cause: Throwable? = null,
) : RuntimeException(message, cause) {

    constructor(
        errorCode: ErrorCode,
        messageMapper: ((String) -> String)? = null,
        cause: Throwable? = null,
    ) : this(
        errorCode.code,
        errorCode.status,
        messageMapper?.let { messageMapper(errorCode.message) } ?: errorCode.message,
        cause,
    )

    constructor(
        errorCode: ErrorCode,
        additionalMessage: String,
        cause: Throwable? = null,
    ) : this(
        errorCode.code,
        errorCode.status,
        "${errorCode.message} $additionalMessage",
        cause,
    )
}