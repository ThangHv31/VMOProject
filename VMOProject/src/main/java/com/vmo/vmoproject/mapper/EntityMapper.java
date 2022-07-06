package com.vmo.vmoproject.mapper;

import java.util.List;

public interface EntityMapper<D,E> {
    D toDTO(E entity);
    E toEntity(D dto);
    List<D> toDTOList(List<E> entityList);
    List<E> toEntityList(List<D> dtoList);
}
