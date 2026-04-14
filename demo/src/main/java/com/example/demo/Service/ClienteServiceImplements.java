package com.example.demo.Service;

import com.example.demo.Entity.Cliente;
import com.example.demo.Repository.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteServiceImplements implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImplements(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarClientePorId(Integer dpiCliente) {
        return clienteRepository.findById(dpiCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con DPI: " + dpiCliente));
    }

    @Override
    public Cliente guardarCliente(Cliente cliente) throws RuntimeException {
        try {
            cliente.setEstado(1);
            return clienteRepository.save(cliente);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el cliente: " + e.getMessage());
        }
    }

    @Override
    public Cliente actualizarCliente(Integer dpiCliente, Cliente clienteActualizado) throws RuntimeException {
        try {
            Cliente cliente = buscarClientePorId(dpiCliente);
            cliente.setNombreCliente(clienteActualizado.getNombreCliente());
            cliente.setApellidoCliente(clienteActualizado.getApellidoCliente());
            cliente.setDireccion(clienteActualizado.getDireccion());
            cliente.setEstado(clienteActualizado.getEstado());
            return clienteRepository.save(cliente);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el cliente: " + e.getMessage());
        }
    }

    @Override
    public void eliminarCliente(Integer dpiCliente) throws RuntimeException {
        try {
            clienteRepository.deleteById(dpiCliente);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el cliente: " + e.getMessage());
        }
    }
}