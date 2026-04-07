package ar.edu.unlp.info.oo1;

public class ZonaComun extends Zona{
    
    public ZonaComun (String nombre, String descripcion){ 
        super(nombre, descripcion);
    }

    @Override
    public double getAdicionalImpacto(){
        return 0;
    }

}
