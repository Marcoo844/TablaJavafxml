package es.marco.Controlador;

import es.marco.Modelo.Persona;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

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

    @FXML
    private void initialize() {
        // Columna ID automática
        colID.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(datos.indexOf(cellData.getValue()) + 1)
        );

        colNombre.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre())
        );
        colApellido.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getApellido())
        );
        colFecha.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getFechaNacimiento())
        );
        tablaPersonas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tablaPersonas.setItems(datos);

        // Datos iniciales
        restaurarFilas();
    }

    @FXML
    private void agregarPersona() {
        String nombre = campoNombre.getText();
        String apellido = campoApellido.getText();
        LocalDate fecha = campoFecha.getValue();

        if (nombre != null && !nombre.isEmpty() &&
                apellido != null && !apellido.isEmpty() &&
                fecha != null) {

            datos.add(new Persona(nombre, apellido, fecha));
            tablaPersonas.refresh(); // actualizar columna ID

            campoNombre.clear();
            campoApellido.clear();
            campoFecha.setValue(null);
        }
    }

    @FXML
    private void eliminarFilasSeleccionadas() {
        ObservableList<Persona> seleccionadas = tablaPersonas.getSelectionModel().getSelectedItems();
        datos.removeAll(seleccionadas);
        tablaPersonas.refresh(); // actualizar IDs
    }

    @FXML
    private void restaurarFilas() {
        datos.clear();
        datos.add(new Persona("Junior", "Buchanan", LocalDate.of(2012, 10, 11)));
        datos.add(new Persona("Mauro", "Gonzalez", LocalDate.of(1980, 1, 10)));
        tablaPersonas.refresh(); // actualizar IDs
    }
}
