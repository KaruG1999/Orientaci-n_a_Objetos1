package ar.edu.unlp.oo1;

import java.util.ArrayList;
import java.util.List;

public class ReporteDeConstruccion {

    private List<Pieza> piezas;

    public ReporteDeConstruccion() {
        this.piezas = new ArrayList<>();
    }

    public void agregarPieza(Pieza pieza) {
        this.piezas.add(pieza);
    }

    public double volumenDeMaterial(String material) {
        double total = 0;
        for (Pieza p : piezas) {
            if (p.getMaterial().equals(material)) {
                total += p.getVolumen();
            }
        }
        return total;
    }

    public double superficieDeColor(String color) {
        double total = 0;
        for (Pieza p : piezas) {
            if (p.getColor().equals(color)) {
                total += p.getSuperficieExterior();
            }
        }
        return total;
    }
}
