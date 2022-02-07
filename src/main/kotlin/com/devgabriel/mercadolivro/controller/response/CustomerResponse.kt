package com.devgabriel.mercadolivro.controller.response

import com.devgabriel.mercadolivro.enums.CustomerStatus

data class CustomerResponse(
    val id: Long?,
    val name: String,
    val email: String,
    val status: CustomerStatus
)
