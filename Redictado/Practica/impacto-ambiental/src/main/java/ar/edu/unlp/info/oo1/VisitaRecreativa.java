package ar.edu.unlp.info.oo1;

import java.time.LocalDate;

public class VisitaRecreativa extends VisitaGuiada {
    
    public VisitaRecreativa(LocalDate fecha, int duracionHoras){
        super(fecha,duracionHoras);
    }

    // Impacto ambiental se estima en 1 punto por participante por hora de duración
    @Override
    public double calcularImpacto(){
        double impacto = this.getAsistentesOrdenados().size() * this.getDuracionHoras();
        return impacto;
    }

}
