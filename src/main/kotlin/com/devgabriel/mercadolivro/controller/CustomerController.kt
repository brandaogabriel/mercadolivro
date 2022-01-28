package com.devgabriel.mercadolivro.controller

import com.devgabriel.mercadolivro.controller.request.PostCustomerRequest
import com.devgabriel.mercadolivro.controller.request.PutCustomerRequest
import com.devgabriel.mercadolivro.controller.request.toCustomerModel
import com.devgabriel.mercadolivro.controller.response.CustomerResponse
import com.devgabriel.mercadolivro.model.Customer
import com.devgabriel.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/customers")
class CustomerController(
    private val customerService: CustomerService
) {

    @GetMapping(produces = ["application/json"])
    fun getCustomers(
        @RequestParam name: String?
    ): ResponseEntity<List<Customer>> {
        return ResponseEntity.ok(customerService.getCustomers(name))
    }

    @GetMapping("/{id}", produces = ["application/json"])
    fun getCustomerById(@PathVariable id: Long): ResponseEntity<CustomerResponse> {
        val customer = customerService.getCustomerById(id)
        return ResponseEntity.ok(
            CustomerResponse(
                name = customer.name,
                email = customer.email
            )
        )
    }

    @PostMapping(
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun create(@RequestBody request: PostCustomerRequest): ResponseEntity<Unit> {
        customerService.create(request.toCustomerModel())
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }

    @PutMapping("/{id}")
    fun updateCustomerById(
        @PathVariable id: Long,
        @RequestBody request: PutCustomerRequest
    ): ResponseEntity<CustomerResponse> {
        val customer = request.toCustomerModel(id)
        customerService.updateCustomerById(customer)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    fun deleteCustomerById(
        @PathVariable id: Long,
    ): ResponseEntity<CustomerResponse> {
        customerService.deleteCustomerById(id)
        return ResponseEntity.noContent().build()
    }
}