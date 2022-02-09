package com.devgabriel.mercadolivro.model

import com.devgabriel.mercadolivro.enums.CustomerStatus
import javax.persistence.*

@Entity(name = "tb_customer")
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var name: String,
    var email: String,
    @Enumerated(EnumType.STRING)
    var status: CustomerStatus,
    val password: String
)