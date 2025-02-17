package com.example.jpa.version20.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.jpa.version20.domain.dto.AuthorDto;
import com.example.jpa.version20.domain.entities.AuthorEntity;
import com.example.jpa.version20.mappers.Mapper;

@Component
public class AuthorMapperImp implements Mapper<AuthorEntity, AuthorDto> {

    private ModelMapper modelMapper;

    public AuthorMapperImp(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapToDto(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);
    }

    @Override
    public AuthorEntity mapFromDto(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }
}