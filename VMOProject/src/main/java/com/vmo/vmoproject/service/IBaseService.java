package com.vmo.vmoproject.service;

import java.util.List;

public interface IBaseService<D> {
    D create(String id,D e);
    D findById(String id);
    D update(String id, D dto);
}
