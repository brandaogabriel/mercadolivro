package com.devgabriel.mercadolivro.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [BookAvailableValidator::class])
annotation class BookAvailable(
    val message: String = "Livro não possui status ativo",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
