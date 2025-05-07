// Archivo: src/main/java/com/franquicias/model/Sucursal.java
package com.franquicias.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sucursal {
    @Id
    private String id;
    private String nombre;
    private List<Producto> productos = new ArrayList<>();
}