package ar.edu.unlp.oo1;

import java.time.LocalDate;

// Strategy: cada política sabe cómo calcular el reembolso
public interface PoliticaCancelacion {

    // Retorna el monto a reembolsar al inquilino
    // reserva: la reserva que se está cancelando
    // fechaCancelacion: el día en que se cancela (siempre anterior al inicio de la reserva)
    double calcularReembolso(Reserva reserva, LocalDate fechaCancelacion);
}
