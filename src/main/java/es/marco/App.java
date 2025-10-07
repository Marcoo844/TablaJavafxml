package es.marco;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Launcher principal de la aplicación.
 */
public class App extends Application {

    private static final Logger logger = LoggerFactory.getLogger(App.class);


    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("Arrancando aplicación");


// Detect locale y cargar resource bundle
        Locale locale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);


        try {
            URL fxmlUrl = getClass().getResource("/FXML/fxml.fxml");
            if (fxmlUrl == null) {
                System.err.println("No se encontró el archivo FXML");
                return;  // o lanza excepción
            }
            FXMLLoader loader = new FXMLLoader(fxmlUrl, bundle);
            Parent root = loader.load();

            Scene scene = new Scene(root, 700, 400);
            primaryStage.setTitle(bundle.getString("TablaFXML"));
            primaryStage.setScene(scene);
            primaryStage.show();

            // Permitir agrandar, pero no reducir más pequeño que 700x400
            primaryStage.setMinWidth(700);
            primaryStage.setMinHeight(400);

            logger.info("Interfaz mostrada");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error cargando interfaz", e);
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}



