package ar.edu.unlp.info.oo1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CondimentoTest {

    private Condimento picante;
    private Condimento noPicante;

    @BeforeEach
    void setUp() {
        picante   = new Condimento("mix provenzal", 3, true);
        noPicante = new Condimento("mix provenzal", 3, false);
    }

    @Test
    void descripcionCondimentoPicante() {
        assertEquals("Condimento mix provenzal (picante, 3 cucharaditas)", picante.getDescripcion());
    }

    @Test
    void descripcionCondimentoNoPicante() {
        assertEquals("Condimento mix provenzal (no picante, 3 cucharaditas)", noPicante.getDescripcion());
    }

    @Test
    void costoCondimentoEsCero() {
        assertEquals(0.0, noPicante.getCostoEstimado());
    }
}
