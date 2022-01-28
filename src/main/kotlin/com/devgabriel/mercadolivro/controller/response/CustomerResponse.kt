package com.devgabriel.mercadolivro.controller.response

import com.devgabriel.mercadolivro.model.Customer

data class CustomerResponse(
    val name: String,
    val email: String,
)

fun CustomerResponse.toCustomerResponse(customer: Customer): CustomerResponse {
    return CustomerResponse(
        name = customer.name,
        email = customer.email
    )
}