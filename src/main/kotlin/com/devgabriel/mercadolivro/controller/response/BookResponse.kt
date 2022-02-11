package com.devgabriel.mercadolivro.controller.response

import com.devgabriel.mercadolivro.enums.BookStatus
import com.devgabriel.mercadolivro.model.Customer
import java.math.BigDecimal

data class BookResponse(
    val id: Long?,
    var name: String,
    var price: BigDecimal,
    val customer: CustomerResponse?,
    val status: BookStatus?
)
