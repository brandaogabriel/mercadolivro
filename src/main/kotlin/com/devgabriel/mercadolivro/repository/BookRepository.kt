package com.devgabriel.mercadolivro.repository

import com.devgabriel.mercadolivro.enums.BookStatus
import com.devgabriel.mercadolivro.model.Book
import com.devgabriel.mercadolivro.model.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Long> {
    fun findByStatus(status: BookStatus): List<Book>
    fun findByCustomer(customer: Customer): List<Book>
}
