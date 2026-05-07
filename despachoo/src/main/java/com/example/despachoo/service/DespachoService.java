package com.example.despachoo.service;

import com.example.despachoo.model.DespachoModel;
import java.util.List;
import java.util.Optional;

public interface DespachoService {
    List<DespachoModel> listarTodos();
    Optional<DespachoModel> buscarPorId(Long id);
    DespachoModel guardar(DespachoModel despacho);
    DespachoModel actualizar(Long id, DespachoModel despacho);
    void eliminar(Long id);
}
