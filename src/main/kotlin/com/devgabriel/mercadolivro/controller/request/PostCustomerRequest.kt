package com.devgabriel.mercadolivro.controller.request

import com.devgabriel.mercadolivro.model.Customer

data class PostCostumerRequest(
    val name: String,
    val email: String,
)

fun PostCostumerRequest.toCustomerModel(): Customer {
    return Customer(
        name = name,
        email = email
    )
}