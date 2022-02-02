package com.devgabriel.mercadolivro.controller.request

import com.devgabriel.mercadolivro.model.Book
import java.math.BigDecimal

data class PutBookRequest(
    val name: String?,
    val price: BigDecimal?
)

fun PutBookRequest.toBookModel(previusValue: Book): Book {
    return Book(
        id = previusValue.id,
        name = this.name ?: previusValue.name,
        price = this.price ?: previusValue.price,
        status = previusValue.status,
        customer = previusValue.customer
    )
}
