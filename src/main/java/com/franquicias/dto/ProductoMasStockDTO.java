// Archivo: src/main/java/com/franquicias/dto/ProductoMasStockDTO.java
package com.franquicias.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoMasStockDTO {
    private String sucursalId;
    private String sucursalNombre;
    private String productoId;
    private String productoNombre;
    private int stock;
}