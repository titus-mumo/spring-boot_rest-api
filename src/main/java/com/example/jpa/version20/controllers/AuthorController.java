package com.example.jpa.version20.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpa.version20.domain.dto.AuthorDto;
import com.example.jpa.version20.domain.entities.AuthorEntity;
import com.example.jpa.version20.mappers.Mapper;
import com.example.jpa.version20.services.AuthorService;

@RestController
public class AuthorController {

    private AuthorService authorService;

    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        AuthorEntity authorEntity = authorMapper.mapFromDto(authorDto);
        AuthorEntity savedAuthor = authorService.createAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.mapToDto(savedAuthor), HttpStatus.CREATED);
    }

    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<Optional<AuthorDto>> getAuthorById(@PathVariable("id") Long id) {
        Optional<AuthorEntity> authorEntity = authorService.getAuthorById(id);
        if (authorEntity.isPresent()) {
            return new ResponseEntity<>(Optional.of(authorMapper.mapToDto(authorEntity.get())), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/authors")
    public List<AuthorDto> getAllAuthors() {
        List<AuthorEntity> allAuthors = authorService.getAllAuthors();
        List<AuthorDto> authorDtos = allAuthors.stream()
                .map(authorMapper::mapToDto)
                .collect(Collectors.toList());
        return authorDtos;
    }

    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity<String> deleteAuthorById(@PathVariable("id") Long id) {
        String deleteResponse = authorService.deleteAuthorById(id);
        if (deleteResponse.equals("Author deleted")) {
            return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(deleteResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> fullAuthorUpdate(@PathVariable("id") Long id,
            @RequestBody AuthorDto authorDto) {
        if (authorService.getAuthorById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authorDto.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFromDto(authorDto);
        AuthorEntity savedAuthor = authorService.createAuthor(authorEntity);
        AuthorDto saveAuthorDto = authorMapper.mapToDto(savedAuthor);
        return new ResponseEntity<>(saveAuthorDto, HttpStatus.OK);
    }
}
