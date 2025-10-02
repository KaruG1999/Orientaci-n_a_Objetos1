package ar.edu.unlp.info.oo1;
import java.time.LocalDate;
import java.util.ArrayList;  
import java.util.List;

public class Presupuesto {

	private LocalDate fecha;
	private String cliente;
	private List<Item> items;    // Manera de declarar tipo dato lista

    // Constructor
    public Presupuesto(String cliente) {
        this.fecha = LocalDate.now();
        this.cliente = cliente;
        this.items = new ArrayList<>();  // instancio una lista
    }

    // Getters y Setters
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public List<Item> getItems() {
        return items;
    }

    // Método del UML: agregarItem
    public void agregarItem(Item item) {
        items.add(item);
    }

    // Método del UML: calcularTotal
    public Double calcularTotal() {
    	double total = 0.0;
        for (Item item : items) {
            total += item.costo();
        }
        return total;
        // return items.stream().mapToDouble(Item::costo).sum(); (OPCION MAS AVANZADA)
    }

    @Override
    public String toString() {
        return "Presupuesto de " + cliente + " (" + fecha + ") - Total: $" + calcularTotal();
    }
	
	
}
