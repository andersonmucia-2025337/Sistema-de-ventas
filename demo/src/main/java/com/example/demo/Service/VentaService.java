package com.example.demo.Service;

import com.example.demo.Entity.Venta;
import java.util.List;

public interface VentaService {
    List<Venta> listarVentas();
    Venta buscarVentaPorId(Integer codigoVenta);
    Venta guardarVenta(Venta venta) throws RuntimeException;
    Venta actualizarVenta(Integer codigoVenta, Venta venta) throws RuntimeException;
    void eliminarVenta(Integer codigoVenta) throws RuntimeException;
}