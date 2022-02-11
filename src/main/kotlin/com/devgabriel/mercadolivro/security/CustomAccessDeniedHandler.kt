package com.devgabriel.mercadolivro.security

import com.devgabriel.mercadolivro.controller.response.ErrorResponse
import com.devgabriel.mercadolivro.exception.Errors
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAccessDeniedHandler : AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException?
    ) {
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val errorResponse = ErrorResponse(HttpStatus.UNAUTHORIZED.value(), Errors.ML_000.message, Errors.ML_000.code, null)
        response.outputStream.print(jacksonObjectMapper().writeValueAsString(errorResponse))
    }
}