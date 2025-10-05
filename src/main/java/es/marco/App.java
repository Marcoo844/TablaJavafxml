package es.marco;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/fxml.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 700, 400);


        primaryStage.setTitle(bundle.getString("TablaFXML"));
        primaryStage.setScene(scene);
        primaryStage.show();


        logger.info("Interfaz mostrada");
    }


    public static void main(String[] args) {
        launch(args);
    }
}



