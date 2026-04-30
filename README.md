## BosquesAustrales -DSY1103 Desarrollo Full Stack 1

## Descripción
-Empezamos ordenando los microoservicios, para luego empezar a desarrollar las posibles soluciones al problema.
-El problema que vamos a resolver es que: La empresa no cuenta con la documentación de origen de la madera cosechada por lo cual fué multada y estan pidiendo un sistema que emita certificados de origen trazados desde el rodal hasta el cliente final.
# -Instalaciones
-  Aserradera La union
- Cancha de acopio Rio Bueno
- Cancha de acopio Osorno
# -Maquinaria
- 3 cuadrillas de cosecha mecanizada
# -Procesos
- Plantacion
- Talacion
- Acopio
- Transporte
- Comercializacion
# -Problemas
- Trazabilidad de procedimientos
- Trazabilidad de rendimiento
- Coordinacion de procedimientos
- Falta de procedimientos
- Falta de documentación de origen de los árboles


## Equipo
| Nombre          | GitHub       |
|-----------------|--------------|
|Isidora Ayala    | isiayala     |
|Cristobal Loncon | ruke-duoc-uc |
|Alvaro Oyarzun   | Alvarooyar   |

## Microoservicios Implementados
| # | Microoservicio   | Puerto | Descripción                               | Desarrollador |
|---|------------------|--------|-------------------------------------------|---------------|
| 1 | mas-predios      | 8080   | instalaciones donde opera la empresa      |  Cristóbal    |
| 2 | mas-cliente      | 8081   | maneja información de contratos, etc      |  Álvaro       |
| 3 | mas-especies     | 8082   | identifica al árbol y sus procesos        |               |
| 4 | mas-despacho     | 8083   | guias de despachos y transportistas       |  Isidora      |
| 5 | mas-facturación  | 8084   | factura cobros por volumen entregado      |  Cristóbal    |
| 6 | mas-seguridad    | 8085   | cumplir estandares de mutual de seguridad |  Álvaro       |
| 7 | mas-trabajadores | 8086   | realizar labores asignadas en el proceso  |  Isidora      |

## Tecnologías Utilizadas
- Java 17 /Spring Boot 3.x
- JPA 
- MySQL / H2
- Feign Client
- SLF4J para logs
- Flayway Migration
# Como se usa el Flyway
- El nombre debe tener V con un numero dos barras bajas y un nombre, es un punto sql.
- Ejemplo: V1__nombrecualquiera.sql
- Esto es para los versionamientos de flyway.

## Cómo Ejecutar el proyecto
1. Clonar el repositorio: `git clone [URL]`
2. Configurar la base de datos en `application.properties`
3. Ejecutar cada microoservicio: `./mvnw spring-boot:run`

## Estado del proyecto 
- En desarrollo 

## Clonar el repositorio 
git clone https://github.com/ruke-duoc-uc/bosques-australes.git

# Entrar al directorio 
cd [nombre-repo]

# Verificar la conexión
git status

# Que commits usar en cada situación 
| Tipo      | Cuando usarlo                           | Ejemplo                                              |
|-----------|-----------------------------------------|------------------------------------------------------|
|feat:      | nueva funcionalidad                     | feat: descripción de la nueva funcionalidad          |
|fix :      | correción de error                      | fix: describir la corrección de error que se realizó |
|docs:      | cambios en documentación                | docs: describir los cambios realizados               |
|refactor:  | mejora código sin cambiar funcionalidad | refactor: describir la parte del código              |
|test:      | agregar o modificar tests               | tests: describir la modificación                     |
|config:    |configuración de proyecto                | config: describir el cambio en la configuración      |
