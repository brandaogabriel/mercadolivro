package com.devgabriel.mercadolivro.model

import java.util.*


data class Customer(
    val id: String = UUID.randomUUID().toString(),
    var name: String,
    var email: String,
)