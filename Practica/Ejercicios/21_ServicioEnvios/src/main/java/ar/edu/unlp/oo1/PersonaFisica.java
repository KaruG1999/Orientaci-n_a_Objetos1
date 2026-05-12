package ar.edu.unlp.oo1;

// Personas físicas tienen 10% de descuento en todos sus envíos
public class PersonaFisica extends Cliente {

    private String dni;

    public PersonaFisica(String nombre, String direccion, String dni) {
        super(nombre, direccion);
        this.dni = dni;
    }

    @Override
    public double aplicarDescuento(double monto) {
        // TODO: retornar monto * 0.90
        throw new UnsupportedOperationException("Implementar");
    }
}
