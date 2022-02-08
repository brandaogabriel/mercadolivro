package com.devgabriel.mercadolivro.validation

import com.devgabriel.mercadolivro.enums.BookStatus
import com.devgabriel.mercadolivro.service.BookService
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class BookAvailableValidator(
    private val bookService: BookService,
) : ConstraintValidator<BookAvailable, Set<Long>> {

    override fun isValid(values: Set<Long>, context: ConstraintValidatorContext?): Boolean {
        values.forEach { bookId: Long ->
            if (!bookService.existsByIdAndStatus(bookId, BookStatus.ATIVO)) return false
        }

        return true
    }

}
