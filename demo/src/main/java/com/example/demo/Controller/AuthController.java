package com.example.demo.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    // Inicio del login
    @GetMapping("/")
    public String inicio() {
        return "redirect:/usuario";
    }

    // Mostrar Login
    @GetMapping("/usuario")
    public String mostrarLogin() {
        return "usuario";
    }

    // Procesar Login
    @PostMapping("/login")
    public String login(@RequestParam String usuario,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        String userCorrecto = "admin";
        String passCorrecto = "1234";

        if (usuario.equals(userCorrecto) && password.equals(passCorrecto)) {
            // Guardar sesion
            session.setAttribute("usuarioLogueado", usuario);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Usuario y contraseña incorrecta");
            return "usuario";
        }
    }

    // Proteger ruta sin spring security
    @GetMapping("/home")
    public String mostrarHome(HttpSession session) {
        // Validar la sesion
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/usuario";
        }
        return "home";
    }

    // Cerrar sesion
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/usuario";
    }
}