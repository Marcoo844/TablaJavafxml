package es.marco.Modelo;


import java.time.LocalDate;


/**
 * Representa a una persona con nombre, apellido y fecha de nacimiento.
 */
public class Persona {
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;


    public Persona(String nombre, String apellido, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
    }


    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }


    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }


    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }


    @Override
    public String toString() {
        return nombre + " " + apellido + " (" + fechaNacimiento + ")";
    }
}