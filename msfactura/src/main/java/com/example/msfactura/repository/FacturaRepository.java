package com.example.msfactura.repository;
import com.example.msfactura.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.client.RestClient;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
}
