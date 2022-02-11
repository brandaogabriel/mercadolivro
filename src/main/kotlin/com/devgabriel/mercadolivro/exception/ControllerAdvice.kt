package com.devgabriel.mercadolivro.exception

import com.devgabriel.mercadolivro.controller.response.ErrorResponse
import com.devgabriel.mercadolivro.controller.response.FieldErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
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

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        ex: MethodArgumentNotValidException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val httStatus = HttpStatus.BAD_REQUEST
        val error = ErrorResponse(
            httpCode = httStatus.value(),
            message = Errors.ML_001.message,
            internalCode = Errors.ML_001.code,
            errors = ex.bindingResult.fieldErrors.map { FieldErrorResponse(it.field, it.defaultMessage ?: "invalid") }
        )
        return ResponseEntity(error, httStatus)
    }

    /**
     * Exception to catch @PreAuthorize when invalid
     */
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(
        ex: AccessDeniedException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val httStatus = HttpStatus.UNAUTHORIZED
        val error = ErrorResponse(
            httpCode = httStatus.value(),
            message = Errors.ML_000.message,
            internalCode = Errors.ML_000.code,
            errors = null
        )
        return ResponseEntity(error, httStatus)
    }
}