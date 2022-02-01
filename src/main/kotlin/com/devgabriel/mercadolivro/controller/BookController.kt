package com.devgabriel.mercadolivro.controller

import com.devgabriel.mercadolivro.controller.request.PostBookRequest
import com.devgabriel.mercadolivro.controller.request.toBookModel
import com.devgabriel.mercadolivro.service.BookService
import com.devgabriel.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/books")
class BookController(
    private val bookService: BookService,
    private val customerService: CustomerService
) {

    @PostMapping(consumes = ["application/json"])
    fun create(@RequestBody request: PostBookRequest): ResponseEntity<Unit> {
        val customer = customerService.getCustomerById(request.customerId)
        val book = request.toBookModel(customer)
        bookService.create(book)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }
}