package com.example.demo.Controller;

import com.example.demo.Entity.Cliente;
import com.example.demo.Service.ClienteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClienteCrudController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/cliente/nuevo")
    public String mostrarFormularioNuevo(HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/home";
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("titulo", "Nuevo Cliente");
        return "cliente-form";
    }

    @PostMapping("/cliente/guardar")
    public String guardarCliente(@RequestParam(required = false) Integer dpiCliente,
                                 @RequestParam String nombreCliente,
                                 @RequestParam String apellidoCliente,
                                 @RequestParam String direccion,
                                 @RequestParam Integer estado,
                                 HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";

        try {
            Cliente cliente = new Cliente();
            cliente.setDpiCliente(dpiCliente);
            cliente.setNombreCliente(nombreCliente);
            cliente.setApellidoCliente(apellidoCliente);
            cliente.setDireccion(direccion);
            cliente.setEstado(estado);

            clienteService.guardarCliente(cliente);
            return "redirect:/home#clientes";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/cliente/nuevo?error=" + e.getMessage();
        }
    }

    @GetMapping("/cliente/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/home";
        Cliente cliente = clienteService.buscarClientePorId(id);
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Editar Cliente");
        return "cliente-form";
    }

    @PostMapping("/cliente/actualizar/{id}")
    public String actualizarCliente(@PathVariable Integer id,
                                    @RequestParam String nombreCliente,
                                    @RequestParam String apellidoCliente,
                                    @RequestParam String direccion,
                                    @RequestParam Integer estado,
                                    HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";

        try {
            Cliente cliente = clienteService.buscarClientePorId(id);
            cliente.setNombreCliente(nombreCliente);
            cliente.setApellidoCliente(apellidoCliente);
            cliente.setDireccion(direccion);
            cliente.setEstado(estado);

            clienteService.actualizarCliente(id, cliente);
            return "redirect:/home#clientes";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/cliente/editar/" + id + "?error=" + e.getMessage();
        }
    }

    @GetMapping("/cliente/eliminar/{id}")
    public String eliminarCliente(@PathVariable Integer id, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";

        try {
            clienteService.eliminarCliente(id);
            return "redirect:/home#clientes";
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