package ar.edu.unlp.oo1;

import java.util.List;

// Shortest Job First: retorna el trabajo con menor tiempo estimado
public class EstrategiaSJF implements EstrategiaSeleccion {

    @Override
    public JobDescription seleccionarSiguiente(List<JobDescription> jobs) {
        // TODO: retornar el job con menor getEstimatedTime()
        // Pista: iterar la lista buscando el mínimo, o usar stream().min()
        throw new UnsupportedOperationException("Implementar");
    }
}
