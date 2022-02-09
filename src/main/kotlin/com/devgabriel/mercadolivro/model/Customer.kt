package com.devgabriel.mercadolivro.model

import com.devgabriel.mercadolivro.enums.CustomerStatus
import com.devgabriel.mercadolivro.enums.Role
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
    val password: String,

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_customer_roles", joinColumns = [JoinColumn(name = "customer_id")])
    val roles: Set<Role> = setOf()
)