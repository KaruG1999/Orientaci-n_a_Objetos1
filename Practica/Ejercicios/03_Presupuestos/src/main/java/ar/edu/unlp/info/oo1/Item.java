package ar.edu.unlp.info.oo1;

public class Item {

	private String detalle;
    private int cantidad;
    private int costoUnitario;

    // Constructor
    public Item(String detalle, int cantidad, int costoUnitario) {
        this.detalle = detalle;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
    }

    // Getters y Setters
    public String getDetalle() {
        return detalle;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public int getCostoUnitario() {
        return costoUnitario;
    }

    // Método del UML
    public int costo() {
        return cantidad * costoUnitario;
    }

    @Override
    public String toString() {
        return cantidad + " x " + detalle + " ($" + costoUnitario + " c/u)";
    }
	
	
}
