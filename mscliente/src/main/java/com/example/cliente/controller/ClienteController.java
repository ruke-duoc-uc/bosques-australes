package com.example.cliente.controller;
import com.example.cliente.dto.ClienteRequestDto;
import com.example.cliente.dto.ClienteResponseDto;
import com.example.cliente.model.Cliente;
import com.example.cliente.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> listaClientes() {
        List<ClienteResponseDto> respuesta = clienteService.listarClientes().stream()
                .map(this::convertirAResponseDto)
                .toList();

        if (respuesta.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> buscarPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.obtenerPorId(id);
        return ResponseEntity.ok(convertirAResponseDto(cliente));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ClienteResponseDto>> buscarPorNombre(@PathVariable String nombre) {
        List<ClienteResponseDto> clientes = clienteService.buscarPorNombre(nombre).stream()
                .map(this::convertirAResponseDto)
                .toList();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ClienteResponseDto>> buscarPorEstado(@PathVariable boolean estado) {
        List<ClienteResponseDto> clientes = clienteService.buscarPorEstado(estado).stream()
                .map(this::convertirAResponseDto)
                .toList();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDto> crearCliente(@Valid @RequestBody ClienteRequestDto dto) {
        Cliente cliente = new Cliente();
        mapearDtoAEntidad(dto, cliente);

        Cliente guardado = clienteService.guardarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertirAResponseDto(guardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequestDto dto) {
        Cliente clienteExistente = clienteService.obtenerPorId(id);
        mapearDtoAEntidad(dto, clienteExistente);

        Cliente actualizado = clienteService.actualizarCliente(clienteExistente);
        return ResponseEntity.ok(convertirAResponseDto(actualizado));
    }

    // --- MÉTODOS AUXILIARES DE MAPEO (MANUAL) ---
    private ClienteResponseDto convertirAResponseDto(Cliente cliente) {
        ClienteResponseDto dto = new ClienteResponseDto();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setRut(cliente.getRut());
        dto.setRazonSocial(cliente.getRazonSocial());
        dto.setDireccion(cliente.getDireccion());
        dto.setComuna(cliente.getComuna());
        dto.setCiudad(cliente.getCiudad());
        dto.setTelefono(cliente.getTelefono());
        dto.setEmail(cliente.getEmail());
        dto.setTipoCliente(cliente.getTipoCliente());
        dto.setEstado(cliente.getEstado());
        return dto;
    }

    private void mapearDtoAEntidad(ClienteRequestDto dto, Cliente cliente) {
        cliente.setNombre(dto.getNombre());
        cliente.setRut(dto.getRut());
        cliente.setRazonSocial(dto.getRazonSocial());
        cliente.setDireccion(dto.getDireccion());
        cliente.setComuna(dto.getComuna());
        cliente.setCiudad(dto.getCiudad());
        cliente.setTelefono(dto.getTelefono());
        cliente.setEmail(dto.getEmail());
        cliente.setTipoCliente(dto.getTipoCliente());
        cliente.setEstado(dto.getEstado());
    }
}