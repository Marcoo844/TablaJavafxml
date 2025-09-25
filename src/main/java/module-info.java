module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;

    opens es.marco.Controlador to javafx.fxml; // Para los FXML controllers
    opens es.marco to javafx.graphics;        // Para la clase App principal
    exports es.marco;                        // opcional, si otros módulos necesitan acceder
}
