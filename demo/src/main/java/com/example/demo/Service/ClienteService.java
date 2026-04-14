package com.example.demo.Service;

import com.example.demo.Entity.Cliente;
import java.util.List;

public interface ClienteService {
    List<Cliente> listarClientes();
    Cliente buscarClientePorId(Integer dpiCliente);
    Cliente guardarCliente(Cliente cliente) throws RuntimeException;
    Cliente actualizarCliente(Integer dpiCliente, Cliente cliente) throws RuntimeException;
    void eliminarCliente(Integer dpiCliente) throws RuntimeException;
}