package com.devgabriel.mercadolivro.controller

import com.devgabriel.mercadolivro.controller.request.PostCustomerRequest
import com.devgabriel.mercadolivro.controller.request.PutCustomerRequest
import com.devgabriel.mercadolivro.enums.CustomerStatus
import com.devgabriel.mercadolivro.repository.CustomerRepository
import com.devgabriel.mercadolivro.security.UserCustomerDetails
import com.devgabriel.mercadolivro.service.helper.buildCustomer
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.random.Random

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles("test")
@WithMockUser(username = "1", password = "pwd", roles = ["ADMIN"])
class CustomerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        customerRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
        customerRepository.deleteAll()
    }

    @Test
    fun `should return all customers`() {
        val customer1 = customerRepository.save(buildCustomer())
        val customer2 = customerRepository.save(buildCustomer())

        mockMvc.perform(get("/api/v1/customers"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value(customer1.id))
            .andExpect(jsonPath("$[0].name").value(customer1.name))
            .andExpect(jsonPath("$[0].email").value(customer1.email))
            .andExpect(jsonPath("$[0].status").value(customer1.status.name))
            .andExpect(jsonPath("$[1].id").value(customer2.id))
            .andExpect(jsonPath("$[1].name").value(customer2.name))
            .andExpect(jsonPath("$[1].email").value(customer2.email))
            .andExpect(jsonPath("$[1].status").value(customer2.status.name))
    }

    @Test
    fun `should filter all customers by name when get all`() {
        val customer1 = customerRepository.save(buildCustomer(name = "Gabriel"))
        customerRepository.save(buildCustomer())

        mockMvc.perform(get("/api/v1/customers?name=Gab"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].id").value(customer1.id))
            .andExpect(jsonPath("$[0].name").value(customer1.name))
            .andExpect(jsonPath("$[0].email").value(customer1.email))
            .andExpect(jsonPath("$[0].status").value(customer1.status.name))
    }

    @Test
    fun `should create customer`() {
        val request = PostCustomerRequest(name = "fake email", email = "${Random.nextInt()}@fake.com.br", password = "123456")

        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated)

        val customers = customerRepository.findAll().toList()
        assertEquals(1, customers.size)
        assertEquals(request.name, customers[0].name)
        assertEquals(request.email, customers[0].email)
    }

    @Test
    fun `should throw error when create customer has invalid information`() {
        val request = PostCustomerRequest(name = "", email = "${Random.nextInt()}@fake.com.br", password = "123456")

        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.http_code").value(422))
            .andExpect(jsonPath("$.message").value("Invalid Request"))
            .andExpect(jsonPath("$.internal_code").value("ML_001"))
    }

    @Test
    @WithMockUser(roles = ["CUSTOMER"])
    fun `should get user by id when user has the same id`() {
        val customer = customerRepository.save(buildCustomer())

        mockMvc.perform(get("/api/v1/customers/${customer.id}").with(user(UserCustomerDetails(customer))))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(customer.id))
            .andExpect(jsonPath("$.name").value(customer.name))
            .andExpect(jsonPath("$.email").value(customer.email))
            .andExpect(jsonPath("$.status").value(customer.status.name))
    }

    @Test
    fun `should get user by id when user is ADMIN`() {
        val customer = customerRepository.save(buildCustomer())

        mockMvc.perform(get("/api/v1/customers/${customer.id}").with(user(UserCustomerDetails(customer))))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(customer.id))
            .andExpect(jsonPath("$.name").value(customer.name))
            .andExpect(jsonPath("$.email").value(customer.email))
            .andExpect(jsonPath("$.status").value(customer.status.name))
    }

    @Test
    fun `should update customer`() {
        val customer = customerRepository.save(buildCustomer())
        val request =  PutCustomerRequest(name = "Jacob", email = "email@jacob.com")

        mockMvc.perform(put("/api/v1/customers/${customer.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNoContent)

        val customers = customerRepository.findAll().toList()
        assertEquals(1, customers.size)
        assertEquals(request.name, customers[0].name)
        assertEquals(request.email, customers[0].email)
    }

    @Test
    fun `should throw error when update customer has invalid information`() {
        val request =  PutCustomerRequest(name = "", email = "email@jacob.com")

        mockMvc.perform(put("/api/v1/customers/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.http_code").value(422))
            .andExpect(jsonPath("$.message").value("Invalid Request"))
            .andExpect(jsonPath("$.internal_code").value("ML_001"))
    }

    @Test
    fun `should delete customer`() {
        val customer = customerRepository.save(buildCustomer())

        mockMvc.perform(delete("/api/v1/customers/${customer.id}"))
            .andExpect(status().isNoContent)

        val customerDeleted = customerRepository.findById(customer.id!!)
        assertEquals(CustomerStatus.INATIVO, customerDeleted.get().status)
    }

    @Test
    fun `should return not found when delete customer not exists`() {
        mockMvc.perform(delete("/api/v1/customers/1"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.http_code").value(404))
            .andExpect(jsonPath("$.message").value("Customer id:[1] not found"))
            .andExpect(jsonPath("$.internal_code").value("ML_201"))
    }
}