package com.devgabriel.mercadolivro.service

import com.devgabriel.mercadolivro.enums.BookStatus
import com.devgabriel.mercadolivro.exception.Errors
import com.devgabriel.mercadolivro.exception.NotFoundException
import com.devgabriel.mercadolivro.model.Book
import com.devgabriel.mercadolivro.model.Customer
import com.devgabriel.mercadolivro.repository.BookRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(
    private val bookRepository: BookRepository
) : BookService {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun create(book: Book) {
        logger.info("[${javaClass.simpleName}.create] start book_name={}", book.name)
        bookRepository.save(book)
        logger.debug("[${javaClass.simpleName}.create] call - success - model: {}", book)
    }

    override fun findAll(pageable: Pageable): Page<Book> {
        return bookRepository.findAll(pageable)
    }

    override fun findActives(pageable: Pageable): Page<Book> {
        return bookRepository.findByStatus(BookStatus.ATIVO, pageable)
    }

    override fun findById(id: Long): Book {
        return bookRepository
            .findById(id)
            .orElseThrow {
                throw NotFoundException(Errors.ML_101.message.format(id), Errors.ML_101.code)
            }
    }

    override fun update(book: Book) {
        bookRepository.save(book)
    }

    override fun delete(id: Long) {
        val book = findById(id)
        book.status = BookStatus.DELETADO
        update(book)
    }

    override fun deleteByCustomer(customer: Customer) {
        val books = bookRepository.findByCustomer(customer)
        books.map { book ->
            if (book.status == BookStatus.ATIVO) book.status = BookStatus.DELETADO
        }
        bookRepository.saveAll(books)
    }
}