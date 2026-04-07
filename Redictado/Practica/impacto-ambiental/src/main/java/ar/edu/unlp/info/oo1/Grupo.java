package ar.edu.unlp.info.oo1;

import java.util.ArrayList;
import java.util.List;

public class Grupo {
    
    private String nombre;
    private int id;
    private List<Participante> alumnos;
    private List<Participante> docentes;

    public Grupo (String nombre, int id){
        this.nombre = nombre;
        this.id = id;
        this.alumnos = new ArrayList<>();
        this.docentes = new ArrayList<>();
    }

    public void agregarAlumno(Participante alumno){
        this.alumnos.add(alumno);
    }

    public void agregarDocente(Participante docente){
        this.docentes.add(docente);
    }

    // El impacto ambiental de un grupo es de 0.5 puntos por alumno por hora. A eso se le resta 1 punto por hora por cada docente responsable
    public double calcularImpactoGrupo(int duracionHoras){
        double puntos = this.alumnos.size() * 0.5 * duracionHoras;
        puntos -= this.docentes.size() * duracionHoras;
        double impacto = Math.max(2.0 * duracionHoras, puntos);
        return impacto;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public List<Participante> getAlumnos(){
        return this.alumnos;
    }

    public List<Participante> getDocentes(){
        return this.docentes;
    }

}
