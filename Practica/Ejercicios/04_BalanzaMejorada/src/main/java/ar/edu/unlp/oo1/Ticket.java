package ar.edu.unlp.oo1;
import java.time.LocalDate;   // Importo modulo de Fecha
import java.util.ArrayList;
import java.util.List;

public class Ticket {

	private LocalDate fecha;
	private int cantidadDeProductos;
	private double pesoTotal;
	private double precioTotal;
	private List<Producto> productos;
	
	// Constructor -> Atajo eclipse : Alt+shift+S -> Generate constructor using fields
	public Ticket(int cantidadDeProductos, double pesoTotal, double precioTotal, List<Producto> productos) {
		this.fecha = LocalDate.now();
		this.cantidadDeProductos = cantidadDeProductos;
		this.pesoTotal = pesoTotal;
		this.precioTotal = precioTotal;
		this.productos = new ArrayList<>(productos);
	}
	
	// + impuesto () : Real impuesto 21%
	public double impuesto() {
		return this.precioTotal * 0.21;
	}

	
	// Implementar getters si es necesario (Error implementar setters)
	
	public int getCantidadDeProductos() {
		return cantidadDeProductos;
	}

	public double getPesoTotal() {
		return pesoTotal;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public LocalDate getFecha() {
		return this.fecha;
	}

	@Override
	public String toString() {
		return "Ticket [fecha=" + fecha + ", cantidadDeProductos=" + cantidadDeProductos + ", pesoTotal=" + pesoTotal
				+ ", precioTotal=" + precioTotal + ", productos=" + productos + "]";
	}
	
	
	
	
}
