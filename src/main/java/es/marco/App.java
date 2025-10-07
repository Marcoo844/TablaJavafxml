package es.marco;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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

        Locale locale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

        try {
            URL fxmlUrl = getClass().getResource("/FXML/fxml.fxml");
            if (fxmlUrl == null) {
                System.err.println("No se encontró el archivo FXML");
                return;
            }
            FXMLLoader loader = new FXMLLoader(fxmlUrl, bundle);
            Parent root = loader.load();

            Scene scene = new Scene(root, 700, 400);
            primaryStage.setTitle(bundle.getString("TablaFXML"));
            primaryStage.setScene(scene);

            // Carga icono y lo asigna a la ventana
            Image icon = new Image(getClass().getResourceAsStream("/iconos/tabla.png"));
            primaryStage.getIcons().add(icon);

            primaryStage.setMinWidth(700);
            primaryStage.setMinHeight(400);

            primaryStage.show();
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



