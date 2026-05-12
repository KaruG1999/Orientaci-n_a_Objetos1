package ar.edu.unlp.info.oo1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CondimentoTest {

    private CondimentoPicante picante;
    private CondimentoNoPicante noPicante;

    @BeforeEach
    void setUp() {
        picante   = new CondimentoPicante("mix provenzal", 3);
        noPicante = new CondimentoNoPicante("mix provenzal", 3);
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

    @Test
    void costoCondimentoPicanteEsCero() {
        assertEquals(0.0, picante.getCostoEstimado());
    }
}
