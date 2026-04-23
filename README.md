# Proyecto Bosques Australes


# Indice
- [Instalaciones](#instalaciones)
- [Maquinaria](#maquinaria)
- [Procesos](#procesos)
- [Problemas](#problemas)
- [MicroServicios](#microservicios)

# Instalaciones
- La union
- Cancha de acopio Rio Bueno
- Cancha de acopio Osorno
# Maquinaria
- 3 cuadrillas de cosecha mecanizada
# Procesos
- Plantacion
- Talacion
- Acopio
- Transporte
- Comercializacion
# Problemas
- Trazabilidad de procedimientos
- Trazabilidad de rendimiento
- Coordinacion de procedimientos
- Falta de procedimientos
# MicroServicios
- ms-predios
- ms-especies
- ms-clientes
- ms-despacho
- ms-facturacion

  
|Microservicio|Contexto|
|-----|--------|
|Predios|Almacena informacion de los predios donde trabaja la empresa, sean propios o externos|
|Especies|Almacena informacion sobre las especies de árboles que se trabajan en la empresa, como sus planes de plantacion|
|Clientes|Se almacena informacion sobre los clientes, como nombres de usuario y contacto|

# Contenido y librerias de microservicios Springboot

Cada libreria debe tener una base de datos independiente para almacenar información. Entre las librerias

- Spring Web
- Spring JPA
- 