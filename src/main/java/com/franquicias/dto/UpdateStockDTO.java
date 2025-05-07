// Archivo: src/main/java/com/franquicias/dto/UpdateStockDTO.java
package com.franquicias.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStockDTO {
    @Min(value = 0, message = "El stock no puede ser negativo")
    private int nuevoStock;
}