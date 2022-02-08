package com.devgabriel.mercadolivro.controller.request

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PostPurchaseRequest(
    @field:NotEmpty
    val customerId: Long,

    @field:NotNull
    val booksId: Set<Long>
)
