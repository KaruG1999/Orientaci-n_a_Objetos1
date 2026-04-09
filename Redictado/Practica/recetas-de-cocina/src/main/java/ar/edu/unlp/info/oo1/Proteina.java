package ar.edu.unlp.info.oo1;

public class Proteina extends Componente {

    private String tipoProteina;
    private String formaPresentacion;
    private int cantidadPorciones;
    private double precioPorPorcion;

    public Proteina(String tipoProteina, String formaPresentacion, int cantidadPorciones, double precioPorPorcion) {
        this.tipoProteina = tipoProteina;
        this.formaPresentacion = formaPresentacion;
        this.cantidadPorciones = cantidadPorciones;
        this.precioPorPorcion = precioPorPorcion;
    }

    @Override
    public String getDescripcion() {
        return "Proteína de " + tipoProteina + " en " + formaPresentacion +
               " (" + cantidadPorciones + " porciones a $" + (int) precioPorPorcion + " por porción)";
    }

    @Override
    public double getCostoEstimado() {
        return precioPorPorcion * cantidadPorciones;
    }
}
