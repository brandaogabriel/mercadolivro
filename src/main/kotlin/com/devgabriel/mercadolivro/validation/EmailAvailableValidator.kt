package com.devgabriel.mercadolivro.validation

import com.devgabriel.mercadolivro.service.CustomerServiceImpl
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class EmailAvailableValidator(
    private val customerServiceImpl: CustomerServiceImpl
) : ConstraintValidator<EmailAvailable, String>{

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value.isNullOrBlank()) {
            return false
        }

        return customerServiceImpl.emailAvailable(value)
    }

}
