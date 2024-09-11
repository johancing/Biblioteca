package com.johancastro.cun.edu.service;

import java.util.Optional;

public interface IService<T, Document> {

    Optional<T> crear(T t);
    Optional<T> actualizar(T t);
    Optional<T> buscar(Document document);
    Iterable<T> listar();
    boolean eliminar(String eliminar);

}
