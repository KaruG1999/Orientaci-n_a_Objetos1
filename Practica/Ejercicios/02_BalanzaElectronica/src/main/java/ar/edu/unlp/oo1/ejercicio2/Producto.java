package ar.edu.unlp.oo1.ejercicio2;

public class Producto {

	private double peso;
	private double precioPorKilo;
	private String descripción;
	
	// contructor 
	public Producto(String descripción, double peso, int precioReal) {
		this.peso = peso;
		this.precioPorKilo = precioReal;
		this.descripción = descripción;
	}
	
	// + getPrecio()
	public double getPrecioPorKilo() {
		return this.precioPorKilo;
	}

	public double getPeso() {
		return this.peso;
	}

	public String getDescripcion() {
		return this.descripción;
	}
	
	// Error en test me decia que faltaba setPrecioPorKilo()
	public void setPrecioPorKilo (double precioPorKilo) {
		this.precioPorKilo = precioPorKilo;
	}
	
	
}
