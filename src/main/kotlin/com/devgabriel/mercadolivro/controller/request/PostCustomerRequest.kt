package com.devgabriel.mercadolivro.controller.request

import com.devgabriel.mercadolivro.enums.CustomerStatus
import com.devgabriel.mercadolivro.model.Customer

data class PostCustomerRequest(
    val name: String,
    val email: String,
)

fun PostCustomerRequest.toCustomerModel(): Customer {
    return Customer(
        name = name,
        email = email,
        status = CustomerStatus.ATIVO
    )
}