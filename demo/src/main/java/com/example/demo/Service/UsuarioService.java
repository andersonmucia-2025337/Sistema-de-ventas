package com.example.demo.Service;

import com.example.demo.Entity.Usuario;
import java.util.List;

public interface UsuarioService {
    List<Usuario> listarUsuarios();
    Usuario buscarUsuarioPorId(Integer codigoUsuario);
    Usuario buscarPorUsername(String username);
    Usuario guardarUsuario(Usuario usuario) throws RuntimeException;
    Usuario actualizarUsuario(Integer codigoUsuario, Usuario usuario) throws RuntimeException;
    void eliminarUsuario(Integer codigoUsuario) throws RuntimeException;
}