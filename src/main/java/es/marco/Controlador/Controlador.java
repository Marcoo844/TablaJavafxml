package es.marco.Controlador;

import es.marco.Modelo.Persona;
import es.marco.dao.PersonaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Controlador {

    @FXML private TextField campoNombre;
    @FXML private TextField campoApellido;
    @FXML private DatePicker campoFecha;

    @FXML private TableView<Persona> tablaPersonas;
    @FXML private TableColumn<Persona, Number> colID;
    @FXML private TableColumn<Persona, String> colNombre;
    @FXML private TableColumn<Persona, String> colApellido;
    @FXML private TableColumn<Persona, LocalDate> colFecha;

    private ObservableList<Persona> datos = FXCollections.observableArrayList();
    private PersonaDAO personaDAO = new PersonaDAO();

    @FXML
    private void initialize() {
        colID.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(datos.indexOf(cellData.getValue()) + 1));

        colNombre.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre()));

        colApellido.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getApellido()));

        colFecha.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getFechaNacimiento()));

        tablaPersonas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaPersonas.setItems(datos);

        // Cargar datos desde la base de datos
        cargarDatosDesdeBD();
    }

    @FXML
    private void agregarPersona() {
        String nombre = campoNombre.getText();
        String apellido = campoApellido.getText();
        LocalDate fecha = campoFecha.getValue();

        if (nombre != null && !nombre.isEmpty() &&
                apellido != null && !apellido.isEmpty() &&
                fecha != null) {

            Persona p = new Persona(nombre, apellido, fecha);

            // Insertar de manera asíncrona en BD
            personaDAO.insertarPersonaAsync(p).thenRun(() -> {
                // Actualizar UI en hilo de JavaFX
                javafx.application.Platform.runLater(() -> {
                    datos.add(p);
                    tablaPersonas.refresh();
                });
            });

            campoNombre.clear();
            campoApellido.clear();
            campoFecha.setValue(null);
        }
    }

    @FXML
    private void eliminarFilasSeleccionadas() {
        datos.removeAll(tablaPersonas.getSelectionModel().getSelectedItems());
        tablaPersonas.refresh();
        // Podrías agregar eliminación en BD de manera similar si quieres
    }

    private void cargarDatosDesdeBD() {
        personaDAO.obtenerTodosAsync().thenAccept(lista -> {
            javafx.application.Platform.runLater(() -> {
                datos.setAll(lista);
                tablaPersonas.refresh();
            });
        });
    }
}
