package es.marco.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utilidad para obtener conexiones JDBC a MariaDB.
 * Intenta obtener configuración desde variables de entorno y,
 * si no están disponibles, carga desde el archivo db.properties en recursos.
 */
public class DBUtil {
    private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
    private static String url;
    private static String user;
    private static String pass;

    static {
        // Primero intentar variables de entorno
        url = System.getenv("DB_URL");
        user = System.getenv("DB_USER");
        pass = System.getenv("DB_PASS");

        if (url == null || user == null) {
            // Cargar desde properties
            try (InputStream in = DBUtil.class.getResourceAsStream("/db.properties")) {
                if (in != null) {
                    Properties p = new Properties();
                    p.load(in);
                    url = p.getProperty("db.url");
                    user = p.getProperty("db.user");
                    pass = p.getProperty("db.pass");
                } else {
                    logger.warn("db.properties no encontrado en resources y variables de entorno no definidas");
                }
            } catch (Exception e) {
                logger.error("Error leyendo db.properties", e);
            }
        }
    }

    /**
     * Obtiene una conexión JDBC a la base de datos MariaDB
     *
     * @return conexión a la base de datos
     * @throws SQLException si los datos de conexión no están configurados o falla la conexión
     */
    public static Connection getConnection() throws SQLException {
        if (url == null || user == null) {
            throw new SQLException("Datos de conexión no configurados (DB_URL/DB_USER o db.properties)");
        }
        logger.debug("Obteniendo conexión a DB: {}", url);
        return DriverManager.getConnection(url, user, pass);
    }
}
