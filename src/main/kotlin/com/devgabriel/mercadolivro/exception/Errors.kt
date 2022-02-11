package com.devgabriel.mercadolivro.exception

enum class Errors(val code: String, val message: String) {
    ML_000("ML_000", "Access Denied"),
    ML_001("ML_001", "Invalid Request"),
    ML_101("ML_101", "Book id:[%s] not found"),
    ML_102("ML_102", "Cannot update book id:[%s] with status [%s]"),
    ML_201("ML_201", "Customer id:[%s] not found")
}