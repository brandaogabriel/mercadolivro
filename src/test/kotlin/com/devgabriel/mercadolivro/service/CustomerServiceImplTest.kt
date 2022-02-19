package com.devgabriel.mercadolivro.service

import com.devgabriel.mercadolivro.enums.CustomerStatus
import com.devgabriel.mercadolivro.enums.Role
import com.devgabriel.mercadolivro.exception.Errors
import com.devgabriel.mercadolivro.exception.NotFoundException
import com.devgabriel.mercadolivro.model.Customer
import com.devgabriel.mercadolivro.repository.CustomerRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@ExtendWith(MockKExtension::class)
class CustomerServiceImplTest {

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @MockK
    private lateinit var bookService: BookService

    @MockK
    private lateinit var bcrypt: BCryptPasswordEncoder

    @InjectMockKs
    @SpyK //When we need to mock functions of the class
    private lateinit var customerService: CustomerServiceImpl

    @Test
    fun `should return all customers`() {
        //GIVEN
        val fakeCustomers = listOf(buildCustomer(), buildCustomer())
        every { customerRepository.findAll() } returns fakeCustomers

        //WHEN
        val customers = customerService.getCustomers(null)

        //THEN
        assertEquals(fakeCustomers, customers)
        verify(exactly = 1) { customerRepository.findAll() }
        verify(exactly = 0) { customerRepository.findByNameContaining(any()) }
    }

    @Test
    fun `should return all customers when name is informed`() {
        //GIVEN
        val name = UUID.randomUUID().toString()

        val fakeCustomers = listOf(buildCustomer(), buildCustomer())
        every { customerRepository.findByNameContaining(name) } returns fakeCustomers

        //WHEN
        val customers = customerService.getCustomers(name)

        //THEN
        assertEquals(fakeCustomers, customers)
        verify(exactly = 1) { customerRepository.findByNameContaining(name) }
        verify(exactly = 0) { customerRepository.findAll() }
    }

    @Test
    fun `should create a customer with encrypted password`() {
        //GIVEN
        val initialPassword = Math.random().toString()
        val encryptedPassword = UUID.randomUUID().toString()
        val fakeCustomer = buildCustomer(password = initialPassword)
        val fakeCustomerWithEncryptedPassword = fakeCustomer.copy(password = encryptedPassword)

        every { customerRepository.save(fakeCustomerWithEncryptedPassword) } returns fakeCustomerWithEncryptedPassword
        every { bcrypt.encode(initialPassword) } returns encryptedPassword

        //WHEN
        customerService.create(fakeCustomer)

        //THEN
        verify(exactly = 1) {
            customerRepository.save(fakeCustomerWithEncryptedPassword)
            bcrypt.encode(initialPassword)
        }

        verifyOrder {
            bcrypt.encode(initialPassword)
            customerRepository.save(fakeCustomerWithEncryptedPassword)
        }
    }

    @Test
    fun `should return a customer by id`() {
        //GIVEN
        val id = Random().nextLong()
        val fakeCustomer = buildCustomer(id = id)

        every { customerRepository.findById(id) } returns Optional.of(fakeCustomer)

        //WHEN
        val customer = customerService.getCustomerById(id)

        //THEN
        assertEquals(id, customer.id)
        verify(exactly = 1) { customerRepository.findById(id) }
    }

    @Test
    fun `should throw NotFoundException when get customer by id not found`() {
        //GIVEN
        val id = Random().nextLong()

        every { customerRepository.findById(id) } returns Optional.empty()

        //WHEN
        val exception = assertThrows<NotFoundException> {
            customerService.getCustomerById(id)
        }

        //THEN
        assertEquals("Customer id:[${id}] not found", exception.message)
        assertEquals("ML_201", exception.errorCode)
        verify(exactly = 1) { customerRepository.findById(id) }
    }

    @Test
    fun `should update a customer`() {
        //GIVEN
        val newEmail = "${UUID.randomUUID()}_novoemail@email.com.br"
        val fakeCustomer = buildCustomer(email = newEmail)

        every { customerRepository.save(fakeCustomer) } returns fakeCustomer

        //WHEN
        customerService.updateCustomerById(fakeCustomer)

        //THEN
        verify(exactly = 1) { customerRepository.save(fakeCustomer) }
    }

    @Test
    fun `should delete a customer by id changing his status to INATIVO`() {
        //GIVEN
        val id = Random().nextLong()
        val fakeCustomer = buildCustomer(id = id)

        every { customerService.getCustomerById(id) } returns fakeCustomer
        every { bookService.deleteByCustomer(fakeCustomer) } just runs
        every { customerRepository.save(fakeCustomer) } returns fakeCustomer

        //WHEN
        customerService.deleteCustomerById(id)

        //THEN
        assertEquals(CustomerStatus.INATIVO, fakeCustomer.status)
        verify(exactly = 1) {
            customerService.getCustomerById(id)
            bookService.deleteByCustomer(fakeCustomer)
            customerRepository.save(fakeCustomer)
        }
    }

    @Test
    fun `should throw NotFoundException when trying to delete a customer by id`() {
        //GIVEN
        val id = Random().nextLong()

        every { customerService.getCustomerById(id) } throws NotFoundException(
            Errors.ML_201.message.format(id),
            Errors.ML_201.code
        )

        //WHEN
        val exception = assertThrows<NotFoundException> {
            customerService.deleteCustomerById(id)
        }

        //THEN
        assertEquals("Customer id:[${id}] not found", exception.message)
        assertEquals("ML_201", exception.errorCode)
        verify(exactly = 1) { customerService.getCustomerById(id) }
    }

    @Test
    fun `should return true when email available`() {
        //GIVEN
        val email = "${UUID.randomUUID()}@email.com"

        every { customerRepository.existsByEmail(email) } returns false

        //WHEN
        val result = customerService.emailAvailable(email)

        //THEN
        assertTrue(result)
        verify(exactly = 1) {
            customerRepository.existsByEmail(email)
        }
    }

    @Test
    fun `should return false when email not available`() {
        //GIVEN
        val email = "${UUID.randomUUID()}@email.com"

        every { customerRepository.existsByEmail(email) } returns true

        //WHEN
        val result = customerService.emailAvailable(email)

        //THEN
        assertFalse(result)
        verify(exactly = 1) {
            customerRepository.existsByEmail(email)
        }
    }

    private fun buildCustomer(
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
}