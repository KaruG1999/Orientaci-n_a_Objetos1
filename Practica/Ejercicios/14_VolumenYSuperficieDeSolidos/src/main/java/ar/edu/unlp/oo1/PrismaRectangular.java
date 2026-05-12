package ar.edu.unlp.oo1;

public class PrismaRectangular extends Pieza {

    private double ladoMayor;
    private double ladoMenor;
    private double altura;

    public PrismaRectangular(String material, String color, double ladoMayor, double ladoMenor, double altura) {
        super(material, color);
        this.ladoMayor = ladoMayor;
        this.ladoMenor = ladoMenor;
        this.altura = altura;
    }

    @Override
    public double getVolumen() {
        return ladoMayor * ladoMenor * altura;
    }

    @Override
    public double getSuperficieExterior() {
        return 2 * (ladoMayor * ladoMenor + ladoMayor * altura + ladoMenor * altura);
    }
}
