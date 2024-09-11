package com.johancastro.cun.edu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Prestamo {

    private long prestamoId;
    private Usuario usuario;
    private Libro libro;
    private Date fechaPrestamo;
    private Date fechaEstimadaReintegro;
    private Date fechaReintegro;
    private String informacion;

}
