package ar.edu.unlp.info.oo1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class VisitaRecreativaTest {
    
    private VisitaRecreativa visitaVacia;
    private VisitaRecreativa visitaConGente;
    private Participante p1;
    private Participante p2;

    @BeforeEach
    void setUp() {
        // Escenario 1: Visita de 2 horas sin nadie
        visitaVacia = new VisitaRecreativa(LocalDate.now(), 2);

        // Escenario 2: Visita de 3 horas con 2 personas
        visitaConGente = new VisitaRecreativa(LocalDate.now(), 3);
        p1 = new Participante("Ana", 111);
        p2 = new Participante("Zulma", 222);
        
        visitaConGente.agregarParticipante(p1);
        visitaConGente.agregarParticipante(p2);
    }

    @Test
    public void testCalcularImpacto_ListaVacia() {
        // 0 personas * 2 horas = 0
        assertEquals( 0, visitaVacia.calcularImpacto(), 0.01);
    }

    @Test
    public void testCalcularImpacto_ConParticipantes() {
        // 2 personas * 3 horas = 6
        assertEquals(6, visitaConGente.calcularImpacto(), 0.01);
    }
    
}
