package com.devgabriel.mercadolivro.exception

import com.devgabriel.mercadolivro.controller.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val httStatus = HttpStatus.NOT_FOUND
        val error = ErrorResponse(
            httpCode = httStatus.value(),
            message = ex.message,
            internalCode = ex.errorCode,
            errors = null
        )
        return ResponseEntity(error, httStatus)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val httStatus = HttpStatus.BAD_REQUEST
        val error = ErrorResponse(
            httpCode = httStatus.value(),
            message = ex.message,
            internalCode = ex.errorCode,
            errors = null
        )
        return ResponseEntity(error, httStatus)
    }
}