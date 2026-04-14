package com.example.demo.Service;

import com.example.demo.Entity.DetalleVenta;
import java.util.List;

public interface DetalleVentaService {
    List<DetalleVenta> listarDetalleVentas();
    DetalleVenta buscarDetalleVentaPorId(Integer codigoDetalleVenta);
    DetalleVenta guardarDetalleVenta(DetalleVenta detalleVenta) throws RuntimeException;
    DetalleVenta actualizarDetalleVenta(Integer codigoDetalleVenta, DetalleVenta detalleVenta) throws RuntimeException;
    void eliminarDetalleVenta(Integer codigoDetalleVenta) throws RuntimeException;
}