package ar.edu.unlp.oo1;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String nombre;
    private List<Propiedad> propiedades;

    public Usuario(String nombre) {
        this.nombre = nombre;
        this.propiedades = new ArrayList<>();
    }

    public String getNombre() { return nombre; }

    public void agregarPropiedad(Propiedad propiedad) {
        propiedades.add(propiedad);
    }

    public Reserva reservar(Propiedad propiedad, DateLapse periodo) {
        return propiedad.reservar(this, periodo);
    }
}
