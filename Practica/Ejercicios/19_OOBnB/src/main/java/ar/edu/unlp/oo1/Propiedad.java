package ar.edu.unlp.oo1;

import java.util.ArrayList;
import java.util.List;

public class Propiedad {

    private String nombre;
    private String direccion;
    private double precioPorNoche;
    private List<Reserva> reservas;

    public Propiedad(String nombre, String direccion, double precioPorNoche) {
        // TODO: inicializar atributos y la lista de reservas vacía
        throw new UnsupportedOperationException("Implementar");
    }

    public String getNombre() { return nombre; }
    public double getPrecioPorNoche() { return precioPorNoche; }

    // Retorna true si la propiedad no tiene ninguna reserva que solape con el período dado
    public boolean estaDisponible(DateLapse periodo) {
        // TODO: recorrer las reservas y verificar que ninguna solape con 'periodo'
        // Pista: reservas.stream().noneMatch(r -> r.getPeriodo().overlaps(periodo))
        throw new UnsupportedOperationException("Implementar");
    }

    // Crea y agrega una reserva si la propiedad está disponible en ese período
    // Retorna la Reserva creada, o null si no está disponible
    public Reserva reservar(Usuario inquilino, DateLapse periodo) {
        // TODO:
        //   1. Verificar disponibilidad con estaDisponible(periodo)
        //   2. Si está disponible: crear la Reserva, agregarla a la lista, retornarla
        //   3. Si no está disponible: retornar null
        throw new UnsupportedOperationException("Implementar");
    }

    // Cancela la reserva dada (la quita de la lista)
    void cancelarReserva(Reserva reserva) {
        // TODO: quitar la reserva de la lista
        throw new UnsupportedOperationException("Implementar");
    }

    // Suma el precio total de las reservas cuyo período esté incluido en 'periodo'
    // El propietario recibe el 75% de esa suma
    public double calcularIngresosEnPeriodo(DateLapse periodo) {
        // TODO:
        //   1. Filtrar reservas cuyo período esté dentro de 'periodo'
        //      (una reserva "entra" si su from y to están dentro del período dado)
        //   2. Sumar los precios totales de esas reservas
        //   3. Retornar el 75% de esa suma
        throw new UnsupportedOperationException("Implementar");
    }
}
