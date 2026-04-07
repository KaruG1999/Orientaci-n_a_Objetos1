package ar.edu.unlp.info.oo1;

public class Participante {

    private String nombre;
    private int dni;

    public Participante(String nombre, int dni){
        this.nombre= nombre;
        this.dni = dni;
    }

    public String getNombre(){
        return this.nombre;
    }

    public int getDni(){
        return this.dni;
    }

}
