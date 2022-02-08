package com.devgabriel.mercadolivro.controller.request

import com.devgabriel.mercadolivro.validation.BookAvailable
import com.fasterxml.jackson.annotation.JsonAlias
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class PostPurchaseRequest(
    @field:NotNull
    @field:Positive
    @JsonAlias("customer_id")
    val customerId: Long,

    @field:NotNull
    @field:BookAvailable
    @JsonAlias("books_id")
    val booksId: Set<Long>
)
