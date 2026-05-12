package ar.edu.unlp.oo1;

public class Esfera extends Pieza {

    private double radio;

    public Esfera(String material, String color, double radio) {
        super(material, color);
        this.radio = radio;
    }

    @Override
    public double getVolumen() {
        return (4.0 / 3.0) * Math.PI * radio * radio * radio;
    }

    @Override
    public double getSuperficieExterior() {
        return 4 * Math.PI * radio * radio;
    }
}
