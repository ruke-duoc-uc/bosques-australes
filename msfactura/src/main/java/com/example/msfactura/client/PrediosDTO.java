package com.example.msfactura.client;

public record PrediosDTO(
        Long id,
        String nombre, // Asegúrate que se llame así en el JSON
        String ubicacion
) {}
