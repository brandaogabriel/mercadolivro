package com.devgabriel.mercadolivro.common.util

import com.devgabriel.mercadolivro.controller.request.PostBookRequest
import com.devgabriel.mercadolivro.controller.request.PostCustomerRequest
import com.devgabriel.mercadolivro.controller.request.PutBookRequest
import com.devgabriel.mercadolivro.controller.request.PutCustomerRequest
import com.devgabriel.mercadolivro.controller.response.BookResponse
import com.devgabriel.mercadolivro.controller.response.CustomerResponse
import com.devgabriel.mercadolivro.controller.response.PageResponse
import com.devgabriel.mercadolivro.enums.BookStatus
import com.devgabriel.mercadolivro.enums.CustomerStatus
import com.devgabriel.mercadolivro.model.Book
import com.devgabriel.mercadolivro.model.Customer
import org.springframework.data.domain.Page

fun PostBookRequest.toBookModel(customer: Customer): Book {
    return Book(
        name = name,
        price = price,
        status = BookStatus.ATIVO,
        customer = customer
    )
}

fun PostCustomerRequest.toCustomerModel(): Customer {
    return Customer(
        name = name,
        email = email,
        status = CustomerStatus.ATIVO,
        password = password
    )
}

fun PutBookRequest.toBookModel(previusValue: Book): Book {
    return Book(
        id = previusValue.id,
        name = this.name ?: previusValue.name,
        price = this.price ?: previusValue.price,
        status = previusValue.status,
        customer = previusValue.customer
    )
}

fun PutCustomerRequest.toCustomerModel(previousValue: Customer): Customer {
    return Customer(
        id = previousValue.id,
        name = name,
        email = email,
        status = previousValue.status,
        password = previousValue.password
    )
}

fun Customer.toCustomerResponse(): CustomerResponse {
    return CustomerResponse(
        id = this.id,
        name = this.name,
        email = this.email,
        status = this.status
    )
}

fun Book.toBookResponse(): BookResponse =
    BookResponse(
        id = this.id,
        name = this.name,
        price = this.price,
        customer = this.customer?.toCustomerResponse(),
        status = this.status
    )

fun <T> Page<T>.toPageResponse(): PageResponse<T> =
    PageResponse(this.content, this.number, this.totalElements, this.totalPages)