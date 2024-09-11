package com.johancastro.cun.edu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SolicitudPrestamo {

    private String numeroDocumento;
    private String isbn;
    private int diasPrestamo;

}
