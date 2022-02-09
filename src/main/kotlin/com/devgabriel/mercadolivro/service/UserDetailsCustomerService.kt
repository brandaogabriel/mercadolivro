package com.devgabriel.mercadolivro.service

import com.devgabriel.mercadolivro.exception.AuthenticationException
import com.devgabriel.mercadolivro.repository.CustomerRepository
import com.devgabriel.mercadolivro.security.UserCustomerDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsCustomerService(
    private val customerRepository: CustomerRepository
) : UserDetailsService {

    override fun loadUserByUsername(id: String): UserDetails {
        val customer = customerRepository.findById(id.toLong())
            .orElseThrow { AuthenticationException("Usuário não encontrado", "999") }
        return UserCustomerDetails(customer)
    }
}