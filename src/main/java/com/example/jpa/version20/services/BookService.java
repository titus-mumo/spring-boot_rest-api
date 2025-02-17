package com.example.jpa.version20.services;

import java.util.List;
import java.util.Optional;

import com.example.jpa.version20.domain.entities.BookEntity;

public interface BookService {
    BookEntity createBook(String isbn, BookEntity bookEntity);

    List<BookEntity> findAllBooks();

    Optional<BookEntity> findBookByIsbn(String isbn);

    String deleteBook(String isbn);
}
