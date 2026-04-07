package ar.edu.unlp.info.oo1;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class VisitaCientifica extends VisitaGuiada {
    
    private List<Zona> zonas;

    public VisitaCientifica(LocalDate fecha, int duracionHoras){
        super(fecha, duracionHoras);
        this.zonas = new ArrayList<>();
    }


    // Impacto ambiental tiene un valor base de 50 puntos, más 5 puntos por cada hora adicional a la primera. Si acceden zonas restringidas, se suma un adicional de 100 puntos por cada zona.
    @Override
    public double calcularImpacto(){
        double impacto = 50;
        if (this.getDuracionHoras() > 1) {
            // Le resta 1 hora a la duración total para calcular el impacto adicional, porque la primera hora ya está incluida en el valor base de 50 puntos.
            impacto += (this.getDuracionHoras() - 1) * 5;
        }

        // Si la lista no esta vacia, recorre la lista de zonas y suma el impacto adicional de cada zona al impacto total.
        for (Zona z: this.zonas){
            impacto += z.getAdicionalImpacto();
        }
           
        return impacto;
    }

    public void agregarZona(Zona zona){
        this.zonas.add(zona);
    }


}
