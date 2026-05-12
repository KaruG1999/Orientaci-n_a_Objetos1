package ar.edu.unlp.oo1;

import java.util.List;

// Priority: retorna el trabajo con mayor prioridad
public class EstrategiaPrioridad implements EstrategiaSeleccion {

    @Override
    public JobDescription seleccionarSiguiente(List<JobDescription> jobs) {
        // TODO: retornar el job con mayor getPriority()
        // Pista: iterar la lista buscando el máximo, o usar stream().max()
        throw new UnsupportedOperationException("Implementar");
    }
}
