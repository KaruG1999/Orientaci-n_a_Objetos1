package ar.edu.unlp.oo1;

//Subclase
public class EmpleadoJerarquico extends Empleado {
	
// Constructor
 public EmpleadoJerarquico(String nombre) {
     super(nombre);
 }

 @Override
 public double sueldoBasico() {
     return super.sueldoBasico() + this.bonoPorCategoria();
 }

 @Override
 public double montoBasico() {
     return 45000;
 }

 public double bonoPorCategoria() {
     return 8000;
 }
 
}
