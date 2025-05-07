// Archivo: src/main/java/com/franquicias/dto/FranquiciaDTO.java
package com.franquicias.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FranquiciaDTO {
    @NotBlank(message = "El nombre de la franquicia es obligatorio")
    private String nombre;
}