package com.devgabriel.mercadolivro.service

import com.devgabriel.mercadolivro.model.Customer
import com.devgabriel.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
    private val bookService: BookService
) : CustomerService {

    override fun getCustomers(name: String?): List<Customer> {
        name?.let {
            return customerRepository.findByNameContaining(it)
        }

        return customerRepository.findAll()
    }

    override fun getCustomerById(id: Long): Customer {
        return customerRepository.findById(id).orElseThrow()
    }

    override fun create(customer: Customer) {
        customerRepository.save(customer)
    }

    override fun updateCustomerById(customer: Customer) {
        if (!customerRepository.existsById(customer.id!!)) {
            throw Exception()
        }

        customerRepository.save(customer)
    }

    override fun deleteCustomerById(id: Long) {
        val customer = getCustomerById(id)
        bookService.deleteByCustomer(customer)
        if (!customerRepository.existsById(id)) {
            throw Exception()
        }
        customerRepository.deleteById(id)
    }
}