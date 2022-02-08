package com.devgabriel.mercadolivro.service

import com.devgabriel.mercadolivro.enums.CustomerStatus
import com.devgabriel.mercadolivro.exception.Errors
import com.devgabriel.mercadolivro.exception.NotFoundException
import com.devgabriel.mercadolivro.model.Customer
import com.devgabriel.mercadolivro.repository.CustomerRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
    private val bookService: BookService
) : CustomerService {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun getCustomers(name: String?): List<Customer> {
        name?.let {
            return customerRepository.findByNameContaining(it)
        }

        return customerRepository.findAll()
    }

    override fun getCustomerById(id: Long): Customer {
        logger.info("[${javaClass.simpleName}.getCustomerById] start customer id={}", id)
        val customer = customerRepository
            .findById(id)
            .orElseThrow {
                logger.debug("[${javaClass.simpleName}.getCustomerById] call - error - customer id: {} NOT FOUND", id)
                throw NotFoundException(Errors.ML_201.message.format(id), Errors.ML_201.code)
            }

        logger.debug("[${javaClass.simpleName}.getCustomerById] call - success - customer id: {}", id)
        return customer
    }

    override fun create(customer: Customer) {
        customerRepository.save(customer)
    }

    override fun updateCustomerById(customer: Customer) {
        logger.info("[${javaClass.simpleName}.updateCustomerById] start customer id={}", customer.id)

        customerRepository.save(customer)
        logger.debug("[${javaClass.simpleName}.updateCustomerById] call - success - customer id: {}", customer.id)
    }

    override fun deleteCustomerById(id: Long) {
        val customer = getCustomerById(id)
        bookService.deleteByCustomer(customer)

        customer.status = CustomerStatus.INATIVO
        customerRepository.save(customer)
    }

    override fun emailAvailable(email: String): Boolean {
        return !customerRepository.existsByEmail(email);
    }
}