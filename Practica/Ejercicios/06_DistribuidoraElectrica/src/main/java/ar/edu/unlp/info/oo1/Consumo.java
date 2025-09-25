package ar.edu.unlp.info.oo1;

import java.time.LocalDate;

public class Consumo {
    private double energiaActiva;   // en kWh
    private double energiaReactiva; // en kVArh
    private LocalDate fecha;

    public Consumo(double energiaActiva, double energiaReactiva, LocalDate fecha) {
        this.energiaActiva = energiaActiva;
        this.energiaReactiva = energiaReactiva;
        this.fecha = fecha;
    }

    public double getEnergiaActiva() {
        return energiaActiva;
    }

    public double getEnergiaReactiva() {
        return energiaReactiva;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    // Factor de potencia = activa / (sqrt(activa^2 + reactiva^2))
    public double calcularFPE() {
        double denominador = Math.sqrt(Math.pow(energiaActiva, 2) + Math.pow(energiaReactiva, 2));
        if (denominador == 0) return 0;
        return energiaActiva / denominador;
    }
}

