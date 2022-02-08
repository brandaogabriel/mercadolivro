package com.devgabriel.mercadolivro.events.listeners

import com.devgabriel.mercadolivro.events.PurchaseEvent
import com.devgabriel.mercadolivro.service.BookService
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UpdateSoldBookListener(
    private val bookService: BookService
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Async
    @EventListener
    fun updateBookStatus(purchaseEvent: PurchaseEvent) {
        logger.info("[${javaClass.simpleName}.updateBookStatus] start - update: {}", purchaseEvent.purchase)

        bookService.updateSoldBooks(purchaseEvent.purchase.books)

        logger.debug("[${javaClass.simpleName}.updateBookStatus] call success - all books status has been updated")
    }
}