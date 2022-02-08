package com.devgabriel.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import javax.validation.constraints.*

data class PostBookRequest(
    @field:NotBlank(message = "Nome deve ser informado")
    val name: String,
    @field:NotNull(message = "Preço deve ser informado")
    val price: BigDecimal,

    @JsonAlias("customer_id")
    @field:NotNull(message = "Customer id deve ser informado")
    val customerId: Long
)
