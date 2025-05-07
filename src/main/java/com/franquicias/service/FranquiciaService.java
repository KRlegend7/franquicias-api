// Archivo: src/main/java/com/franquicias/service/FranquiciaService.java
package com.franquicias.service;

import com.franquicias.dto.ProductoMasStockDTO;
import com.franquicias.exception.ResourceNotFoundException;
import com.franquicias.model.Franquicia;
import com.franquicias.model.Producto;
import com.franquicias.model.Sucursal;
import com.franquicias.repository.FranquiciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FranquiciaService {

    @Autowired
    private FranquiciaRepository franquiciaRepository;

    // Crear una nueva franquicia
    public Franquicia crearFranquicia(String nombre) {
        Franquicia franquicia = new Franquicia();
        franquicia.setNombre(nombre);
        return franquiciaRepository.save(franquicia);
    }
    
    // Actualizar nombre de franquicia
    public Franquicia actualizarNombreFranquicia(String franquiciaId, String nuevoNombre) {
        Franquicia franquicia = obtenerFranquiciaPorId(franquiciaId);
        franquicia.setNombre(nuevoNombre);
        return franquiciaRepository.save(franquicia);
    }

    // Agregar una nueva sucursal a una franquicia
    public Franquicia agregarSucursal(String franquiciaId, String nombreSucursal) {
        Franquicia franquicia = obtenerFranquiciaPorId(franquiciaId);
        
        Sucursal nuevaSucursal = new Sucursal();
        nuevaSucursal.setId(java.util.UUID.randomUUID().toString());
        nuevaSucursal.setNombre(nombreSucursal);
        
        franquicia.getSucursales().add(nuevaSucursal);
        return franquiciaRepository.save(franquicia);
    }
    
    // Actualizar nombre de sucursal
    public Franquicia actualizarNombreSucursal(String franquiciaId, String sucursalId, String nuevoNombre) {
        Franquicia franquicia = obtenerFranquiciaPorId(franquiciaId);
        
        Sucursal sucursal = franquicia.getSucursales().stream()
                .filter(s -> s.getId().equals(sucursalId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con ID: " + sucursalId));
        
        sucursal.setNombre(nuevoNombre);
        return franquiciaRepository.save(franquicia);
    }

    // Agregar un nuevo producto a una sucursal
    public Franquicia agregarProducto(String franquiciaId, String sucursalId, String nombreProducto, int stock) {
        Franquicia franquicia = obtenerFranquiciaPorId(franquiciaId);
        
        Sucursal sucursal = franquicia.getSucursales().stream()
                .filter(s -> s.getId().equals(sucursalId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con ID: " + sucursalId));
        
        Producto nuevoProducto = new Producto();
        nuevoProducto.setId(java.util.UUID.randomUUID().toString());
        nuevoProducto.setNombre(nombreProducto);
        nuevoProducto.setStock(stock);
        
        sucursal.getProductos().add(nuevoProducto);
        return franquiciaRepository.save(franquicia);
    }
    
    // Actualizar nombre de producto
    public Franquicia actualizarNombreProducto(String franquiciaId, String sucursalId, String productoId, String nuevoNombre) {
        Franquicia franquicia = obtenerFranquiciaPorId(franquiciaId);
        
        Sucursal sucursal = franquicia.getSucursales().stream()
                .filter(s -> s.getId().equals(sucursalId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con ID: " + sucursalId));
        
        Producto producto = sucursal.getProductos().stream()
                .filter(p -> p.getId().equals(productoId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productoId));
        
        producto.setNombre(nuevoNombre);
        return franquiciaRepository.save(franquicia);
    }

    // Eliminar un producto de una sucursal
    public Franquicia eliminarProducto(String franquiciaId, String sucursalId, String productoId) {
        Franquicia franquicia = obtenerFranquiciaPorId(franquiciaId);
        
        Sucursal sucursal = franquicia.getSucursales().stream()
                .filter(s -> s.getId().equals(sucursalId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con ID: " + sucursalId));
        
        boolean eliminado = sucursal.getProductos().removeIf(p -> p.getId().equals(productoId));
        
        if (!eliminado) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + productoId);
        }
        
        return franquiciaRepository.save(franquicia);
    }

    // Modificar el stock de un producto
    public Franquicia modificarStockProducto(String franquiciaId, String sucursalId, String productoId, int nuevoStock) {
        Franquicia franquicia = obtenerFranquiciaPorId(franquiciaId);
        
        Sucursal sucursal = franquicia.getSucursales().stream()
                .filter(s -> s.getId().equals(sucursalId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Sucursal no encontrada con ID: " + sucursalId));
        
        Producto producto = sucursal.getProductos().stream()
                .filter(p -> p.getId().equals(productoId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productoId));
        
        producto.setStock(nuevoStock);
        return franquiciaRepository.save(franquicia);
    }

    // Obtener el producto con más stock por sucursal para una franquicia
    public List<ProductoMasStockDTO> obtenerProductosConMasStockPorSucursal(String franquiciaId) {
        Franquicia franquicia = obtenerFranquiciaPorId(franquiciaId);
        List<ProductoMasStockDTO> resultado = new ArrayList<>();
        
        for (Sucursal sucursal : franquicia.getSucursales()) {
            if (!sucursal.getProductos().isEmpty()) {
                Optional<Producto> productoConMasStock = sucursal.getProductos().stream()
                        .max(Comparator.comparing(Producto::getStock));
                
                productoConMasStock.ifPresent(producto -> {
                    ProductoMasStockDTO dto = new ProductoMasStockDTO();
                    dto.setSucursalId(sucursal.getId());
                    dto.setSucursalNombre(sucursal.getNombre());
                    dto.setProductoId(producto.getId());
                    dto.setProductoNombre(producto.getNombre());
                    dto.setStock(producto.getStock());
                    resultado.add(dto);
                });
            }
        }
        
        return resultado;
    }

    // Obtener todas las franquicias
    public List<Franquicia> obtenerTodasLasFranquicias() {
        return franquiciaRepository.findAll();
    }

    // Obtener una franquicia por ID
    public Franquicia obtenerFranquiciaPorId(String id) {
        return franquiciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Franquicia no encontrada con ID: " + id));
    }
}