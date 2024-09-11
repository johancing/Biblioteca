package com.johancastro.cun.edu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Libro {

    private long id;
    private String isbn;
    private String titulo;
    private String autor;
    private String edicion;

}
