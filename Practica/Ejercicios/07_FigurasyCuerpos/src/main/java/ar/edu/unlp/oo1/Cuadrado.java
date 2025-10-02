package ar.edu.unlp.oo1;

public class Cuadrado implements Figura2D{
	
    private double lado;
    
    // Constructor por defecto (REQUERIDO por los tests)
    public Cuadrado() {
        this.lado = 0.0;
    }
    
    // Constructor con parámetro
    public Cuadrado(double lado) {
        this.lado = lado;
    }
    
    // Getter y Setter para lado
    public double getLado() {
        return this.lado;
    }
    
    public void setLado(double lado) {
        this.lado = lado;
    }
    
    // Perímetro del cuadrado: lado * 4
    public double getPerimetro() {
        return this.lado * 4;
    }
    
    // Área del cuadrado: lado²
    public double getArea() {
        return this.lado * this.lado;
    }
}