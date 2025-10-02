package ar.edu.unlp.info.oo1;

import java.util.ArrayList;
import java.util.List;


public class Inversor {

	// private String nombre; //No menciona nombre en enunciado
	private List<Inversion> inversiones;  // Asociación 0..* en forma de lista

    public Inversor() {
        this.inversiones = new ArrayList<>();
    }

    public void agregarInversion(Inversion inv) {
        this.inversiones.add(inv);
    }

    public void sacarInversion(Inversion inv) {
        this.inversiones.remove(inv);
    }

    public double valorActualTotal() {
        return this.inversiones.stream()
                               .mapToDouble(Inversion::valorActual)
                               .sum();
    }

    public List<Inversion> getInversiones() {
        return this.inversiones;
    }
	
}
