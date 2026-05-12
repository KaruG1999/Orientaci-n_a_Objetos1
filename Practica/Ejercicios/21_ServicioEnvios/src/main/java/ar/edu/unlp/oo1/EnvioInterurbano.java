package ar.edu.unlp.oo1;

import java.time.LocalDate;

// Costo por gramo según distancia:
//   < 100 km   → $20/g
//   100-500 km → $25/g
//   > 500 km   → $30/g
public class EnvioInterurbano extends Envio {

    private int distanciaKm;

    public EnvioInterurbano(LocalDate fechaDespacho, String origen, String destino,
                             int pesoEnGramos, int distanciaKm) {
        super(fechaDespacho, origen, destino, pesoEnGramos);
        this.distanciaKm = distanciaKm;
    }

    public int getDistanciaKm() { return distanciaKm; }

    @Override
    public double calcularCostoBase() {
        // TODO:
        //   Determinar tarifa según distanciaKm:
        //     distanciaKm < 100   → 20
        //     distanciaKm <= 500  → 25
        //     distanciaKm > 500   → 30
        //   Retornar tarifa × getPesoEnGramos()
        throw new UnsupportedOperationException("Implementar");
    }
}
