package com.devgabriel.mercadolivro.controller.request

data class LoginRequest(
    val email: String,
    val password: String,
)