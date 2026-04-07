package ar.edu.unlp.info.oo1;

import java.util.List;
import java.util.ArrayList;

public class ReservaNatural {
    
    private String nombre;
    private List<VisitaGuiada> visitasGuiadas;
    private List<Zona> zonas;

    public ReservaNatural(String nombre) {
        this.nombre = nombre;
        this.visitasGuiadas = new ArrayList<>();
        this.zonas = new ArrayList<>();
    }

    public void agregarVisitaGuiada(VisitaGuiada visita){
        this.visitasGuiadas.add(visita);
    }

    public double calcularImpactoTotal(){
        double impactoTotal =0;
        for (VisitaGuiada visita: this.visitasGuiadas){
            impactoTotal += visita.calcularImpacto();
        }
        return impactoTotal;
    }

}
