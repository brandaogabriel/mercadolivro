package com.devgabriel.mercadolivro.service

import com.devgabriel.mercadolivro.model.Customer
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CustomerService {

    val customers = mutableListOf<Customer>()

    fun getCustomers(name: String?): List<Customer> {
        name?.let {
            return customers.filter { it.name.contains(name, ignoreCase = true) }
        }

        return customers
    }
}