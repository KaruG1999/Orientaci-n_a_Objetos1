package ar.edu.unlp.oo1;

import java.time.LocalDate;

// Costo:
//   $5000 fijo (cualquier destino)
//   + $10/g si peso <= 1000 g
//   + $12/g si peso > 1000 g
//   + $800 si entregaRapida
public class EnvioInternacional extends Envio {

    private boolean entregaRapida;

    public EnvioInternacional(LocalDate fechaDespacho, String origen, String destino,
                               int pesoEnGramos, boolean entregaRapida) {
        super(fechaDespacho, origen, destino, pesoEnGramos);
        this.entregaRapida = entregaRapida;
    }

    @Override
    public double calcularCostoBase() {
        // TODO:
        //   costoBase = 5000
        //   + (pesoEnGramos <= 1000 ? 10 : 12) × pesoEnGramos
        //   + (entregaRapida ? 800 : 0)
        throw new UnsupportedOperationException("Implementar");
    }
}
