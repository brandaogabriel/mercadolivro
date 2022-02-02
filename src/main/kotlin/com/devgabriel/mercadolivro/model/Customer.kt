package com.devgabriel.mercadolivro.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity(name = "tb_customer")
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var name: String,
    var email: String,
) {

    @OneToMany(mappedBy = "customer", cascade = [CascadeType.REMOVE])
    @JsonIgnore
    val books = mutableListOf<Book>()
}