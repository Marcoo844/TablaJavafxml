package es.marco;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    @Override
    public void start(Stage stage) {
        logger.info("Lanzando la aplicación");
        logger.debug("Depuración mostrada");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/fxml.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Tabla");
            stage.setScene(scene);

            stage.setMinWidth(300);
            stage.setMinHeight(350);

            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}