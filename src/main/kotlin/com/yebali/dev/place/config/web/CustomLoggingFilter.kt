package com.yebali.dev.place.config.web

import com.yebali.dev.place.config.BasicProperties
import org.springframework.stereotype.Component
import org.springframework.web.filter.CommonsRequestLoggingFilter
import javax.servlet.http.HttpServletRequest

@Component
class CustomLoggingFilter: CommonsRequestLoggingFilter() {
    override fun isIncludeClientInfo() = true
    override fun isIncludeHeaders() = true
    override fun isIncludeQueryString() = true
    override fun isIncludePayload() = true
    override fun getMaxPayloadLength() = 10000
}
