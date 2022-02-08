package com.devgabriel.mercadolivro.controller.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class PutCustomerRequest(
    @field:NotBlank(message = "Nome deve ser informado")
    val name: String,
    @field:Email(message = "E-mail deve ser válido")
    val email: String,
)