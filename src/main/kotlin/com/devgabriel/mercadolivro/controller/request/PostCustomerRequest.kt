package com.devgabriel.mercadolivro.controller.request

import com.devgabriel.mercadolivro.validation.EmailAvailable
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank


data class PostCustomerRequest(
    @field:NotBlank(message = "Nome deve ser informado")
    val name: String,
    @field:Email(message = "E-mail deve ser válido")
    @field:EmailAvailable
    val email: String,
)

