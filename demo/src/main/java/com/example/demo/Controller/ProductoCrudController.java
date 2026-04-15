package com.example.demo.Controller;

import com.example.demo.Entity.Producto;
import com.example.demo.Service.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductoCrudController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/producto/nuevo")
    public String mostrarFormularioNuevo(HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/home";
        model.addAttribute("producto", new Producto());
        model.addAttribute("titulo", "Nuevo Producto");
        return "producto-form";
    }

    @PostMapping("/producto/guardar")
    public String guardarProducto(@RequestParam String nombreProducto,
                                  @RequestParam Double precio,
                                  @RequestParam Integer stock,
                                  @RequestParam Integer estado,
                                  HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";

        try {
            Producto producto = new Producto();
            producto.setNombreProducto(nombreProducto);
            producto.setPrecio(precio);
            producto.setStock(stock);
            producto.setEstado(estado);

            productoService.guardarProducto(producto);
            return "redirect:/home#productos";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/producto/nuevo?error=" + e.getMessage();
        }
    }

    @GetMapping("/producto/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/home";
        Producto producto = productoService.buscarProductoPorId(id);
        model.addAttribute("producto", producto);
        model.addAttribute("titulo", "Editar Producto");
        return "producto-form";
    }

    @PostMapping("/producto/actualizar/{id}")
    public String actualizarProducto(@PathVariable Integer id,
                                     @RequestParam String nombreProducto,
                                     @RequestParam Double precio,
                                     @RequestParam Integer stock,
                                     @RequestParam Integer estado,
                                     HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";

        try {
            Producto producto = productoService.buscarProductoPorId(id);
            producto.setNombreProducto(nombreProducto);
            producto.setPrecio(precio);
            producto.setStock(stock);
            producto.setEstado(estado);

            productoService.actualizarProducto(id, producto);
            return "redirect:/home#productos";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/producto/editar/" + id + "?error=" + e.getMessage();
        }
    }

    @GetMapping("/producto/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";

        try {
            productoService.eliminarProducto(id);
            return "redirect:/home#productos";
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