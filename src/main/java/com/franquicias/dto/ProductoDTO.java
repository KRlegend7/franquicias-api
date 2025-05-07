// Archivo: src/main/java/com/franquicias/dto/ProductoDTO.java
package com.franquicias.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;
    
    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;
}