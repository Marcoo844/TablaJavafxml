package es.marco.Modelo;

import java.time.LocalDate;

/**
 * Creación de la clase Persona
 */
public class Persona {

    /**
     * Declaración de los atributos
     */
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;

    /**
     * Contructor principal
     * @param nombre
     * @param apellido
     * @param fechaNacimiento
     */
    public Persona(String nombre, String apellido, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Getter de nombre
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Setter de nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Getter de apellido
     * @return apellido
     */
    public String getApellido() {
        return apellido;
    }
    /**
     * Setter de apellido
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    /**
     * Getter de Fecha de Nacimiento
     * @return fechaNacimiento
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    /**
     * Setter de Fecha de Nacimiento
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
