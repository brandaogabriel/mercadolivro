package com.devgabriel.mercadolivro.service

import com.devgabriel.mercadolivro.model.Book

interface BookService {
    fun create(book: Book)
    fun findAll(): List<Book>
    fun findActives(): List<Book>
    fun findById(id: Long): Book
    fun update(book: Book)
    fun delete(id: Long)
}
