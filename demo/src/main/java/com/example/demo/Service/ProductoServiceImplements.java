package com.example.demo.Service;

import com.example.demo.Entity.Producto;
import com.example.demo.Repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoServiceImplements implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImplements(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto buscarProductoPorId(Integer codigoProducto) {
        return productoRepository.findById(codigoProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + codigoProducto));
    }

    @Override
    public Producto guardarProducto(Producto producto) throws RuntimeException {
        try {
            producto.setEstado(1);
            return productoRepository.save(producto);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el producto: " + e.getMessage());
        }
    }

    @Override
    public Producto actualizarProducto(Integer codigoProducto, Producto productoActualizado) throws RuntimeException {
        try {
            Producto producto = buscarProductoPorId(codigoProducto);
            producto.setNombreProducto(productoActualizado.getNombreProducto());
            producto.setPrecio(productoActualizado.getPrecio());
            producto.setStock(productoActualizado.getStock());
            producto.setEstado(productoActualizado.getEstado());
            return productoRepository.save(producto);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el producto: " + e.getMessage());
        }
    }

    @Override
    public void eliminarProducto(Integer codigoProducto) throws RuntimeException {
        try {
            productoRepository.deleteById(codigoProducto);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el producto: " + e.getMessage());
        }
    }
}