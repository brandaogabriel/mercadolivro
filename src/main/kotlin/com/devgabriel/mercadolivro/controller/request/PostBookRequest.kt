package com.devgabriel.mercadolivro.controller.request

import com.devgabriel.mercadolivro.enums.BookStatus
import com.devgabriel.mercadolivro.model.Book
import com.devgabriel.mercadolivro.model.Customer
import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal

data class PostBookRequest(
    val name: String,
    val price: BigDecimal,
    @JsonAlias("customer_id")
    val customerId: Long
)

fun PostBookRequest.toBookModel(customer: Customer): Book {
    return Book(
        name = name,
        price = price,
        status = BookStatus.ATIVO,
        customer = customer
    )
}