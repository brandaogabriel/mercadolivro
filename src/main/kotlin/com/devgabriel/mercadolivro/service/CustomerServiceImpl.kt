package com.devgabriel.mercadolivro.service

import com.devgabriel.mercadolivro.model.Customer
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl : CustomerService {

    val customers = mutableListOf<Customer>()

    override fun getCustomers(name: String?): List<Customer> {
        name?.let {
            return customers.filter { it.name.contains(name, ignoreCase = true) }
        }

        return customers
    }

    override fun getCustomerById(id: String): Customer {
        return customers.find { it.id == id }!!
    }

    override fun create(customer: Customer) {
        customers.add(customer)
    }

    override fun updateCustomerById(id: String, customerUp: Customer) {
        val customer = customers.find { it.id == id }
        customer!!.name = customerUp.name
        customer.email = customerUp.email
    }

    override fun deleteCustomerById(id: String) {
        customers.removeIf { it.id == id }
    }
}