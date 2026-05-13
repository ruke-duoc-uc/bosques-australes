package com.example.cliente.service;

import com.example.cliente.model.*;
import com.example.cliente.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarClientes(){
        return clienteRepository.findAll();
    };

    public Cliente guardarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public List<Cliente> buscarPorActividad(boolean estado){
        return clienteRepository.findByEstado(true);
    }

    public List<Cliente> buscarPorNombre(String nombre){
        return clienteRepository.findByNombre(nombre);
    }


    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }
}
