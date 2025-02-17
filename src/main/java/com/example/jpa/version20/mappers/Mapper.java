package com.example.jpa.version20.mappers;

public interface Mapper<A, B> {

    B mapToDto(A a);

    A mapFromDto(B b);

}
