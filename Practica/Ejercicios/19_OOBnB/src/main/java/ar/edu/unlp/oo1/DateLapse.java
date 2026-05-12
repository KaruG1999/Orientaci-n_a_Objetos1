package ar.edu.unlp.oo1;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateLapse {

    private LocalDate from;
    private LocalDate to;

    public DateLapse(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    public LocalDate getFrom() { return from; }
    public LocalDate getTo()   { return to; }

    public long sizeInDays() {
        return ChronoUnit.DAYS.between(from, to);
    }

    public boolean includesDate(LocalDate date) {
        return !date.isBefore(from) && !date.isAfter(to);
    }

    public boolean overlaps(DateLapse other) {
        return !this.to.isBefore(other.from) && !this.from.isAfter(other.to);
    }
}
