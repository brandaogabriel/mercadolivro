package com.devgabriel.mercadolivro.service

import com.devgabriel.mercadolivro.events.PurchaseEvent
import com.devgabriel.mercadolivro.model.Purchase
import com.devgabriel.mercadolivro.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseServiceImpl(
    private val purchaseRepository: PurchaseRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : PurchaseService {

    override fun create(purchase: Purchase) {
        purchaseRepository.save(purchase)
        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchase))
    }
}