package com.example.cliente.repository;

import com.example.cliente.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * CAPA DE PERSISTENCIA (REPOSITORIO)
 * * Interfaz que extiende de JpaRepository para heredar de forma automática
 * todas las operaciones CRUD básicas (save, findAll, delete, findById) hacia la BD H2/PostgreSQL.
 * Aquí también se declaran las consultas personalizadas (Query Methods) específicas del negocio.
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    /**
     * ¿Qué hace?: Filtra los registros de clientes según su disponibilidad operativa en el sistema.
     * ¿Para qué sirve?: Permite traer solo los clientes habilitados (true) o deshabilitados (false).
     * @param estado Valor booleano de búsqueda (true = Activo / false = Inactivo).
     * @return Una lista (List) de entidades 'Cliente' que coincidan con el estado indicado.
     */
    List<Cliente> findByEstado(boolean estado);
    /**
     * ¿Qué hace?: Realiza una búsqueda parcial y predictiva por el nombre del cliente.
     * ¿Para qué sirve?: Actúa como un buscador dinámico. La palabra clave 'Containing' aplica un operador
     * 'LIKE %texto%' y 'IgnoreCase' transforma todo a minúsculas para ignorar acentos o diferencias de tipeo.
     * @param nombre Cadena de texto o fragmento del nombre que se desea buscar.
     * @return Lista de clientes cuyo nombre contenga la secuencia de texto ingresada.
     */
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
    /**
     * ¿Qué hace?: Comprueba la existencia previa de un número de identificación fiscal (RUT) en las tablas.
     * ¿Para qué sirve?: Es la validación clave que utiliza el servicio para impedir el registro
     * de dos clientes distintos con el mismo RUT, evitando colisiones de datos relacionales.
     * @param rut El RUT corporativo en formato String que se quiere validar.
     * @return 'true' si el RUT ya está ocupado en la BD, o 'false' si el camino está libre para registrar.
     */
    boolean existsByRut(String rut); // Para evitar duplicados
}
