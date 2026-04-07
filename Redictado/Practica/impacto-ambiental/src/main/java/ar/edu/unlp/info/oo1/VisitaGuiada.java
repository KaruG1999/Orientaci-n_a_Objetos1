package ar.edu.unlp.info.oo1;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public abstract class VisitaGuiada {

    private LocalDate fecha;
    private int duracionHoras;
    private List<Participante> participantes;

    public VisitaGuiada (LocalDate fecha, int duracionHoras){
        this.fecha = fecha;
        this.duracionHoras = duracionHoras;
        this.participantes = new ArrayList<>();
    }
 
    // Es publico porque se va a usar en ReservaNatural para calcular el impacto total de las visitas guiadas
    public abstract double calcularImpacto();   

    // Debe devolver la lista de participantes ordenados alfabeticamente por su nombre.
    public List<Participante> getAsistentesOrdenados() {
        return this.participantes.stream() // 1. Convertimos la lista en un flujo (stream)
               
               // 2. Ordenamos usando un comparador
               .sorted((p1, p2) -> p1.getNombre().compareToIgnoreCase(p2.getNombre()))
               
               // 3. Volvemos a transformar el flujo en una Lista nueva
               .collect(Collectors.toList()); 
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getDuracionHoras() {
        return duracionHoras;
    }

    // agregar participnate no es abstracto porque cada tipo de visita guiada va a tener una forma diferente de agregar participantes, por ejemplo, la visita recreativa va a permitir agregar participantes hasta un máximo de 20, mientras que la visita educativa va a permitir agregar participantes sin límite.
    public void agregarParticipante(Participante participante){
        this.participantes.add(participante);
    };


}
