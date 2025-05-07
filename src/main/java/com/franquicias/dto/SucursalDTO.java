// Archivo: src/main/java/com/franquicias/dto/SucursalDTO.java
package com.franquicias.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SucursalDTO {
    @NotBlank(message = "El nombre de la sucursal es obligatorio")
    private String nombre;
}