package com.example.demo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_usuario")
    private Integer codigoUsuario;

    @NotBlank(message = "El username no puede estar vacío")
    @Size(max = 45, message = "El username no puede exceder los 45 caracteres")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(max = 45, message = "La contraseña no puede exceder los 45 caracteres")
    @Column(name = "password")
    private String password;

    @Email(message = "El email debe tener un formato válido")
    @Pattern(regexp = ".*@(gmail\\.com|email\\.com|yahoo\\.com)$",
            message = "El email debe ser: @gmail.com, @email.com o @yahoo.com")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "El rol no puede estar vacío")
    @Size(max = 45, message = "El rol no puede exceder los 45 caracteres")
    @Column(name = "rol")
    private String rol;

    @Column(name = "estado")
    private Integer estado;

    // Getters and Setters
    public Integer getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Integer codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}