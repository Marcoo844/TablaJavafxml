module org.example {
    // --- JavaFX ---
    requires javafx.controls;
    requires javafx.fxml;

    // --- Base de datos (JDBC) ---
    requires java.sql;

    // --- Logging ---
    requires org.slf4j;
    requires ch.qos.logback.classic;

    // --- Aperturas necesarias para JavaFX ---
    opens es.marco.Controlador to javafx.fxml; // para los controladores FXML
    opens es.marco to javafx.graphics;         // para la clase App principal

    // --- Exportaciones ---
    exports es.marco;                          // para acceso desde otros módulos (opcional)
    exports es.marco.Controlador;
    exports es.marco.Modelo;
}
