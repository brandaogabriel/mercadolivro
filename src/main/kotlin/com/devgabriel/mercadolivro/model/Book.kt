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
    var price: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    val customer: Customer? = null,
) {

    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null
        set(value) {
            if (field == BookStatus.CANCELADO || field == BookStatus.DELETADO) {
                throw Exception("Não é possível alterar um livro com status $field")
            }
            field = value
        }

    constructor(
        id: Long? = null,
        name: String,
        price: BigDecimal,
        customer: Customer? = null,
        status: BookStatus?
    ) : this(id, name, price, customer) {
        this.status = status
    }
}