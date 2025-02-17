package com.example.jpa.version20.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.example.jpa.version20.domain.entities.BookEntity;
import com.example.jpa.version20.repositories.BookRepository;
import com.example.jpa.version20.services.BookService;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

    @Override
    public List<BookEntity> findAllBooks() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<BookEntity> findBookByIsbn(String isbn) {
        return Optional.ofNullable(bookRepository.findById(isbn).orElse(null));
    }

    @Override
    public String deleteBook(String isbn) {
        Optional<BookEntity> bookEntity = findBookByIsbn(isbn);
        if (bookEntity.isPresent()) {
            bookRepository.deleteById(isbn);
            return "Book deleted";
        } else {
            return "book_not_found";
        }
    }
}