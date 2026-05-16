package com.example.despachoo.service;

import com.example.despachoo.model.DespachoModel;
import com.example.despachoo.repository.DespachoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DespachoServiceIMPL implements DespachoService {
    @Autowired
    private DespachoRepository despachoRepository;

    @Override
    public List<DespachoModel> listarTodos() {
        return despachoRepository.findAll();
    }

    @Override
    public Optional<DespachoModel> buscarPorId(Long id) {
        return despachoRepository.findById(id);
    }

    @Override
    public DespachoModel guardar(DespachoModel despacho) {
        return despachoRepository.save(despacho);
    }

    @Override
    public DespachoModel actualizar(Long id, DespachoModel despacho) {
        despacho.setId(id);
        return despachoRepository.save(despacho);
    }

    @Override
    public void eliminar(Long id) {
        despachoRepository.deleteById(id);
    }
}
