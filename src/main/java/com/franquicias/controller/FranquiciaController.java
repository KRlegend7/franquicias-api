// Archivo: src/main/java/com/franquicias/controller/FranquiciaController.java
package com.franquicias.controller;

import com.franquicias.dto.*;
import com.franquicias.model.Franquicia;
import com.franquicias.service.FranquiciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/franquicias")
public class FranquiciaController {

    @Autowired
    private FranquiciaService franquiciaService;

    // Endpoint para crear una nueva franquicia
    @PostMapping
    public ResponseEntity<Franquicia> crearFranquicia(@Valid @RequestBody FranquiciaDTO franquiciaDTO) {
        Franquicia nuevaFranquicia = franquiciaService.crearFranquicia(franquiciaDTO.getNombre());
        return new ResponseEntity<>(nuevaFranquicia, HttpStatus.CREATED);
    }
    
    // Endpoint para actualizar el nombre de una franquicia
    @PutMapping("/{franquiciaId}/nombre")
    public ResponseEntity<Franquicia> actualizarNombreFranquicia(
            @PathVariable String franquiciaId,
            @Valid @RequestBody UpdateNameDTO updateNameDTO) {
        Franquicia franquicia = franquiciaService.actualizarNombreFranquicia(franquiciaId, updateNameDTO.getNuevoNombre());
        return ResponseEntity.ok(franquicia);
    }

    // Endpoint para agregar una nueva sucursal a una franquicia
    @PostMapping("/{franquiciaId}/sucursales")
    public ResponseEntity<Franquicia> agregarSucursal(
            @PathVariable String franquiciaId,
            @Valid @RequestBody SucursalDTO sucursalDTO) {
        Franquicia franquicia = franquiciaService.agregarSucursal(franquiciaId, sucursalDTO.getNombre());
        return ResponseEntity.ok(franquicia);
    }
    
    // Endpoint para actualizar el nombre de una sucursal
    @PutMapping("/{franquiciaId}/sucursales/{sucursalId}/nombre")
    public ResponseEntity<Franquicia> actualizarNombreSucursal(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @Valid @RequestBody UpdateNameDTO updateNameDTO) {
        Franquicia franquicia = franquiciaService.actualizarNombreSucursal(franquiciaId, sucursalId, updateNameDTO.getNuevoNombre());
        return ResponseEntity.ok(franquicia);
    }

    // Endpoint para agregar un nuevo producto a una sucursal
    @PostMapping("/{franquiciaId}/sucursales/{sucursalId}/productos")
    public ResponseEntity<Franquicia> agregarProducto(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @Valid @RequestBody ProductoDTO productoDTO) {
        Franquicia franquicia = franquiciaService.agregarProducto(
                franquiciaId, 
                sucursalId, 
                productoDTO.getNombre(), 
                productoDTO.getStock()
        );
        return ResponseEntity.ok(franquicia);
    }
    
    // Endpoint para actualizar el nombre de un producto
    @PutMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/nombre")
    public ResponseEntity<Franquicia> actualizarNombreProducto(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @PathVariable String productoId,
            @Valid @RequestBody UpdateNameDTO updateNameDTO) {
        Franquicia franquicia = franquiciaService.actualizarNombreProducto(
                franquiciaId, 
                sucursalId, 
                productoId, 
                updateNameDTO.getNuevoNombre()
        );
        return ResponseEntity.ok(franquicia);
    }

    // Endpoint para eliminar un producto de una sucursal
    @DeleteMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}")
    public ResponseEntity<Franquicia> eliminarProducto(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @PathVariable String productoId) {
        Franquicia franquicia = franquiciaService.eliminarProducto(franquiciaId, sucursalId, productoId);
        return ResponseEntity.ok(franquicia);
    }

    // Endpoint para modificar el stock de un producto
    @PutMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/stock")
    public ResponseEntity<Franquicia> modificarStockProducto(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @PathVariable String productoId,
            @Valid @RequestBody UpdateStockDTO updateStockDTO) {
        Franquicia franquicia = franquiciaService.modificarStockProducto(
                franquiciaId, 
                sucursalId, 
                productoId, 
                updateStockDTO.getNuevoStock()
        );
        return ResponseEntity.ok(franquicia);
    }

    // Endpoint para obtener el producto con más stock por sucursal para una franquicia
    @GetMapping("/{franquiciaId}/productos-mas-stock")
    public ResponseEntity<List<ProductoMasStockDTO>> obtenerProductosConMasStockPorSucursal(
            @PathVariable String franquiciaId) {
        List<ProductoMasStockDTO> productos = franquiciaService.obtenerProductosConMasStockPorSucursal(franquiciaId);
        return ResponseEntity.ok(productos);
    }

    // Endpoint para obtener todas las franquicias
    @GetMapping
    public ResponseEntity<List<Franquicia>> obtenerTodasLasFranquicias() {
        List<Franquicia> franquicias = franquiciaService.obtenerTodasLasFranquicias();
        return ResponseEntity.ok(franquicias);
    }

    // Endpoint para obtener una franquicia por ID
    @GetMapping("/{franquiciaId}")
    public ResponseEntity<Franquicia> obtenerFranquiciaPorId(@PathVariable String franquiciaId) {
        Franquicia franquicia = franquiciaService.obtenerFranquiciaPorId(franquiciaId);
        return ResponseEntity.ok(franquicia);
    }
}