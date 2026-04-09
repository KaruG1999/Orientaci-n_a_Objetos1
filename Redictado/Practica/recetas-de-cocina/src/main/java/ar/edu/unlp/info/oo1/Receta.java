package ar.edu.unlp.info.oo1;

import java.util.ArrayList;
import java.util.List;

public class Receta {

    private String nombre;
    private List<Componente> componentes;

    public Receta(String nombre) {
        this.nombre = nombre;
        this.componentes = new ArrayList<>();
    }

    public void agregarComponente(Componente componente) {
        this.componentes.add(componente);
    }

    public String getDescripcion() {
        String descripcion = "Receta \"" + nombre + "\"";
        int numero = 1;
        for (Componente c : componentes) {
            descripcion += "\n" + numero + ". " + c.getDescripcion();
            numero++;
        }
        return descripcion;
    }

    public double getCostoEstimado() {
        double total = 0.0;
        for (Componente c : componentes) {
            total += c.getCostoEstimado();
        }
        return total;
    }
}
