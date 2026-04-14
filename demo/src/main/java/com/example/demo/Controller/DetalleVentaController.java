package com.example.demo.Controller;

import com.example.demo.Entity.DetalleVenta;
import com.example.demo.Service.DetalleVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/detalle-ventas")
public class DetalleVentaController {
    private final DetalleVentaService detalleVentaService;

    public DetalleVentaController(DetalleVentaService detalleVentaService) {
        this.detalleVentaService = detalleVentaService;
    }

    @GetMapping
    public List<DetalleVenta> listarDetalleVentas() {
        return detalleVentaService.listarDetalleVentas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarDetalleVentaPorId(@PathVariable Integer id) {
        try {
            DetalleVenta detalle = detalleVentaService.buscarDetalleVentaPorId(id);
            return ResponseEntity.ok(detalle);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> guardarDetalleVenta(@RequestBody DetalleVenta detalleVenta) {
        try {
            DetalleVenta nuevoDetalle = detalleVentaService.guardarDetalleVenta(detalleVenta);
            return new ResponseEntity<>(nuevoDetalle, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarDetalleVenta(@PathVariable Integer id, @RequestBody DetalleVenta detalleVenta) {
        try {
            DetalleVenta detalleActualizado = detalleVentaService.actualizarDetalleVenta(id, detalleVenta);
            return ResponseEntity.ok(detalleActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarDetalleVenta(@PathVariable Integer id) {
        try {
            detalleVentaService.eliminarDetalleVenta(id);
            return ResponseEntity.ok("Detalle de venta con ID " + id + " eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}