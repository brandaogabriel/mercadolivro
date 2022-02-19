package com.devgabriel.mercadolivro.service

import com.devgabriel.mercadolivro.events.PurchaseEvent
import com.devgabriel.mercadolivro.repository.PurchaseRepository
import com.devgabriel.mercadolivro.service.helper.buildPurchase
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.ApplicationEventPublisher

@ExtendWith(MockKExtension::class)
class PurchaseServiceImplTest {

    @MockK
    private lateinit var purchaseRepository: PurchaseRepository

    @MockK
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @InjectMockKs
    private lateinit var purchaseServiceImpl: PurchaseServiceImpl

    //Used when we need to create a specific slot to be completed in an event
    //Fill an object during runtime
    private val purchaseEventSlot = slot<PurchaseEvent>()

    @Test
    fun `should create purchase and publish event`() {
        //GIVEN
        val purchase = buildPurchase()

        every { purchaseRepository.save(purchase) } returns purchase
        every { applicationEventPublisher.publishEvent(any()) } just runs

        //WHEN
        purchaseServiceImpl.create(purchase)

        //THEN
        verify(exactly = 1) {
            purchaseRepository.save(purchase)
            applicationEventPublisher.publishEvent(capture(purchaseEventSlot))
        }

        assertEquals(purchase, purchaseEventSlot.captured.purchase)
    }

    @Test
    fun `should update a purchase`() {
        //GIVEN
        val purchase = buildPurchase()

        every { purchaseRepository.save(purchase) } returns purchase

        //WHEN
        purchaseServiceImpl.update(purchase)

        //THEN
        verify(exactly = 1) {
            purchaseRepository.save(purchase)
        }
    }
}