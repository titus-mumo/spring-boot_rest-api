package com.example.jpa.version20.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa.version20.domain.dto.BookDto;
import com.example.jpa.version20.domain.entities.BookEntity;
import com.example.jpa.version20.mappers.Mapper;
import com.example.jpa.version20.services.BookService;

@RestController
public class BookController {

    private BookService bookService;

    private Mapper<BookEntity, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    };

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFromDto(bookDto);
        BookEntity createdBook = bookService.createBook(isbn, bookEntity);
        BookDto savedBook = bookMapper.mapToDto(createdBook);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<Optional<BookDto>> getBookByIsbn(@PathVariable("isbn") String isbn) {
        Optional<BookEntity> bookEntity = bookService.findBookByIsbn(isbn);
        if (bookEntity.isPresent()) {
            return new ResponseEntity<>(Optional.of(bookMapper.mapToDto(bookEntity.get())), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/books")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookEntity> allBookEntities = bookService.findAllBooks();
        List<BookDto> bookDtos = allBookEntities.stream()
                .map(bookMapper::mapToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookDtos, HttpStatus.OK);
    }

    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity<String> deleteBook(@PathVariable("isbn") String isbn) {
        String deleteResponse = bookService.deleteBook(isbn);
        if (deleteResponse.equals("Book deleted")) {
            return new ResponseEntity<>("Book deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(deleteResponse, HttpStatus.NOT_FOUND);
        }
    }
}
