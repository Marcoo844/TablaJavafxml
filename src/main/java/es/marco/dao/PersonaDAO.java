package es.marco.dao;

import es.marco.Modelo.Persona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Persona.
 * Todas las operaciones son síncronas internamente, pero se usarán en hilos separados desde JavaFX.
 */
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

    private static final String INSERT =
            "INSERT INTO persona(nombre, apellido, fecha_nacimiento) VALUES (?, ?, ?)";

    private static final String SELECT_ALL =
            "SELECT nombre, apellido, fecha_nacimiento FROM persona";

    private static final String DELETE_BY_ID =
            "DELETE FROM persona WHERE id = ?";

    public PersonaDAO() {
        // Crear tabla si no existe
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_TABLE);
        } catch (SQLException e) {
            logger.error("Error creando tabla persona", e);
        }
    }

    public void insertar(Persona p) throws SQLException {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setDate(3, Date.valueOf(p.getFechaNacimiento()));
            ps.executeUpdate();
        }
    }

    public List<Persona> listar() throws SQLException {
        List<Persona> lista = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                LocalDate fecha = rs.getDate("fecha_nacimiento").toLocalDate();
                lista.add(new Persona(nombre, apellido, fecha));
            }
        }
        return lista;
    }

    // Si quieres eliminar por ID, primero deberías almacenar el ID en Persona
    public void eliminarPorNombreApellido(Persona p) throws SQLException {
        String sql = "DELETE FROM persona WHERE nombre = ? AND apellido = ? AND fecha_nacimiento = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setDate(3, Date.valueOf(p.getFechaNacimiento()));
            ps.executeUpdate();
        }
    }
}
