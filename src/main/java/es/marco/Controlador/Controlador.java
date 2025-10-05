package es.marco.Controlador;

import es.marco.Modelo.Persona;
import es.marco.dao.PersonaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.List;

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

        cargarPersonasDesdeDB();
    }

    @FXML
    private void agregarPersona() {
        String nombre = campoNombre.getText();
        String apellido = campoApellido.getText();
        LocalDate fecha = campoFecha.getValue();

        if (nombre == null || nombre.isEmpty() ||
                apellido == null || apellido.isEmpty() ||
                fecha == null) return;

        Persona nueva = new Persona(nombre, apellido, fecha);

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                personaDAO.insertar(nueva);
                return null;
            }
        };

        task.setOnSucceeded(event -> {
            datos.add(nueva);
            tablaPersonas.refresh();
            campoNombre.clear();
            campoApellido.clear();
            campoFecha.setValue(null);
        });

        task.setOnFailed(event -> task.getException().printStackTrace());

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void eliminarFilasSeleccionadas() {
        ObservableList<Persona> seleccionadas = tablaPersonas.getSelectionModel().getSelectedItems();

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                for (Persona p : seleccionadas) {
                    personaDAO.eliminarPorNombreApellido(p);
                }
                return null;
            }
        };

        task.setOnSucceeded(event -> {
            datos.removeAll(seleccionadas);
            tablaPersonas.refresh();
        });

        task.setOnFailed(event -> task.getException().printStackTrace());

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void restaurarFilas() {
        cargarPersonasDesdeDB();
    }

    private void cargarPersonasDesdeDB() {
        Task<ObservableList<Persona>> task = new Task<>() {
            @Override
            protected ObservableList<Persona> call() throws Exception {
                List<Persona> lista = personaDAO.listar();
                return FXCollections.observableArrayList(lista);
            }
        };

        task.setOnSucceeded(event -> {
            datos.setAll(task.getValue());
            tablaPersonas.refresh();
        });

        task.setOnFailed(event -> task.getException().printStackTrace());

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
