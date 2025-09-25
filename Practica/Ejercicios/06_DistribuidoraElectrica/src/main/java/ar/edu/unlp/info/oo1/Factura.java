package ar.edu.unlp.info.oo1;
import java.time.LocalDate;

public class Factura {
	
    private Usuario usuario;
    private Consumo consumo;
    private LocalDate fechaEmision;
    private double bonificacion;
    private double montoFinal;

    public Factura(Usuario usuario, Consumo consumo, CuadroTarifario cuadro) {
        this.usuario = usuario;
        this.consumo = consumo;
        this.fechaEmision = LocalDate.now();
        this.calcularMonto(cuadro);
    }

    private void calcularMonto(CuadroTarifario cuadro) {
        double costo = consumo.getEnergiaActiva() * cuadro.getPrecioKWh();
        double fpe = consumo.calcularFPE();

        if (fpe > 0.8) {
            this.bonificacion = costo * 0.10;
        } else {
            this.bonificacion = 0;
        }

        this.montoFinal = costo - bonificacion;
    }

    public double getMontoFinal() {
        return montoFinal;
    }

    public double getBonificacion() {
        return bonificacion;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Consumo getConsumo() {
        return consumo;
    }
}
