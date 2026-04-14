package com.example.demo.Service;

import com.example.demo.Entity.Usuario;
import com.example.demo.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioServiceImplements implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImplements(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarUsuarioPorId(Integer codigoUsuario) {
        return usuarioRepository.findById(codigoUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + codigoUsuario));
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) throws RuntimeException {
        try {
            usuario.setEstado(1);
            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el usuario: " + e.getMessage());
        }
    }

    @Override
    public Usuario actualizarUsuario(Integer codigoUsuario, Usuario usuarioActualizado) throws RuntimeException {
        try {
            Usuario usuario = buscarUsuarioPorId(codigoUsuario);
            usuario.setUsername(usuarioActualizado.getUsername());
            usuario.setPassword(usuarioActualizado.getPassword());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setRol(usuarioActualizado.getRol());
            usuario.setEstado(usuarioActualizado.getEstado());
            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    @Override
    public void eliminarUsuario(Integer codigoUsuario) throws RuntimeException {
        try {
            usuarioRepository.deleteById(codigoUsuario);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el usuario: " + e.getMessage());
        }
    }
}