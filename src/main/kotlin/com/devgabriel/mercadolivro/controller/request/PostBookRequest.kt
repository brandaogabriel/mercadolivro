package com.devgabriel.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class PostBookRequest(
    @field:NotBlank(message = "Nome deve ser informado")
    val name: String,
    @field:NotEmpty(message = "Preço deve ser informado")
    val price: BigDecimal,
    @field:NotEmpty(message = "Customer id deve ser informado")
    @JsonAlias("customer_id")
    val customerId: Long
)
