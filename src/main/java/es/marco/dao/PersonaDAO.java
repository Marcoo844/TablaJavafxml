package es.marco.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DAO para la entidad Persona.
 * Proporciona métodos para interactuar con la tabla persona en la base de datos.
 */
public class PersonaDAO {
    private static final Logger logger = LoggerFactory.getLogger(PersonaDAO.class);

    /**
     * SQL para crear la tabla persona si no existe.
     */
    private static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS persona (
            id INT AUTO_INCREMENT PRIMARY KEY,
            nombre VARCHAR(100) NOT NULL,
            apellido VARCHAR(100) NOT NULL,
            fecha_nacimiento DATE NOT NULL
            ) ENGINE=InnoDB;
            """;

    /**
     * SQL para insertar una nueva persona.
     */
    private static final String INSERT =
            "INSERT INTO persona(nombre, apellido, fecha_nacimiento) VALUES (?, ?, ?)";

    // Aquí irán los métodos para CRUD (crear, leer, actualizar, eliminar)
}
