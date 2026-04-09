package ar.edu.unlp.info.oo1;

public class Condimento extends Componente {

    private String mezcla;
    private int cantidadCucharaditas;
    private boolean esPicante;

    public Condimento(String mezcla, int cantidadCucharaditas, boolean esPicante) {
        this.mezcla = mezcla;
        this.cantidadCucharaditas = cantidadCucharaditas;
        this.esPicante = esPicante;
    }

    @Override
    public String getDescripcion() {
        String picante = esPicante ? "picante" : "no picante";
        return "Condimento " + mezcla + " (" + picante + ", " + cantidadCucharaditas + " cucharaditas)";
    }

    @Override
    public double getCostoEstimado() {
        return 0.0;
    }
}
