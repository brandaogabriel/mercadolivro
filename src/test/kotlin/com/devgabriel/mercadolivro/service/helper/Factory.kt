package com.devgabriel.mercadolivro.service.helper

import com.devgabriel.mercadolivro.enums.CustomerStatus
import com.devgabriel.mercadolivro.enums.Role
import com.devgabriel.mercadolivro.model.Book
import com.devgabriel.mercadolivro.model.Customer
import com.devgabriel.mercadolivro.model.Purchase
import java.math.BigDecimal
import java.util.*

fun buildCustomer(
    id: Long? = null,
    name: String = "customer name",
    email: String = "${UUID.randomUUID()}@email.com",
    password: String = "password"
) = Customer(
    id = id,
    name = name,
    email = email,
    password = password,
    status = CustomerStatus.ATIVO,
    roles = setOf(Role.CUSTOMER)
)

fun buildPurchase(
    id: Long? = null,
    customer: Customer = buildCustomer(),
    books: MutableList<Book> = mutableListOf(),
    nfe: String? = UUID.randomUUID().toString(),
    price: BigDecimal = BigDecimal.TEN
) = Purchase(
    id = id,
    customer = customer,
    books = books,
    nfe = nfe,
    price = price
)