package ar.edu.unlp.oo1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Cliente {

    private String nombre;
    private String direccion;
    private List<Envio> envios;

    public Cliente(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.envios = new ArrayList<>();
    }

    public void agregarEnvio(Envio envio) {
        envios.add(envio);
    }

    // Retorna el monto total de los envíos despachados entre from y to
    public double montoEnPeriodo(LocalDate from, LocalDate to) {
        // TODO:
        //   Filtrar envios cuya fechaDespacho esté entre from y to (inclusive)
        //   Sumar envio.calcularCosto(this) para cada uno
        // Pista:
        //   envios.stream()
        //     .filter(e -> !e.getFechaDespacho().isBefore(from) && !e.getFechaDespacho().isAfter(to))
        //     .mapToDouble(e -> e.calcularCosto(this))
        //     .sum()
        throw new UnsupportedOperationException("Implementar");
    }

    // Cada subclase define si aplica descuento o no
    public abstract double aplicarDescuento(double monto);
}
