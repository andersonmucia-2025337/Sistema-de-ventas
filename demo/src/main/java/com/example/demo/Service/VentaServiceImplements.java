package com.example.demo.Service;

import com.example.demo.Entity.Venta;
import com.example.demo.Repository.VentaRepository;
import com.example.demo.Repository.ClienteRepository;
import com.example.demo.Repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class VentaServiceImplements implements VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    public VentaServiceImplements(VentaRepository ventaRepository,
                                  ClienteRepository clienteRepository,
                                  UsuarioRepository usuarioRepository) {
        this.ventaRepository = ventaRepository;
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta buscarVentaPorId(Integer codigoVenta) {
        return ventaRepository.findById(codigoVenta)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + codigoVenta));
    }

    @Override
    public Venta guardarVenta(Venta venta) throws RuntimeException {
        try {
            venta.setFechaVenta(LocalDate.now());
            venta.setEstado(1);

            // Verificar que el cliente existe
            clienteRepository.findById(venta.getCliente().getDpiCliente())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            // Verificar que el usuario existe
            usuarioRepository.findById(venta.getUsuario().getCodigoUsuario())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            return ventaRepository.save(venta);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la venta: " + e.getMessage());
        }
    }

    @Override
    public Venta actualizarVenta(Integer codigoVenta, Venta ventaActualizada) throws RuntimeException {
        try {
            Venta venta = buscarVentaPorId(codigoVenta);
            venta.setFechaVenta(ventaActualizada.getFechaVenta());
            venta.setTotal(ventaActualizada.getTotal());
            venta.setEstado(ventaActualizada.getEstado());
            venta.setCliente(ventaActualizada.getCliente());
            venta.setUsuario(ventaActualizada.getUsuario());
            return ventaRepository.save(venta);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la venta: " + e.getMessage());
        }
    }

    @Override
    public void eliminarVenta(Integer codigoVenta) throws RuntimeException {
        try {
            ventaRepository.deleteById(codigoVenta);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la venta: " + e.getMessage());
        }
    }
}