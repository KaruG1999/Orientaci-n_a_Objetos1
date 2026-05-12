package ar.edu.unlp.oo1;

import java.time.LocalDate;

public class Reserva {

    private Usuario inquilino;
    private Propiedad propiedad;
    private DateLapse periodo;

    public Reserva(Usuario inquilino, Propiedad propiedad, DateLapse periodo) {
        // TODO: inicializar atributos
        throw new UnsupportedOperationException("Implementar");
    }

    public DateLapse getPeriodo() { return periodo; }
    public Propiedad getPropiedad() { return propiedad; }

    // El precio es: cantidad de noches × precio por noche de la propiedad
    public double getPrecioTotal() {
        // TODO: periodo.sizeInDays() * propiedad.getPrecioPorNoche()
        throw new UnsupportedOperationException("Implementar");
    }

    // Cancela la reserva solo si el período aún no comenzó
    // Es decir, si hoy < periodo.getFrom()
    public boolean cancelar() {
        // TODO:
        //   1. Verificar que LocalDate.now().isBefore(periodo.getFrom())
        //   2. Si sí: llamar a propiedad.cancelarReserva(this) y retornar true
        //   3. Si no: retornar false (el período ya comenzó, no se puede cancelar)
        throw new UnsupportedOperationException("Implementar");
    }
}
