package ar.edu.unlp.info.oo1;

public class CondimentoNoPicante extends Condimento {

    public CondimentoNoPicante(String mezcla, int cantidadCucharaditas) {
        super(mezcla, cantidadCucharaditas);
    }

    @Override
    public String getDescripcion() {
        return "Condimento " + getMezcla() + " (no picante, " + getCantidadCucharaditas() + " cucharaditas)";
    }
}
