package ar.edu.unlp.info.oo1;

public class BaseIntegral extends Base {

    public BaseIntegral(String tipoBase, int cantidadPorciones) {
        super(tipoBase, cantidadPorciones);
    }

    @Override
    public String getDescripcion() {
        return "Base de " + getTipoBase() + " (integral, " + getCantidadPorciones() + " porciones)";
    }

    @Override
    public double getCostoEstimado() {
        return 2200.0;
    }
}
