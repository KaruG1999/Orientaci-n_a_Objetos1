package ar.edu.unlp.oo1;

import java.util.ArrayList;
import java.util.List;

public class Balanza {

	private int cantidadDeProductos;
	private double precioTotal;
	private double pesoTotal;
	private List<Producto> listaProd; // Ahora la balanza debe recordar productos ingresados
	
	// Contructor
	public Balanza() {
		this.ponerEnCero();  // estaría bien planteado?
	}
	
	// + ponerEnCero() -> pone en cero la balanza
	public void ponerEnCero () {
		this.cantidadDeProductos = 0;
		this.precioTotal = 0;
		this.pesoTotal = 0;
		this.listaProd = new ArrayList<>(); // inicializar lista (o this.listaProd.clear()?)
	}
	
	// + agregarProducto() -> recibe clase Producto y lo agrega a datos cargados
	public void agregarProducto(Producto p) {
		this.cantidadDeProductos++;
		this.precioTotal += p.getPrecioPorKilo() * p.getPeso();
		this.pesoTotal += p.getPeso();
		this.listaProd.add(p); // agrego producto a lista
	}
	
	// + emitirTicket() -> Crea y devuelve un ticket nuevo
	public Ticket emitirTicket() {
		Ticket nuevoTicket = new Ticket(this.cantidadDeProductos, this.pesoTotal,this.precioTotal, this.listaProd);
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
	
	// agrego el get lista de productos cargados en balanza 
	public List<Producto> getProductos() {
		return this.listaProd;   
	}
	
}


// 1) Es necesario almacenar totales? NO, con cantProd, pesoTotal y Precio total se puede 
//calcular recorriendo lista 

// Pasan los test ya que ellos solo verifican que funcione ante el llamado a los metodos, no ven 
// como son por dentro 
