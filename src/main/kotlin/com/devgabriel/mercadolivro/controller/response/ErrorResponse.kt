package com.devgabriel.mercadolivro.controller.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorResponse(
    @JsonProperty("http_code")
    val httpCode: Int,
    val message: String,
    @JsonProperty("internal_code")
    val internalCode: String,
    val errors: List<FieldErrorResponse>?
)
