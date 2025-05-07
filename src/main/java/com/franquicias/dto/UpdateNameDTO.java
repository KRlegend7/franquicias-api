// Archivo: src/main/java/com/franquicias/dto/UpdateNameDTO.java
package com.franquicias.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNameDTO {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nuevoNombre;
}