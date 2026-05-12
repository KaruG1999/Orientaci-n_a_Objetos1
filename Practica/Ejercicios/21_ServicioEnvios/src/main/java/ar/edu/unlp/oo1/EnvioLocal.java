package ar.edu.unlp.oo1;

import java.time.LocalDate;

// Costo: $1000 estándar + $500 si es entrega rápida
public class EnvioLocal extends Envio {

    private boolean entregaRapida;

    public EnvioLocal(LocalDate fechaDespacho, String origen, String destino,
                      int pesoEnGramos, boolean entregaRapida) {
        super(fechaDespacho, origen, destino, pesoEnGramos);
        this.entregaRapida = entregaRapida;
    }

    @Override
    public double calcularCostoBase() {
        // TODO: 1000 + (entregaRapida ? 500 : 0)
        throw new UnsupportedOperationException("Implementar");
    }
}
