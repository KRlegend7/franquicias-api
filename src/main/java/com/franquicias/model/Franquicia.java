// Archivo: src/main/java/com/franquicias/model/Franquicia.java
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
@Document(collection = "franquicias")
public class Franquicia {
    @Id
    private String id;
    private String nombre;
    private List<Sucursal> sucursales = new ArrayList<>();
}