package com.devgabriel.mercadolivro.controller

import com.devgabriel.mercadolivro.common.util.toBookModel
import com.devgabriel.mercadolivro.common.util.toBookResponse
import com.devgabriel.mercadolivro.controller.request.PostBookRequest
import com.devgabriel.mercadolivro.controller.request.PutBookRequest
import com.devgabriel.mercadolivro.controller.response.BookResponse
import com.devgabriel.mercadolivro.service.BookService
import com.devgabriel.mercadolivro.service.CustomerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    @GetMapping
    fun findAll(
        @PageableDefault(page = 0, size = 10) pageable: Pageable
    ): ResponseEntity<Page<BookResponse>> {
        return ResponseEntity.ok(bookService.findAll(pageable).map { it.toBookResponse() })
    }

    @GetMapping("/actives", produces = ["application/json"])
    fun findAllActives(@PageableDefault(page = 0, size = 10) pageable: Pageable): ResponseEntity<Page<BookResponse>> {
        return ResponseEntity.ok(bookService.findActives(pageable).map { it.toBookResponse() })
    }

    @GetMapping("/{id}", produces = ["application/json"])
    fun findById(@PathVariable id: Long): ResponseEntity<BookResponse> {
        return ResponseEntity.ok(bookService.findById(id).toBookResponse())
    }

    @PutMapping("/{id}", consumes = ["application/json"])
    fun update(@PathVariable id: Long, @RequestBody request: PutBookRequest): ResponseEntity<Unit> {
        val book = bookService.findById(id)
        bookService.update(request.toBookModel(book))
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        bookService.delete(id)
        return ResponseEntity.noContent().build()
    }
}