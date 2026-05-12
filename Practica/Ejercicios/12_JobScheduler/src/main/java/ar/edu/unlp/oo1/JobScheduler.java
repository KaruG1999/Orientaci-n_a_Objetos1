package ar.edu.unlp.oo1;

import java.util.ArrayList;
import java.util.List;

// El scheduler delega la selección del siguiente job a una EstrategiaSeleccion
// Una vez asignada la estrategia al construir, no puede cambiarse
public class JobScheduler {

    private List<JobDescription> jobs;
    private EstrategiaSeleccion estrategia;

    public JobScheduler(EstrategiaSeleccion estrategia) {
        // TODO: inicializar la lista vacía y guardar la estrategia
        throw new UnsupportedOperationException("Implementar");
    }

    // Agrega un job al final de la lista de pendientes
    public void schedule(JobDescription job) {
        // TODO: agregar job a la lista
        throw new UnsupportedOperationException("Implementar");
    }

    // Determina el siguiente job según la estrategia, lo quita de la lista y lo retorna
    public JobDescription next() {
        // TODO:
        //   1. Pedir a la estrategia que elija el siguiente job
        //   2. Quitarlo de la lista
        //   3. Retornarlo
        throw new UnsupportedOperationException("Implementar");
    }
}
