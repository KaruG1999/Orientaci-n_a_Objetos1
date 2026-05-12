package ar.edu.unlp.oo1;

import java.util.List;

public interface EstrategiaSeleccion {

    // Recibe la lista de jobs pendientes y retorna el índice del que debe ejecutarse
    // La implementación de next() en JobScheduler usará este método
    JobDescription seleccionarSiguiente(List<JobDescription> jobs);
}
