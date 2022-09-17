package com.yebali.dev.place.config.web.exception

import com.yebali.dev.place.config.BasicProperties
import com.yebali.dev.place.util.Logging
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest

@Component
class CustomErrorAttributes(
    private val basicProperties: BasicProperties
) : DefaultErrorAttributes() {
    companion object : Logging()

    override fun getErrorAttributes(webRequest: WebRequest?, options: ErrorAttributeOptions?): ErrorAttributes {
        val throwable = getError(webRequest)
        logger.error("Error occurred", throwable)

        val errorAttributes =
            super.getErrorAttributes(webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE))

        errorAttributes.apply {
            set("serviceName", basicProperties.serviceName)
            set("errorCode", "UnexpectedException")
            remove("status")
            remove("error")
        }
        return errorAttributes
    }
}

typealias ErrorAttributes = MutableMap<String, Any>
