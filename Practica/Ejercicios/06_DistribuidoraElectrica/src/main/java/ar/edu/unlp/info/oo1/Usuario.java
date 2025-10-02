package ar.edu.unlp.info.oo1;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	
    private String nombre;
    private String domicilio;
    private List<Consumo> consumos;

    public Usuario(String nombre, String domicilio) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.consumos = new ArrayList<>();
    }

    public void agregarConsumo(Consumo consumo) {
        this.consumos.add(consumo);
    }

    public Consumo getUltimoConsumo() {
        if (this.consumos.isEmpty()) return null;
        return this.consumos.get(this.consumos.size() - 1);
    }

    public String getNombre() {
        return nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }
}

