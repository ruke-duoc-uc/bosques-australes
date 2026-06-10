package com.example.msplanCosecha.client;

public record PlanCosechaPatch(
        Long idEspecie,
        Double alturaPromedio,
        Long edadRodal,
        String descripcion
) {}