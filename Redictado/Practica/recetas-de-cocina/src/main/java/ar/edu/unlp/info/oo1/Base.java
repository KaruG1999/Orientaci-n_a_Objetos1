package ar.edu.unlp.info.oo1;

public class Base extends Componente {

    private String tipoBase;
    private int cantidadPorciones;
    private boolean esIntegral;

    public Base(String tipoBase, int cantidadPorciones, boolean esIntegral) {
        this.tipoBase = tipoBase;
        this.cantidadPorciones = cantidadPorciones;
        this.esIntegral = esIntegral;
    }

    @Override
    public String getDescripcion() {
        String tipo = esIntegral ? "integral" : "tradicional";
        return "Base de " + tipoBase + " (" + tipo + ", " + cantidadPorciones + " porciones)";
    }

    @Override
    public double getCostoEstimado() {
        return esIntegral ? 2200.0 : 1500.0;
    }
}
