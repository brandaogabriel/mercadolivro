package com.devgabriel.mercadolivro.service

import com.devgabriel.mercadolivro.model.Book
import com.devgabriel.mercadolivro.model.Customer
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BookService {
    fun create(book: Book)
    fun findAll(pageable: Pageable): Page<Book>
    fun findActives(pageable: Pageable): Page<Book>
    fun findById(id: Long): Book
    fun update(book: Book)
    fun delete(id: Long)
    fun deleteByCustomer(customer: Customer)
    fun findAllById(booksId: Set<Long>): List<Book>
    fun updateSoldBooks(booksToBeUpdated: MutableList<Book>)
}
