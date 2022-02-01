package com.devgabriel.mercadolivro.model

import com.devgabriel.mercadolivro.enums.BookStatus
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "tb_book")
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var name: String,
    val price: BigDecimal,

    @Enumerated(EnumType.STRING)
    val status: BookStatus? = null,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    val customer: Customer? = null,
)