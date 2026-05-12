package ar.edu.unlp.oo1;

import java.time.LocalDate;

// Flexible: reembolsa el 100% siempre
public class PoliticaFlexible implements PoliticaCancelacion {

    @Override
    public double calcularReembolso(Reserva reserva, LocalDate fechaCancelacion) {
        // TODO: retornar reserva.getPrecioTotal() (100%)
        throw new UnsupportedOperationException("Implementar");
    }
}
