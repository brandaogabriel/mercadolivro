package com.devgabriel.mercadolivro.service

import com.devgabriel.mercadolivro.model.Customer

interface CustomerService {

    fun getCustomers(name: String?): List<Customer>
    fun getCustomerById(id: String): Customer
    fun create(customer: Customer)
    fun updateCustomerById(id: String, customer: Customer)
    fun deleteCustomerById(id: String)
}