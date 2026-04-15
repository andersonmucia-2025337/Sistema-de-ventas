package com.example.demo.Controller;

import com.example.demo.Entity.DetalleVenta;
import com.example.demo.Service.DetalleVentaService;
import com.example.demo.Service.ProductoService;
import com.example.demo.Service.VentaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DetalleCrudController {

    @Autowired
    private DetalleVentaService detalleVentaService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private VentaService ventaService;

    @GetMapping("/detalle/nuevo")
    public String mostrarFormularioNuevo(HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/home";
        model.addAttribute("detalle", new DetalleVenta());
        model.addAttribute("productos", productoService.listarProductos());
        model.addAttribute("ventas", ventaService.listarVentas());
        model.addAttribute("titulo", "Nuevo Detalle de Venta");
        return "detalle-form";
    }

    @PostMapping("/detalle/guardar")
    public String guardarDetalle(@RequestParam Integer cantidad,
                                 @RequestParam Double precioUnitario,
                                 @RequestParam Double subtotal,
                                 @RequestParam Integer productoCodigo,
                                 @RequestParam Integer ventaCodigo,
                                 HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";

        try {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(precioUnitario);
            detalle.setSubtotal(subtotal);
            detalle.setProducto(productoService.buscarProductoPorId(productoCodigo));
            detalle.setVenta(ventaService.buscarVentaPorId(ventaCodigo));

            detalleVentaService.guardarDetalleVenta(detalle);
            return "redirect:/home#detalles";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/detalle/nuevo?error=" + e.getMessage();
        }
    }

    @GetMapping("/detalle/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/home";
        DetalleVenta detalle = detalleVentaService.buscarDetalleVentaPorId(id);
        model.addAttribute("detalle", detalle);
        model.addAttribute("productos", productoService.listarProductos());
        model.addAttribute("ventas", ventaService.listarVentas());
        model.addAttribute("titulo", "Editar Detalle de Venta");
        return "detalle-form";
    }

    @PostMapping("/detalle/actualizar/{id}")
    public String actualizarDetalle(@PathVariable Integer id,
                                    @RequestParam Integer cantidad,
                                    @RequestParam Double precioUnitario,
                                    @RequestParam Double subtotal,
                                    @RequestParam Integer productoCodigo,
                                    @RequestParam Integer ventaCodigo,
                                    HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";

        try {
            DetalleVenta detalle = detalleVentaService.buscarDetalleVentaPorId(id);
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(precioUnitario);
            detalle.setSubtotal(subtotal);
            detalle.setProducto(productoService.buscarProductoPorId(productoCodigo));
            detalle.setVenta(ventaService.buscarVentaPorId(ventaCodigo));

            detalleVentaService.actualizarDetalleVenta(id, detalle);
            return "redirect:/home#detalles";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/detalle/editar/" + id + "?error=" + e.getMessage();
        }
    }

    @GetMapping("/detalle/eliminar/{id}")
    public String eliminarDetalle(@PathVariable Integer id, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";

        try {
            detalleVentaService.eliminarDetalleVenta(id);
            return "redirect:/home#detalles";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home?error=" + e.getMessage();
        }
    }

    private boolean esAdmin(HttpSession session) {
        String rol = (String) session.getAttribute("rol");
        return "administrador".equals(rol) || "admin".equals(rol);
    }
}