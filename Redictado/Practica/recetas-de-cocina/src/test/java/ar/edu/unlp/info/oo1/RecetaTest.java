package ar.edu.unlp.info.oo1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RecetaTest {

    private Receta recetaVacia;
    private Receta recetaCompleta;

    @BeforeEach
    void setUp() {
        recetaVacia = new Receta("Bowl tibio de pollo");

        recetaCompleta = new Receta("Bowl tibio de pollo");
        recetaCompleta.agregarComponente(new Base("arroz", 2, true));
        recetaCompleta.agregarComponente(new Condimento("mix provenzal", 3, false));
        recetaCompleta.agregarComponente(new Proteina("pollo", "cubos", 2, 2200.0));
    }

    @Test
    void descripcionRecetaSinComponentes() {
        assertEquals("Receta \"Bowl tibio de pollo\"", recetaVacia.getDescripcion());
    }

    @Test
    void descripcionRecetaConComponentes() {
        String esperado = "Receta \"Bowl tibio de pollo\"\n" +
                          "1. Base de arroz (integral, 2 porciones)\n" +
                          "2. Condimento mix provenzal (no picante, 3 cucharaditas)\n" +
                          "3. Proteína de pollo en cubos (2 porciones a $2200 por porción)";
        assertEquals(esperado, recetaCompleta.getDescripcion());
    }

    @Test
    void costoRecetaSinComponentes() {
        assertEquals(0.0, recetaVacia.getCostoEstimado());
    }

    @Test
    void costoRecetaSoloCondimentos() {
        Receta receta = new Receta("Solo condimentos");
        receta.agregarComponente(new Condimento("sal", 1, false));
        receta.agregarComponente(new Condimento("pimienta", 1, false));
        assertEquals(0.0, receta.getCostoEstimado());
    }

    @Test
    void costoRecetaConTodosLosTipos() {
        // Base integral $2200 + condimento $0 + proteína 2 * $2200 = $6600
        assertEquals(6600.0, recetaCompleta.getCostoEstimado());
    }
}
