package ar.edu.unlp.info.oo1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProteinaTest {

    private Proteina proteina;

    @BeforeEach
    void setUp() {
        proteina = new Proteina("pollo", "cubos", 2, 2200.0);
    }

    @Test
    void descripcionProteina() {
        assertEquals("Proteína de pollo en cubos (2 porciones a $2200 por porción)", proteina.getDescripcion());
    }

    @Test
    void costoProteina() {
        assertEquals(4400.0, proteina.getCostoEstimado());
    }
}
