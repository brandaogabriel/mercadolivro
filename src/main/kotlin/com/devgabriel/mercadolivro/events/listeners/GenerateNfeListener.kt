package com.devgabriel.mercadolivro.events.listeners

import com.devgabriel.mercadolivro.events.PurchaseEvent
import com.devgabriel.mercadolivro.service.PurchaseService
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.util.*

@Component
class GenerateNfeListener(
    private val purchaseService: PurchaseService
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @EventListener
    fun generateNfe(purchaseEvent: PurchaseEvent) {
        logger.info("[${javaClass.simpleName}.generateNfe] start - purchase: {}", purchaseEvent.purchase)

        val purchase = purchaseEvent.purchase
        purchase.nfe = UUID.randomUUID().toString()
        purchaseService.update(purchase)
        logger.debug("[${javaClass.simpleName}.generateNfe] call success - nfe: {} generated", purchase.nfe)
    }
}