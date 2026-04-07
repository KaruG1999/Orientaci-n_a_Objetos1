package ar.edu.unlp.info.oo1;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class VisitaEducativa extends VisitaGuiada {
    
    private List<Grupo> grupos;

    public VisitaEducativa(LocalDate fecha, int duracionHoras){
        super(fecha, duracionHoras);
        this.grupos = new ArrayList<>();
    }

    public void agregarGrupo(Grupo grupo){
        this.grupos.add(grupo);

        // Sincronizamos con la lista de la superclase (VisitaGuiada)
        // para que el método getListadoAsistentes() funcione después ??
        for (Participante a : grupo.getAlumnos()) {
            super.agregarParticipante(a); 
    }
        for (Participante d : grupo.getDocentes()) {
            super.agregarParticipante(d);
    }
    }

    // Impacto ambiental
    @Override
    public double calcularImpacto(){
        double  impacto=0;
        for (Grupo g: this.grupos) {
            impacto += g.calcularImpactoGrupo(this.getDuracionHoras());
        }
        return impacto;
    }


}
