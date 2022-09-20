package com.yebali.dev.place.keyword.exception

import com.yebali.dev.place.util.exception.BusinessException

class KeywordErrorException(
    additionalMessage: String = ""
): BusinessException (
    errorCode = KeywordErrorCode.KeywordError,
    additionalMessage = additionalMessage,
)