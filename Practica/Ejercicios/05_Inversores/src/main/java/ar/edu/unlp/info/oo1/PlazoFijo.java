package ar.edu.unlp.info.oo1;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PlazoFijo extends Inversion {
	
    private LocalDate fechaConstitucion;
    private double montoDepositado;
    private double porcentajeInteres; // interés diario (%)

    public PlazoFijo(LocalDate fechaConstitucion, double montoDepositado, double porcentajeInteres) {
        this.fechaConstitucion = fechaConstitucion;
        this.montoDepositado = montoDepositado;
        this.porcentajeInteres = porcentajeInteres;
    }

    @Override
    public double valorActual() {
        long dias = ChronoUnit.DAYS.between(fechaConstitucion, LocalDate.now());
        double intereses = montoDepositado * (porcentajeInteres / 100) * dias;
        return montoDepositado + intereses;
    }
}
