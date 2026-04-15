package com.example.demo.Controller;

import com.example.demo.Entity.Usuario;
import com.example.demo.Service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioCrudController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario/nuevo")
    public String mostrarFormularioNuevo(HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/home";
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("titulo", "Nuevo Usuario");
        return "usuario-form";
    }

    @PostMapping("/usuario/guardar")
    public String guardarUsuario(@RequestParam String username,
                                 @RequestParam String password,
                                 @RequestParam String email,
                                 @RequestParam String rol,
                                 @RequestParam Integer estado,
                                 HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";

        try {
            Usuario usuario = new Usuario();
            usuario.setUsername(username);
            usuario.setPassword(password);
            usuario.setEmail(email);
            usuario.setRol(rol);
            usuario.setEstado(estado);

            usuarioService.guardarUsuario(usuario);
            return "redirect:/home#usuarios";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/usuario/nuevo?error=" + e.getMessage();
        }
    }

    @GetMapping("/usuario/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, HttpSession session, Model model) {
        if (!esAdmin(session)) return "redirect:/home";
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo", "Editar Usuario");
        return "usuario-form";
    }

    @PostMapping("/usuario/actualizar/{id}")
    public String actualizarUsuario(@PathVariable Integer id,
                                    @RequestParam String username,
                                    @RequestParam String password,
                                    @RequestParam String email,
                                    @RequestParam String rol,
                                    @RequestParam Integer estado,
                                    HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";

        try {
            Usuario usuario = usuarioService.buscarUsuarioPorId(id);
            usuario.setUsername(username);
            if (password != null && !password.isEmpty()) {
                usuario.setPassword(password);
            }
            usuario.setEmail(email);
            usuario.setRol(rol);
            usuario.setEstado(estado);

            usuarioService.actualizarUsuario(id, usuario);
            return "redirect:/home#usuarios";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/usuario/editar/" + id + "?error=" + e.getMessage();
        }
    }

    @GetMapping("/usuario/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/home";

        try {
            usuarioService.eliminarUsuario(id);
            return "redirect:/home#usuarios";
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