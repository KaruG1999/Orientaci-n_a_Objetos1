package ar.edu.unlp.oo1;

public class Reserva {

    private Usuario inquilino;
    private Propiedad propiedad;
    private DateLapse periodo;

    public Reserva(Usuario inquilino, Propiedad propiedad, DateLapse periodo) {
        this.inquilino = inquilino;
        this.propiedad = propiedad;
        this.periodo = periodo;
    }

    public DateLapse getPeriodo() { return periodo; }
    public Propiedad getPropiedad() { return propiedad; }

    public double getPrecioTotal() {
        return periodo.sizeInDays() * propiedad.getPrecioPorNoche();
    }
}
