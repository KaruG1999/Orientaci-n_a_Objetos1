package ar.edu.unlp.info.oo1;

public class ZonaRestringida extends Zona{
    
    public ZonaRestringida (String nombre, String descripcion){ 
        super(nombre, descripcion);
    }

    @Override
    public double getAdicionalImpacto(){
        return 100;
    }

}
