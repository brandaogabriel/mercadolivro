package com.devgabriel.mercadolivro.repository

import com.devgabriel.mercadolivro.enums.BookStatus
import com.devgabriel.mercadolivro.model.Book
import com.devgabriel.mercadolivro.model.Customer
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Long> {
    fun findByStatus(status: BookStatus, pageable: Pageable): Page<Book>
    fun findByCustomer(customer: Customer): List<Book>
}
