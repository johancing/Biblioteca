package com.johancastro.cun.edu.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario extends Persona {

    private long id;
    private String tipoDocumento;
    private String numeroDocumento;
    private String direccion;
    private String numeroTelefono;
    private String email;
}
