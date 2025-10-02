package ar.edu.unlp.oo1.ejercicio2;

public class Balanza {

	private int cantidadDeProductos;
	private double precioTotal;
	private double pesoTotal;
	
	// Contructor
	public Balanza() {
		this.ponerEnCero();  // estaría bien planteado?
	}
	
	// + ponerEnCero() -> pone en cero la balanza
	public void ponerEnCero () {
		this.cantidadDeProductos = 0;
		this.precioTotal = 0;
		this.pesoTotal = 0;
	}
	
	// + agregarProducto() -> recibe clase Producto y lo agrega a datos cargados
	public void agregarProducto(Producto p) {
		this.cantidadDeProductos++;
		this.precioTotal += p.getPrecioPorKilo();
		this.pesoTotal += p.getPeso();
	}
	
	// + emitirTicket() -> Crea y devuelve un ticket nuevo
	public Ticket emitirTicket() {
		Ticket nuevoTicket = new Ticket(this.cantidadDeProductos, this.pesoTotal,this.precioTotal);
		return nuevoTicket;
	}

	public int getCantidadDeProductos() {
		return cantidadDeProductos;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public double getPesoTotal() {
		return pesoTotal;
	}
	
}
