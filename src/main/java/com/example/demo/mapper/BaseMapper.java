package com.example.demo.mapper;

public interface BaseMapper<D, E> {
    E dtoToEntity(D d);
    D entityToDto(E e);
}

