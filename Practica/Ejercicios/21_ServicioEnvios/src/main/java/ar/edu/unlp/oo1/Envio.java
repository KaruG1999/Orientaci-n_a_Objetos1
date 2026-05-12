package ar.edu.unlp.oo1;

import java.time.LocalDate;

public abstract class Envio {

    private LocalDate fechaDespacho;
    private String origen;
    private String destino;
    private int pesoEnGramos;

    public Envio(LocalDate fechaDespacho, String origen, String destino, int pesoEnGramos) {
        this.fechaDespacho = fechaDespacho;
        this.origen = origen;
        this.destino = destino;
        this.pesoEnGramos = pesoEnGramos;
    }

    public LocalDate getFechaDespacho() { return fechaDespacho; }
    public int getPesoEnGramos() { return pesoEnGramos; }

    // Costo sin aplicar el descuento de persona física
    // Cada subclase implementa su propia fórmula
    public abstract double calcularCostoBase();

    // Costo final: el cliente puede tener descuento
    // No sobreescribir este método en las subclases
    public double calcularCosto(Cliente cliente) {
        double base = calcularCostoBase();
        return cliente.aplicarDescuento(base);
    }
}
