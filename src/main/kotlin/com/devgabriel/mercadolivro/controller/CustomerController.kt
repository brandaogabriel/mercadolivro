package com.devgabriel.mercadolivro.controller

import com.devgabriel.mercadolivro.common.util.toCustomerModel
import com.devgabriel.mercadolivro.common.util.toCustomerResponse
import com.devgabriel.mercadolivro.controller.request.PostCustomerRequest
import com.devgabriel.mercadolivro.controller.request.PutCustomerRequest
import com.devgabriel.mercadolivro.controller.response.CustomerResponse
import com.devgabriel.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/api/v1/customers")
class CustomerController(
    private val customerService: CustomerService
) {

    @GetMapping(produces = ["application/json"])
    fun getCustomers(
        @RequestParam name: String?
    ): ResponseEntity<List<CustomerResponse>> {
        return ResponseEntity
            .ok(customerService.getCustomers(name).map { it.toCustomerResponse() })
    }

    @GetMapping("/{id}", produces = ["application/json"])
    fun getCustomerById(@PathVariable id: Long): ResponseEntity<CustomerResponse> {
        val customer = customerService.getCustomerById(id)
        return ResponseEntity.ok(customer.toCustomerResponse())
    }

    @PostMapping(
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun create(@RequestBody @Valid request: PostCustomerRequest): ResponseEntity<Unit> {
        customerService.create(request.toCustomerModel())
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }

    @PutMapping("/{id}")
    fun updateCustomerById(
        @PathVariable id: Long,
        @RequestBody @Valid request: PutCustomerRequest
    ): ResponseEntity<Unit> {
        val savedCustomer = customerService.getCustomerById(id)
        customerService.updateCustomerById(request.toCustomerModel(savedCustomer))
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    fun deleteCustomerById(
        @PathVariable id: Long,
    ): ResponseEntity<Unit> {
        customerService.deleteCustomerById(id)
        return ResponseEntity.noContent().build()
    }
}