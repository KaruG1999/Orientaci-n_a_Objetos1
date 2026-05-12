package ar.edu.unlp.oo1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Propiedad {

    private String nombre;
    private String direccion;
    private double precioPorNoche;
    private List<Reserva> reservas;
    private PoliticaCancelacion politica; // puede cambiarse en cualquier momento

    public Propiedad(String nombre, String direccion, double precioPorNoche, PoliticaCancelacion politica) {
        // TODO: inicializar todos los atributos y la lista de reservas vacía
        throw new UnsupportedOperationException("Implementar");
    }

    public String getNombre() { return nombre; }
    public double getPrecioPorNoche() { return precioPorNoche; }
    public PoliticaCancelacion getPolitica() { return politica; }

    public void setPolitica(PoliticaCancelacion politica) {
        this.politica = politica;
    }

    public boolean estaDisponible(DateLapse periodo) {
        // TODO: igual que en Ej 19
        throw new UnsupportedOperationException("Implementar");
    }

    public Reserva reservar(Usuario inquilino, DateLapse periodo) {
        // TODO: igual que en Ej 19
        throw new UnsupportedOperationException("Implementar");
    }

    void cancelarReserva(Reserva reserva) {
        // TODO: igual que en Ej 19
        throw new UnsupportedOperationException("Implementar");
    }

    // Cancela la reserva y retorna el monto reembolsado según la política
    // Precondición: la reserva aún no comenzó
    public double cancelarConReembolso(Reserva reserva, LocalDate fechaCancelacion) {
        // TODO:
        //   1. Calcular reembolso: politica.calcularReembolso(reserva, fechaCancelacion)
        //   2. Cancelar la reserva: cancelarReserva(reserva)
        //   3. Retornar el reembolso
        throw new UnsupportedOperationException("Implementar");
    }

    public double calcularIngresosEnPeriodo(DateLapse periodo) {
        // TODO: igual que en Ej 19
        throw new UnsupportedOperationException("Implementar");
    }
}
