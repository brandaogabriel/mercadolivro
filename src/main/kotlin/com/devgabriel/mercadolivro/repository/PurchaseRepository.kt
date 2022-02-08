package com.devgabriel.mercadolivro.repository

import com.devgabriel.mercadolivro.model.Purchase
import org.springframework.data.jpa.repository.JpaRepository

interface PurchaseRepository : JpaRepository<Purchase, Long> {
}