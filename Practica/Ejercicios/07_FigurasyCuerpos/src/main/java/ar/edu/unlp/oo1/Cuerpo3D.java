package ar.edu.unlp.oo1;

public class Cuerpo3D {
	
    private double altura;
    private Figura2D caraBasal;  // Puede ser Área o Perimetro de Círculo o Cuadrado
    
    // Constructor por defecto (REQUERIDO por los tests)
    public Cuerpo3D() {
        this.altura = 0.0;
        this.caraBasal = null;
    }
    
    public Cuerpo3D(double altura) {
        this.caraBasal = null;  // es correcto que sea null?
        this.altura = altura;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public Figura2D getCaraBasal() {
        return caraBasal;
    }

    // Paso objeto que implementa la interfaz. ej -> cilindro.setCaraBasal(new Circulo(5));
    public void setCaraBasal(Figura2D caraBasal) {
        this.caraBasal = caraBasal;
    }

    public double getVolumen() {
        return caraBasal.getArea() * altura;
    }

    public double getSuperficieExterior() {
        return 2 * caraBasal.getArea() + caraBasal.getPerimetro() * altura;
    }
}

