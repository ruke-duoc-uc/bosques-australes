package com.example.msfactura.client;

public record ClientesDTO(
        String razonSocial,
        String comuna,
        String ciudad,
        String telefono
) {}
