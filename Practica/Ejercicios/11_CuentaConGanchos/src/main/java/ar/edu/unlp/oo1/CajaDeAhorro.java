package ar.edu.unlp.oo1;

public class CajaDeAhorro extends Cuenta {

    public CajaDeAhorro() {
        super();
    }

    @Override
    protected boolean puedeExtraer(double monto) {
        double montoConCosto = monto * 1.02;  // se suma 2%
        return this.getSaldo() >= montoConCosto;
    }

    @Override
    public void depositar(double monto) {
        // descuento el 2% al depósito
        double montoNeto = monto * 0.98;
        super.depositar(montoNeto);
    }

    @Override
    public boolean extraer(double monto) {
        double montoConCosto = monto * 1.02;
        if (this.puedeExtraer(monto)) {
            this.extraerSinControlar(montoConCosto);
            return true;
        }
        return false;
    }

    @Override
    public boolean transferirACuenta(double monto, Cuenta cuentaDestino) {
        double montoConCosto = monto * 1.02;
        if (this.puedeExtraer(monto)) {
            this.extraerSinControlar(montoConCosto);
            cuentaDestino.depositar(monto); // el destino recibe el monto completo
            return true;
        }
        return false;
    }
}
