package com.yebali.dev.place.config.web.exception

import com.yebali.dev.place.config.BasicProperties
import com.yebali.dev.place.util.Logging
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.badRequest
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class GlobalExceptionHandler(
    private val basicProperties: BasicProperties
) {
    companion object : Logging()

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    protected fun handleMethodArgumentTypeMismatchException(
        e: MethodArgumentTypeMismatchException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        logger.error("Error occurred", e)
        return badRequest().body(
            ErrorResponse(
                message = "Argument type mismatch. ${e.message}",
                path = request.requestURI,
                serviceName = basicProperties.serviceName,
                errorCode = "ArgumentTypeMismatchException"
            )
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    protected fun handleHttpMessageNotReadableException(
        e: HttpMessageNotReadableException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        logger.error("Error occurred", e)
        return badRequest().body(
            ErrorResponse(
                message = "Http request body message not readable. ${e.message}",
                path = request.requestURI,
                serviceName = basicProperties.serviceName,
                errorCode = "HttpMessageNotReadableException"
            )
        )
    }
}
