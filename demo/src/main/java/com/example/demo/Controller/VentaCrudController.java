package com.example.demo.Controller;

import com.example.demo.Entity.Venta;
import com.example.demo.Service.VentaService;
import com.example.demo.Service.ClienteService;
import com.example.demo.Service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
public class VentaCrudController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/venta/nuevo")
    public String mostrarFormularioNuevo(HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/home";
        model.addAttribute("venta", new Venta());
        model.addAttribute("clientes", clienteService.listarClientes());
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        model.addAttribute("titulo", "Nueva Venta");
        return "venta-form";
    }

    @PostMapping("/venta/guardar")
    public String guardarVenta(@RequestParam String fechaVenta,
                               @RequestParam Double total,
                               @RequestParam Integer estado,
                               @RequestParam Integer clienteDpi,
                               @RequestParam Integer usuarioCodigo,
                               HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";

        try {
            Venta venta = new Venta();
            venta.setFechaVenta(LocalDate.parse(fechaVenta));
            venta.setTotal(total);
            venta.setEstado(estado);
            venta.setCliente(clienteService.buscarClientePorId(clienteDpi));
            venta.setUsuario(usuarioService.buscarUsuarioPorId(usuarioCodigo));

            ventaService.guardarVenta(venta);
            return "redirect:/home#ventas";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/venta/nuevo?error=" + e.getMessage();
        }
    }

    @GetMapping("/venta/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/home";
        Venta venta = ventaService.buscarVentaPorId(id);
        model.addAttribute("venta", venta);
        model.addAttribute("clientes", clienteService.listarClientes());
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        model.addAttribute("titulo", "Editar Venta");
        return "venta-form";
    }

    @PostMapping("/venta/actualizar/{id}")
    public String actualizarVenta(@PathVariable Integer id,
                                  @RequestParam String fechaVenta,
                                  @RequestParam Double total,
                                  @RequestParam Integer estado,
                                  @RequestParam Integer clienteDpi,
                                  @RequestParam Integer usuarioCodigo,
                                  HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";

        try {
            Venta venta = ventaService.buscarVentaPorId(id);
            venta.setFechaVenta(LocalDate.parse(fechaVenta));
            venta.setTotal(total);
            venta.setEstado(estado);
            venta.setCliente(clienteService.buscarClientePorId(clienteDpi));
            venta.setUsuario(usuarioService.buscarUsuarioPorId(usuarioCodigo));

            ventaService.actualizarVenta(id, venta);
            return "redirect:/home#ventas";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/venta/editar/" + id + "?error=" + e.getMessage();
        }
    }

    @GetMapping("/venta/eliminar/{id}")
    public String eliminarVenta(@PathVariable Integer id, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";

        try {
            ventaService.eliminarVenta(id);
            return "redirect:/home#ventas";
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