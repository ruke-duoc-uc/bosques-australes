## BosquesAustrales -DSY1103 Desarrollo Full Stack 1

## Descripción
BosquesAustrales es una empresa que realiza procesos de plantacion, manejo sílvicola, transporte y comercializacion tanto local como internacional de madera aserrada, astillas de eucalipto y astillas de pino radiata.
Esta empresa fue multada por el SAG al no presentar la documentación de origen de madera cosechada en predio de terceros, por ello se nos encargo brindar un sistema capaz de almacenar y obtner la información necesaria para la documentación de varios procesos relevantes que son regulados por la ley

# Indice
- [MicroServicios](#microservicios)
- [Comunicaciones](#comunicaciones)
- [Metodos](#sintaxtis-de-metodos)

## Equipo
| Nombre          | GitHub       |
|-----------------|--------------|
|Isidora Ayala    | isiayala     |
|Cristobal Loncon | ruke-duoc-uc |
|Alvaro Oyarzun   | Alvarooyar   |

## Microoservicios Implementados
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

## Tecnologías Utilizadas
|Dependencia|Descripción|
|---|---|
|Spring Web||
- JPA 
- H2
- Flyway Migration

# Como se usa el Flyway
- El nombre debe tener V con un numero dos barras bajas y un nombre, es un punto sql.
- Ejemplo: V1__nombrecualquiera.sql
- Esto es para los versionamientos de flyway.

## Clonar el repositorio 

```
git clone https://github.com/ruke-duoc-uc/bosques-australes.git
```

# Verificar la conexión

```text
git status
```

# Formato de titulo en Commit
| Tipo      | Cuando usarlo                           | Ejemplo                                              |
|-----------|-----------------------------------------|------------------------------------------------------|
|feat:      | nueva funcionalidad                     | feat: descripción de la nueva funcionalidad          |
|fix :      | correción de error                      | fix: describir la corrección de error que se realizó |
|docs:      | cambios en documentación                | docs: describir los cambios realizados               |
|refactor:  | mejora código sin cambiar funcionalidad | refactor: describir la parte del código              |
|test:      | agregar o modificar tests               | tests: describir la modificación                     |
|config:    |configuración de proyecto                | config: describir el cambio en la configuración      |

# Comunicaciones
|Receptor de informacion|Emisor/es|
|:---|:---:|
|msfactura|msclientes / mspredios|
|mscuadrilla|mstrabajadores|
|msplanCosecha|msespecies|
|msseguridad|mstrabajadores|

# Sintaxtis de metodos
|Metodo|Funcion|Tipo en Service|
|---|---|---|
|listarNombreClase|Mostrar todos los objetos del microservicio|List<NombreClase>|
|buscarPorId|Mostrar un objeto en especifico|NombreClase|
|guardarNombreClase|Agregar un objeto al microservicio|NombreClase|
|actualizarNombreClase|Cambiar los atributos de un objeto|Optional<NombreClase>|
|actualizarNombreClaseCompleto|Cambiar los atributos de un objeto, incluyendo los establecidos por otro microservicio|Optional<NombreClase>
|eliminarNombreClase|Eliminar un objeto|void|
|existePorId|Metodo Boolean usado para manejar errores 404 Not Found|Boolean| 