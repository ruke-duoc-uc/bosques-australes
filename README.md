# BosquesAustrales -DSY1103 Desarrollo Full Stack 1

# Descripción de BosquesAustrales
BosquesAustrales es una empresa que realiza procesos de plantacion, manejo sílvicola, transporte y comercializacion tanto local como internacional de madera aserrada, astillas de eucalipto y astillas de pino radiata.
Esta empresa fue multada por el SAG al no presentar la documentación de origen de madera cosechada en predio de terceros, por ello se nos encargo brindar un sistema capaz de almacenar y obtener la información necesaria para la documentación de varios procesos relevantes que son regulados por la ley
# Solucion del problema
El repositorio almacena un proyecto de microservicios que almacenan informacion relevante para las regulaciones de negocio, como datos de predios, árboles y su metodos de trabajo, clientes, facturas, trabajadores etc.
El objetivo es facilitar el ingreso y consulta de dicha informacion para cumplir con las regulaciones del SAG y otras entidades reguladoras segun corresponda 
# Indice
- [MicroServicios](#microservicios)
- [Comunicaciones](#comunicaciones)
- [Metodos](#sintaxtis-de-metodos)

# Equipo
| Nombre          | GitHub       |
|-----------------|--------------|
|Isidora Ayala    | isiayala     |
|Cristobal Loncon | ruke-duoc-uc |
|Alvaro Oyarzun   | Alvarooyar   |

# Microoservicios Implementados

| # | Microoservicio   | Puerto | Descripción                               | Responsable |
|---|------------------|--------|-------------------------------------------| --- |
| 1 | ms-predios      | 8080   | instalaciones donde opera la empresa      | Cristobal |
| 2 | ms-cliente      | 8081   | maneja información de contratos, etc      | Alvaro |
| 3 | ms-especies     | 8082   | identifica al árbol y sus procesos        | Cristobal |
| 4 | ms-despacho     | 8083   | guias de despachos y transportistas       | Isidora |
| 5 | ms-facturación  | 8084   | factura cobros por volumen entregado      | Cristobal |
| 6 | ms-seguridad    | 8085   | cumplir estandares de mutual de seguridad | Alvaro |
| 7 | ms-trabajadores | 8086   | realizar labores asignadas en el proceso  | Isidora |
| 8 | ms-acopio       | 8087   | Maneja informacion sobre el stock         | Isidora |
| 9 | ms-cuadrillas   | 8088   | Division de equipos y responsabilidades   | Alvaro |
| 10 | ms-planCosecha | 8089   | Planificación de cosecha por rodal y temporada | Cristobal |
| 11 | gateway        | 8090   | Centraliza las consultas de los microservicios | Cristobal - Alvaro - Isidora |

# Dependencias Utilizadas en los microservicios
Microservicios
|Dependencia|Descripción|
|---|---|
|Validation|Valida que los atributos de los objetos no esten vacios|
|JPA|Metodos de comunicacion con H2|
|Jacoco|Cobertura de metodos|
|H2|Base de datos ligera con persistencia|
|Swagger|Herramienta de documentacion para metodos de microservicios|
Gateway
|Reactive GateWay|Enrutacion de API de microservicios|
|Reactive Web||
# Formato de titulo en Commit
| Tipo      | Cuando usarlo                           | Ejemplo                                              |
|-----------|-----------------------------------------|------------------------------------------------------|
|feat:      | nueva funcionalidad                     | feat: descripción de la nueva funcionalidad          |
|fix :      | correción de error                      | fix: describir la corrección de error que se realizó |
|docs:      | cambios en documentación                | docs: describir los cambios realizados               |
|refactor:  | mejora código sin cambiar funcionalidad | refactor: describir la parte del código              |
|test:      | agregar o modificar tests               | tests: describir la modificación                     |
|config:    | configuración de proyecto               | config: describir el cambio en la configuración      |

# Comunicaciones
|Receptor de informacion|Emisor/es|
|:---|:---:|
|msfactura|msclientes / mspredios|
|mscuadrilla|mstrabajadores|
|msplanCosecha|msespecies|
|msseguridad|mstrabajadores|
# Rutas de comunicación
Ya que el proyecto centraliza las consultas en un Gateway, se utiliza una direccion base, a la cual se le suma  un cuerpo en la direccion para comunicarse con un microsevicio especifico
```text
http://localhost:8090
```
|Microservicio|Direccion completa|Cuerpo|
|---|---|---|
|msespecie|http://localhost:8090/api/especies|/api/especies|
|mspredios|http://localhost:8090/api/predios|/api/predios|
|msfactura|http://localhost:8090/api/factura|/api/factura|
|msplanCosecha|http://localhost:8090/api/planCosecha|/api/planCosecha|

# Sintaxtis de metodos
|Metodo|Funcion|Tipo en Service|
|---|---|---|
|listarNombreClase|Mostrar todos los objetos del microservicio|List<NombreClase>|
|buscarPorId|Mostrar un objeto en especifico|NombreClase|
|guardarNombreClase|Agregar un objeto al microservicio|NombreClase|
|actualizarNombreClase|Cambiar los atributos de un objeto|Optional<NombreClase>|
|actualizarNombreClaseCompleto|Cambiar los atributos de un objeto, incluyendo los establecidos por otro microservicio|Optional<NombreClase>
|eliminarNombreClase|Eliminar un objeto|void|
|existePorId|Metodo Boolean usado para reportar que no se encontro un objeto con la id otorgada|Boolean| 