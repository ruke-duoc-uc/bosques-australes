package com.example.msfactura.repository;
import com.example.msfactura.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    private Factura actualizarFactura(Factura factura){

        return
    }
}
