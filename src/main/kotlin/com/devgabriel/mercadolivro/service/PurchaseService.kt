package com.devgabriel.mercadolivro.service

import com.devgabriel.mercadolivro.model.Purchase

interface PurchaseService {
    fun create(purchase: Purchase)
    fun update(purchaseWithNfe: Purchase)
}
