package ar.edu.unlp.info.oo1;

public abstract class Zona {
    
    private String nombre;
    private String descripcion;

    public Zona (String nombre, String descripcion){
        this.nombre= nombre;
        this.descripcion = descripcion;
    }

    public abstract double getAdicionalImpacto();

}
