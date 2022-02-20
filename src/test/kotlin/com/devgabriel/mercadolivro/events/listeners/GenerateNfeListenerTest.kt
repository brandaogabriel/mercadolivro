package com.devgabriel.mercadolivro.events.listeners

import com.devgabriel.mercadolivro.events.PurchaseEvent
import com.devgabriel.mercadolivro.service.PurchaseService
import com.devgabriel.mercadolivro.service.helper.buildPurchase
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class GenerateNfeListenerTest {

    @MockK
    private lateinit var purchaseService: PurchaseService

    @InjectMockKs
    private lateinit var generateNfeListener: GenerateNfeListener

    @Test
    fun `should generate nfe for purchase`() {
        //GIVEN
        val purchase = buildPurchase(nfe = null)
        val fakeNfe = UUID.randomUUID()
        val expectedPurchase = purchase.copy(nfe = fakeNfe.toString())

        //Mock any static class
        mockkStatic(UUID::class)

        every { UUID.randomUUID() } returns fakeNfe
        every { purchaseService.update(expectedPurchase) } just runs

        //WHEN
        generateNfeListener.generateNfe(PurchaseEvent(this, purchase))

        //THEN
        verify(exactly = 1) { purchaseService.update(expectedPurchase) }
    }
}