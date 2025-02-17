package com.example.jpa.version20.services;

import java.util.List;
import java.util.Optional;

import com.example.jpa.version20.domain.entities.AuthorEntity;

public interface AuthorService {

    AuthorEntity createAuthor(AuthorEntity author);

    Optional<AuthorEntity> getAuthorById(Long id);

    List<AuthorEntity> getAllAuthors();

    String deleteAuthorById(Long id);

}
