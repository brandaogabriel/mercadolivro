package com.devgabriel.mercadolivro.service

import com.devgabriel.mercadolivro.enums.BookStatus
import com.devgabriel.mercadolivro.model.Book
import com.devgabriel.mercadolivro.repository.BookRepository
import org.slf4j.LoggerFactory
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

    override fun findAll(): List<Book> {
        return bookRepository.findAll()
    }

    override fun findActives(): List<Book> {
        return bookRepository.findByStatus(BookStatus.ATIVO)
    }

    override fun findById(id: Long): Book {
        return bookRepository.findById(id).orElseThrow()
    }

    override fun update(book: Book) {
        bookRepository.save(book)
    }

    override fun delete(id: Long) {
        val book = findById(id)
        book.status = BookStatus.DELETADO
        update(book)
    }
}