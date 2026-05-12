package ar.edu.unlp.oo1;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// Moderada:
//   - Más de 7 días antes del inicio → 100%
//   - Entre 2 y 7 días antes       → 50%
//   - 2 días o menos antes          → 0%
public class PoliticaModerada implements PoliticaCancelacion {

    @Override
    public double calcularReembolso(Reserva reserva, LocalDate fechaCancelacion) {
        // TODO:
        //   1. Calcular días entre fechaCancelacion y reserva.getPeriodo().getFrom()
        //      Pista: ChronoUnit.DAYS.between(fechaCancelacion, reserva.getPeriodo().getFrom())
        //   2. Si diasDeAntelacion > 7  → retornar 100%
        //   3. Si diasDeAntelacion > 2  → retornar 50%
        //   4. Si diasDeAntelacion <= 2 → retornar 0%
        throw new UnsupportedOperationException("Implementar");
    }
}
