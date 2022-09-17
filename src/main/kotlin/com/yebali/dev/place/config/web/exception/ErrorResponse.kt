package com.yebali.dev.place.config.web.exception

import java.time.OffsetDateTime

class ErrorResponse(
    val timestamp: OffsetDateTime = OffsetDateTime.now(),
    val message: String?,
    val path: String?,
    val serviceName: String,
    val errorCode: String
)
