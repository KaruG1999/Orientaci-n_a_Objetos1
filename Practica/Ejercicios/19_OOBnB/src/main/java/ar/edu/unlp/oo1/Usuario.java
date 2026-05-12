package ar.edu.unlp.oo1;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String nombre;
    private String direccion;
    private String dni;
    private List<Propiedad> propiedades;

    public Usuario(String nombre, String direccion, String dni) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.dni = dni;
        this.propiedades = new ArrayList<>();
    }

    public String getNombre() { return nombre; }

    public void agregarPropiedad(Propiedad propiedad) {
        propiedades.add(propiedad);
    }

    // Crea una reserva como inquilino en una propiedad ajena
    // Retorna la Reserva si fue posible, null si no
    public Reserva reservar(Propiedad propiedad, DateLapse periodo) {
        // TODO: delegar en propiedad.reservar(this, periodo)
        throw new UnsupportedOperationException("Implementar");
    }
}
