package com.devgabriel.mercadolivro.repository

import com.devgabriel.mercadolivro.service.helper.buildCustomer
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerRepositoryTest {

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun setUp() {
        customerRepository.deleteAll()
    }

    @Test
    fun `should return name containing`() {
        //GIVEN
        val gabriel = customerRepository.save(buildCustomer(name = "Gabriel"))
        val gabriela = customerRepository.save(buildCustomer(name = "Gabriela"))
        customerRepository.save(buildCustomer(name = "Carlos"))

        //WHEN
        val result = customerRepository.findByNameContaining("Ga")

        //THEN
        assertEquals(listOf(gabriel, gabriela), result)
    }

    @Nested
    inner class `exists by email` {

        @Test
        fun `should return true when email exits`() {
            //GIVEN
            val email = "email@teste.com"
            customerRepository.save(buildCustomer(email = email))

            //WHEN
            val result = customerRepository.existsByEmail(email)

            //THEN
            assertTrue(result)
        }

        @Test
        internal fun `should return false when email do not exist`() {
            //GIVEN
            val email = "email_naoexiste@email.com"

            //WHEN
            val result = customerRepository.existsByEmail(email)

            //THEN
            assertFalse(result)
        }
    }

    @Nested
    inner class `find by email` {

        @Test
        fun `should return customer when email exists`() {
            //GIVEN
            val email = "email@teste.com"
            customerRepository.save(buildCustomer(email = email))

            //WHEN
            val result = customerRepository.findByEmail(email)

            //THEN
            assertNotNull(result)
        }

        @Test
        fun `should return null when email do not exist`() {
            //GIVEN
            val email = "email_naoexiste@email.com"

            //WHEN
            val result = customerRepository.findByEmail(email)

            //THEN
            assertNull(result)
        }
    }
}