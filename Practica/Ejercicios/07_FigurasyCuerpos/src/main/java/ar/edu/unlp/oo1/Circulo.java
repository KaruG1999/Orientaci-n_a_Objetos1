package ar.edu.unlp.oo1;

public class Circulo implements Figura2D{
	
    private double radio;
    
    //REQUERIDO por los tests
    public Circulo() {
        this.radio = 0.0;
    }
    
    // Constructor con parámetro
    public Circulo(double radio) {
        this.radio = radio;
    }
    
    // Getter y Setter para radio
    public double getRadio() {
        return this.radio;
    }
    
    public void setRadio(double radio) {
        this.radio = radio;
    }
    
    // Diámetro del círculo: radio * 2
    public double getDiametro() {
        return this.radio * 2;
    }
    
    // Perímetro del círculo: π * diámetro
    public double getPerimetro() {
        return Math.PI * this.getDiametro();
    }
    
    // Área del círculo: π * radio²
    public double getArea() {
        return Math.PI * this.radio * this.radio;
    }
}