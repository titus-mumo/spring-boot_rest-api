package com.example.jpa.version20.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.jpa.version20.domain.dto.BookDto;
import com.example.jpa.version20.domain.entities.BookEntity;
import com.example.jpa.version20.mappers.Mapper;

@Component
public class BookMapperImp implements Mapper<BookEntity, BookDto> {

    private ModelMapper modelMapper;

    public BookMapperImp(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto mapToDto(BookEntity entity) {
        return modelMapper.map(entity, BookDto.class);
    }

    @Override
    public BookEntity mapFromDto(BookDto dto) {
        return modelMapper.map(dto, BookEntity.class);
    }

}
