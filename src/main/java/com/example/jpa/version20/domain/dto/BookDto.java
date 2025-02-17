package com.example.jpa.version20.domain.dto;

import com.example.jpa.version20.domain.entities.AuthorEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    private String isbn;

    private String title;

    private AuthorEntity author;
}
