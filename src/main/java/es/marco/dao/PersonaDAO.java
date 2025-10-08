package es.marco.dao;

import es.marco.Modelo.Persona;
import es.marco.conexion.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PersonaDAO {
    private static final Logger logger = LoggerFactory.getLogger(PersonaDAO.class);

    private static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS persona (
                id INT AUTO_INCREMENT PRIMARY KEY,
                nombre VARCHAR(100) NOT NULL,
                apellido VARCHAR(100) NOT NULL,
                fecha_nacimiento DATE NOT NULL
            ) ENGINE=InnoDB;
            """;

    private static final String INSERT = "INSERT INTO persona(nombre, apellido, fecha_nacimiento) VALUES (?, ?, ?)";
    private static final String SELECT_ALL = "SELECT nombre, apellido, fecha_nacimiento FROM persona";

    public PersonaDAO() {
        // Crear tabla si no existe
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_TABLE);
        } catch (SQLException e) {
            logger.error("Error creando tabla persona", e);
        }
    }

    // Inserta una persona de forma asíncrona
    public CompletableFuture<Void> insertarPersonaAsync(Persona p) {
        return DBUtil.getConnectionAsync().thenAccept(conn -> {
            try (conn;
                 PreparedStatement ps = conn.prepareStatement(INSERT)) {
                ps.setString(1, p.getNombre());
                ps.setString(2, p.getApellido());
                ps.setDate(3, Date.valueOf(p.getFechaNacimiento()));
                ps.executeUpdate();
            } catch (SQLException e) {
                logger.error("Error insertando persona", e);
            }
        });
    }

    // Obtiene todas las personas de forma asíncrona
    public CompletableFuture<List<Persona>> obtenerTodosAsync() {
        return DBUtil.getConnectionAsync().thenApply(conn -> {
            List<Persona> lista = new ArrayList<>();
            try (conn;
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(SELECT_ALL)) {
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    LocalDate fecha = rs.getDate("fecha_nacimiento").toLocalDate();
                    lista.add(new Persona(nombre, apellido, fecha));
                }
            } catch (SQLException e) {
                logger.error("Error obteniendo personas", e);
            }
            return lista;
        });
    }
}
