package com.devgabriel.mercadolivro.service

import com.devgabriel.mercadolivro.model.Purchase
import com.devgabriel.mercadolivro.repository.PurchaseRepository
import org.springframework.stereotype.Service

@Service
class PurchaseServiceImpl(
    private val purchaseRepository: PurchaseRepository
) : PurchaseService {

    override fun create(purchase: Purchase) {
        purchaseRepository.save(purchase)
    }
}