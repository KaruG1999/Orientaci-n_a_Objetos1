package ar.edu.unlp.info.oo1;

public class CondimentoPicante extends Condimento {

    public CondimentoPicante(String mezcla, int cantidadCucharaditas) {
        super(mezcla, cantidadCucharaditas);
    }

    @Override
    public String getDescripcion() {
        return "Condimento " + getMezcla() + " (picante, " + getCantidadCucharaditas() + " cucharaditas)";
    }
}
