package ar.edu.unlp.oo1;

// Clientes corporativos no tienen descuento
public class ClienteCorporativo extends Cliente {

    private String cuit;

    public ClienteCorporativo(String nombre, String direccion, String cuit) {
        super(nombre, direccion);
        this.cuit = cuit;
    }

    @Override
    public double aplicarDescuento(double monto) {
        // TODO: retornar monto sin modificar (sin descuento)
        throw new UnsupportedOperationException("Implementar");
    }
}
