package ar.edu.unlp.oo1;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateLapse {

    private LocalDate from;
    private LocalDate to;

    public DateLapse(LocalDate from, LocalDate to) {
        // TODO: guardar from y to
        throw new UnsupportedOperationException("Implementar");
    }

    public LocalDate getFrom() {
        // TODO: retornar from
        throw new UnsupportedOperationException("Implementar");
    }

    public LocalDate getTo() {
        // TODO: retornar to
        throw new UnsupportedOperationException("Implementar");
    }

    public int sizeInDays() {
        // TODO: retornar la cantidad de días entre from y to
        // Pista: ChronoUnit.DAYS.between(from, to)
        throw new UnsupportedOperationException("Implementar");
    }

    public boolean includesDate(LocalDate date) {
        // TODO: retornar true si date está entre from y to (inclusive en ambos extremos)
        // Pista: !date.isBefore(from) && !date.isAfter(to)
        throw new UnsupportedOperationException("Implementar");
    }

    // Necesario para OOBnB (Ej 19)
    public boolean overlaps(DateLapse other) {
        // TODO: retornar true si los dos períodos se superponen
        // Dos períodos NO se superponen solo si uno termina antes de que el otro empiece:
        //   this.to < other.from  →  this.to.isBefore(other.from)
        //   other.to < this.from  →  other.to.isBefore(this.from)
        // Se superponen si ninguna de las dos condiciones anteriores es verdadera
        throw new UnsupportedOperationException("Implementar");
    }
}
