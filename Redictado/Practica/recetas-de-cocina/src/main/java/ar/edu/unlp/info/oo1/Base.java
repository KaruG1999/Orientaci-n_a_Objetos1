package ar.edu.unlp.info.oo1;

public abstract class Base implements Componente {

    private String tipoBase;
    private int cantidadPorciones;

    public Base(String tipoBase, int cantidadPorciones) {
        this.tipoBase = tipoBase;
        this.cantidadPorciones = cantidadPorciones;
    }

    public String getTipoBase() {
        return tipoBase;
    }

    public int getCantidadPorciones() {
        return cantidadPorciones;
    }
}
