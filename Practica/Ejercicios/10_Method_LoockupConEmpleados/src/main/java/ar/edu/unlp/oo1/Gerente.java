package ar.edu.unlp.oo1;

//Subclase final en la jerarquía
public class Gerente extends EmpleadoJerarquico {
 public Gerente(String nombre) {
     super(nombre);
 }

 @Override
 public double aportes() {
     return this.montoBasico() * 0.05;
 }

 @Override
 public double montoBasico() {
     return 57000;
 }
}
