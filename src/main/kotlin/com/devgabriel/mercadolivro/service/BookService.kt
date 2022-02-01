package com.devgabriel.mercadolivro.service

import com.devgabriel.mercadolivro.model.Book

interface BookService {
    fun create(book: Book)
}
