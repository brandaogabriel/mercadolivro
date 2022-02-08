package com.devgabriel.mercadolivro.controller.mapper

import com.devgabriel.mercadolivro.controller.request.PostPurchaseRequest
import com.devgabriel.mercadolivro.model.Purchase
import com.devgabriel.mercadolivro.service.BookService
import com.devgabriel.mercadolivro.service.CustomerService
import org.springframework.stereotype.Component

@Component
class PurchaseMapper(
    private val customerService: CustomerService,
    private val bookService: BookService,
) {

    fun toModel(request: PostPurchaseRequest): Purchase {
        val customer = customerService.getCustomerById(request.customerId)
        val books = bookService.findAllById(request.booksId)
        return Purchase(
            customer = customer,
            books = books,
            price = books.sumOf { it.price })
    }

}