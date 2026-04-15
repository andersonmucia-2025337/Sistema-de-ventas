package com.example.demo.Controller;

import com.example.demo.Entity.Cliente;
import com.example.demo.Entity.DetalleVenta;
import com.example.demo.Entity.Producto;
import com.example.demo.Entity.Usuario;
import com.example.demo.Entity.Venta;
import com.example.demo.Service.ClienteService;
import com.example.demo.Service.DetalleVentaService;
import com.example.demo.Service.ProductoService;
import com.example.demo.Service.UsuarioService;
import com.example.demo.Service.VentaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private VentaService ventaService;

    @Autowired
    private DetalleVentaService detalleVentaService;

    @GetMapping("/")
    public String inicio() {
        return "redirect:/usuario";
    }

    @GetMapping("/usuario")
    public String mostrarLogin() {
        return "usuario";
    }

    @PostMapping("/login")
    public String login(@RequestParam String usuario,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        // Validar contra la base de datos
        Usuario usuarioBD = usuarioService.buscarPorUsername(usuario);

        if (usuarioBD != null && usuarioBD.getPassword().equals(password) && usuarioBD.getEstado() == 1) {
            session.setAttribute("usuarioLogueado", usuarioBD.getUsername());
            session.setAttribute("rol", usuarioBD.getRol());
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Usuario y contraseña incorrecta");
            return "usuario";
        }
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam String username,
                                   @RequestParam String password,
                                   @RequestParam String email,
                                   @RequestParam String rol,
                                   Model model) {
        try {
            Usuario usuarioExistente = usuarioService.buscarPorUsername(username);
            if (usuarioExistente != null) {
                model.addAttribute("error", "El usuario ya existe");
                return "registro";
            }

            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setUsername(username);
            nuevoUsuario.setPassword(password);
            nuevoUsuario.setEmail(email);
            nuevoUsuario.setRol(rol);
            nuevoUsuario.setEstado(1);

            usuarioService.guardarUsuario(nuevoUsuario);

            model.addAttribute("exito", "Usuario registrado exitosamente. Ahora puede iniciar sesión.");
            return "registro";
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar usuario: " + e.getMessage());
            return "registro";
        }
    }

    @GetMapping("/home")
    public String mostrarHome(HttpSession session, Model model) {
        // Validar la sesion
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/usuario";
        }

        String rol = (String) session.getAttribute("rol");
        boolean isAdmin = "administrador".equals(rol) || "admin".equals(rol);

        // Cargar todos los datos para las tablas
        model.addAttribute("clientes", clienteService.listarClientes());
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        model.addAttribute("productos", productoService.listarProductos());
        model.addAttribute("ventas", ventaService.listarVentas());
        model.addAttribute("detalles", detalleVentaService.listarDetalleVentas());
        model.addAttribute("isAdmin", isAdmin);

        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/usuario";
    }
}