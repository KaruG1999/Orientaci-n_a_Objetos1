package ar.edu.unlp.info.oo1;

import java.time.LocalDate;
public class main {

    public static void main(String[] args) {
        ReservaNatural reserva = new ReservaNatural("Reserva Natural de la Biosfera Yabotí");
        VisitaRecreativa visita1 = new VisitaRecreativa(LocalDate.of(2023, 10, 1), 2);
        VisitaEducativa visita2 = new VisitaEducativa(LocalDate.of(2023, 10, 2), 3);
        Participante participante1 = new Participante("Juan Perez", 12345678);
        Participante participante2 = new Participante("Maria Gomez", 87654321);
        visita1.agregarParticipante(participante1);
        visita1.agregarParticipante(participante2);
        visita2.agregarParticipante(participante1);
        reserva.agregarVisitaGuiada(visita1);
        reserva.agregarVisitaGuiada(visita2);
        System.out.println("Impacto total de las visitas guiadas: " + reserva.calcularImpactoTotal());
    }
    
}
