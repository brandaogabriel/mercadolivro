package com.devgabriel.mercadolivro.controller

import com.devgabriel.mercadolivro.controller.mapper.PurchaseMapper
import com.devgabriel.mercadolivro.controller.request.PostPurchaseRequest
import com.devgabriel.mercadolivro.service.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/purchase")
class PurchaseController(
    private val purchaseService: PurchaseService,
    private val purchaseMapper: PurchaseMapper
) {

    @PostMapping
    fun purchase(@RequestBody @Valid request: PostPurchaseRequest): ResponseEntity<Unit> {
        purchaseService.create(purchaseMapper.toModel(request))
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

}