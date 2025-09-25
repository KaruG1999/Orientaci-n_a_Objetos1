package ar.edu.unlp.oo1;

public class CuentaCorriente extends Cuenta {

    private double limiteDescubierto;

    public CuentaCorriente() {
        super();
        this.limiteDescubierto = 0;
    }

    public double getDescubierto() {
        return this.limiteDescubierto;
    }

    public void setDescubierto(double descubierto) {
        this.limiteDescubierto = descubierto;
    }

    @Override
    protected boolean puedeExtraer(double monto) {
        // puede quedar en negativo hasta el límite
        return (this.getSaldo() - monto) >= -this.limiteDescubierto;
    }
}

