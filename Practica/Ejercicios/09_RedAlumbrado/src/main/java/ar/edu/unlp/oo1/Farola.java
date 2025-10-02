package ar.edu.unlp.oo1;

import java.util.ArrayList;
import java.util.List;

public class Farola {
	
	private boolean encendida;   // Estado interno 
	private List<Farola> vecinos; // Lista que contiene a las farolas vecinas -> representa grafo 
	
	//Constructor inicia con Farola apagada
	public Farola() {
		this.encendida = false;
		this.vecinos = new ArrayList<>();
	}
	
	// Relación bidireccional entre farolas -> solo propaga si realmente cambia de estado (Recursividad)
    public void pairWithNeighbor(Farola otraFarola) {
    	
    	// Caso donde otra farola es Null o es la farola actual(auto-relacion) ??
        if (otraFarola == null || otraFarola == this) return;

        if (!vecinos.contains(otraFarola)) { // Si la farola no esta contenida en la lista
            vecinos.add(otraFarola); // agrega la farola a la Lista
            otraFarola.pairWithNeighbor(this); // relación recíproca
//Se llama recíprocamente al mismo método, pero desde otraFarola, pasándole la farola actual (this) como parámetro.
//Esto asegura que la relación de vecindad sea bidireccional: si A es vecina de B, entonces B también es vecina de A.
        }
    }

    // Retorna lista de vecinos
    public List<Farola> getNeighbors() {
        return this.vecinos;
    }

    // Encender farola y propagar si es necesario
    public void turnOn() {
        if (!encendida) {
            encendida = true;
            for (Farola vecino : vecinos) {  //-> for-each se basa internamente en un Iterator (clase colecciones)
                vecino.turnOn(); // Llama recursivamente a métodos de vecinas (se detiene con encendida true)
            }
        }
    }

    // Apagar farola y propagar si es necesario (idem turnOn)
    public void turnOff() {
        if (encendida) {
            encendida = false;
            for (Farola vecino : vecinos) {
                vecino.turnOff();
            }
        }
    }

    // Consultar estado
    public boolean isOn() {
        return this.encendida;
    }
	
    public boolean isOff() {
    	return !this.encendida;
    }
	
}
