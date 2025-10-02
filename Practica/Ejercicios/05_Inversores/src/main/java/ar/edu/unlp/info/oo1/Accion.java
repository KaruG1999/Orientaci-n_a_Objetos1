package ar.edu.unlp.info.oo1;

public class Accion extends Inversion {
	
    private String nombre;       // Nombre que identifica la acción
    private int cantidad;        // Cantidad de acciones
    private double valorUnitario; // Valor por acción

    public Accion(String nombre, int cantidad, double valorUnitario) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.valorUnitario = valorUnitario;
    }

    @Override
    public double valorActual() {
        return this.cantidad * this.valorUnitario;
    }

    public String getNombre() {
        return nombre;
    }
}
