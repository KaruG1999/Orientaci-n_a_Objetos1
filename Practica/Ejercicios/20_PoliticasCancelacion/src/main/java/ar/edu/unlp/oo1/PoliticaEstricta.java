package ar.edu.unlp.oo1;

import java.time.LocalDate;

// Estricta: no reembolsa nada
public class PoliticaEstricta implements PoliticaCancelacion {

    @Override
    public double calcularReembolso(Reserva reserva, LocalDate fechaCancelacion) {
        // TODO: retornar 0
        throw new UnsupportedOperationException("Implementar");
    }
}
