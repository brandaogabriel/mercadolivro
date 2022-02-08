package com.devgabriel.mercadolivro.service

import com.devgabriel.mercadolivro.model.Customer

interface CustomerService {

    fun getCustomers(name: String?): List<Customer>
    fun getCustomerById(id: Long): Customer
    fun create(customer: Customer)
    fun updateCustomerById(customer: Customer)
    fun deleteCustomerById(id: Long)
    fun emailAvailable(email: String): Boolean
}