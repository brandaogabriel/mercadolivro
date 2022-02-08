package com.devgabriel.mercadolivro.events

import com.devgabriel.mercadolivro.model.Purchase
import org.springframework.context.ApplicationEvent

class PurchaseEvent(
    source: Any,
    val purchase: Purchase
) : ApplicationEvent(source)