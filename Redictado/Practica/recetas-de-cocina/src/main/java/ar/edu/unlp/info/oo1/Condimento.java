package ar.edu.unlp.info.oo1;

public abstract class Condimento implements Componente {

    private String mezcla;
    private int cantidadCucharaditas;

    public Condimento(String mezcla, int cantidadCucharaditas) {
        this.mezcla = mezcla;
        this.cantidadCucharaditas = cantidadCucharaditas;
    }

    public String getMezcla() {
        return mezcla;
    }

    public int getCantidadCucharaditas() {
        return cantidadCucharaditas;
    }

    @Override
    public double getCostoEstimado() {
        return 0.0;
    }
}
