package com.devgabriel.mercadolivro.controller.request

import com.devgabriel.mercadolivro.model.Customer

data class PutCustomerRequest(
    val name: String,
    val email: String,
)

fun PutCustomerRequest.toCustomerModel(previousValue: Customer): Customer {
    return Customer(
        id = previousValue.id,
        name = name,
        email = email,
        status = previousValue.status
    )
}