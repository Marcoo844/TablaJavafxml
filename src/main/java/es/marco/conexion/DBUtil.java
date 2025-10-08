package es.marco.conexion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class DBUtil {
    private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
    private static String url;
    private static String user;
    private static String pass;

    static {
        url = System.getenv("DB_URL");
        user = System.getenv("DB_USER");
        pass = System.getenv("DB_PASS");

        if (url == null || user == null) {
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

    // Conexión síncrona
    public static Connection getConnection() throws SQLException {
        if (url == null || user == null) {
            throw new SQLException("Datos de conexión no configurados");
        }
        logger.debug("Obteniendo conexión a DB: {}", url);
        return DriverManager.getConnection(url, user, pass);
    }

    // Conexión asíncrona usando CompletableFuture
    public static CompletableFuture<Connection> getConnectionAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
