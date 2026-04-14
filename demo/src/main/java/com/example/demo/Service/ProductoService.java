package com.example.demo.Service;

import com.example.demo.Entity.Producto;
import java.util.List;

public interface ProductoService {
    List<Producto> listarProductos();
    Producto buscarProductoPorId(Integer codigoProducto);
    Producto guardarProducto(Producto producto) throws RuntimeException;
    Producto actualizarProducto(Integer codigoProducto, Producto producto) throws RuntimeException;
    void eliminarProducto(Integer codigoProducto) throws RuntimeException;
}