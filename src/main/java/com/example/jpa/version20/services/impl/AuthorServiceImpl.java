package com.example.jpa.version20.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.example.jpa.version20.domain.entities.AuthorEntity;
import com.example.jpa.version20.repositories.AuthorRepository;
import com.example.jpa.version20.services.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public Optional<AuthorEntity> getAuthorById(Long id) {
        return Optional.ofNullable(authorRepository.findById(id).orElse(null));
    }

    @Override
    public List<AuthorEntity> getAllAuthors() {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public String deleteAuthorById(Long id) {
        Optional<AuthorEntity> authorEntity = getAuthorById(id);
        if (authorEntity.isPresent()) {
            authorRepository.deleteById(id);
            return "Author deleted";
        } else {
            return "Author not found";
        }
    }

}
