package com.devgabriel.mercadolivro.controller

import com.devgabriel.mercadolivro.controller.request.PostPurchaseRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/purchases")
class PurchaseController {

    @PostMapping
    fun purchase(@RequestBody @Valid request: PostPurchaseRequest): Unit {

    }

}