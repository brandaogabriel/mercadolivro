package com.devgabriel.mercadolivro.controller.request

import com.devgabriel.mercadolivro.model.Customer

data class PutCustomerRequest(
    val name: String,
    val email: String,
)

fun PutCustomerRequest.toCustomerModel(id: String): Customer {
    return Customer(
        id = id,
        name = name,
        email = email
    )
}