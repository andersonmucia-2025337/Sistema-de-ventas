package com.example.demo.Service;

import com.example.demo.Entity.DetalleVenta;
import com.example.demo.Repository.DetalleVentaRepository;
import com.example.demo.Repository.VentaRepository;
import com.example.demo.Repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DetalleVentaServiceImplements implements DetalleVentaService {

    private final DetalleVentaRepository detalleVentaRepository;
    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    public DetalleVentaServiceImplements(DetalleVentaRepository detalleVentaRepository,
                                         VentaRepository ventaRepository,
                                         ProductoRepository productoRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public List<DetalleVenta> listarDetalleVentas() {
        return detalleVentaRepository.findAll();
    }

    @Override
    public DetalleVenta buscarDetalleVentaPorId(Integer codigoDetalleVenta) {
        return detalleVentaRepository.findById(codigoDetalleVenta)
                .orElseThrow(() -> new RuntimeException("Detalle de venta no encontrado con ID: " + codigoDetalleVenta));
    }

    @Override
    public DetalleVenta guardarDetalleVenta(DetalleVenta detalleVenta) throws RuntimeException {
        try {
            // Verificar que la venta existe
            ventaRepository.findById(detalleVenta.getVenta().getCodigoVenta())
                    .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

            // Verificar que el producto existe
            productoRepository.findById(detalleVenta.getProducto().getCodigoProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            return detalleVentaRepository.save(detalleVenta);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el detalle de venta: " + e.getMessage());
        }
    }

    @Override
    public DetalleVenta actualizarDetalleVenta(Integer codigoDetalleVenta, DetalleVenta detalleVentaActualizado) throws RuntimeException {
        try {
            DetalleVenta detalleVenta = buscarDetalleVentaPorId(codigoDetalleVenta);
            detalleVenta.setCantidad(detalleVentaActualizado.getCantidad());
            detalleVenta.setPrecioUnitario(detalleVentaActualizado.getPrecioUnitario());
            detalleVenta.setSubtotal(detalleVentaActualizado.getSubtotal());
            detalleVenta.setProducto(detalleVentaActualizado.getProducto());
            detalleVenta.setVenta(detalleVentaActualizado.getVenta());
            return detalleVentaRepository.save(detalleVenta);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el detalle de venta: " + e.getMessage());
        }
    }

    @Override
    public void eliminarDetalleVenta(Integer codigoDetalleVenta) throws RuntimeException {
        try {
            detalleVentaRepository.deleteById(codigoDetalleVenta);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el detalle de venta: " + e.getMessage());
        }
    }
}