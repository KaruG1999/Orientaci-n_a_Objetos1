package ar.edu.unlp.info.oo1;

public class BaseTradicional extends Base {

    public BaseTradicional(String tipoBase, int cantidadPorciones) {
        super(tipoBase, cantidadPorciones);
    }

    @Override
    public String getDescripcion() {
        return "Base de " + getTipoBase() + " (tradicional, " + getCantidadPorciones() + " porciones)";
    }

    @Override
    public double getCostoEstimado() {
        return 1500.0;
    }
}
