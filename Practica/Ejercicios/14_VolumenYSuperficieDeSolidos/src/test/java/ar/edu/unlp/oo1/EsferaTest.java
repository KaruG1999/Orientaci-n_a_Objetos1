package ar.edu.unlp.oo1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EsferaTest {

    // Particiones:
    //   - radio = 1 (valor unitario, fácil de verificar)
    //   - radio = 3 (valor mayor)
    // Fórmulas:
    //   Volumen         = (4/3) * π * r³
    //   SuperficieExt.  = 4 * π * r²
    // Caso notable: con r=3, volumen y superficie dan el mismo resultado numérico
    //   (36π), lo que permite verificar independencia de las fórmulas.

    private static final double DELTA = 1e-6;

    private Esfera esferaUnitaria;   // r=1
    private Esfera esferaGeneral;    // r=3

    @BeforeEach
    void setUp() {
        esferaUnitaria = new Esfera("Hierro", "Rojo", 1);
        esferaGeneral  = new Esfera("Madera", "Verde", 3);
    }

    @Test
    void getMaterial() {
        assertEquals("Hierro", esferaUnitaria.getMaterial());
    }

    @Test
    void getColor() {
        assertEquals("Rojo", esferaUnitaria.getColor());
    }

    // Volumen = (4/3) * π * 1³ = 4π/3
    @Test
    void volumenEsferaUnitaria() {
        assertEquals((4.0 / 3.0) * Math.PI, esferaUnitaria.getVolumen(), DELTA);
    }

    // Volumen = (4/3) * π * 27 = 36π
    @Test
    void volumenEsferaGeneral() {
        assertEquals(36 * Math.PI, esferaGeneral.getVolumen(), DELTA);
    }

    // Superficie = 4 * π * 1² = 4π
    @Test
    void superficieEsferaUnitaria() {
        assertEquals(4 * Math.PI, esferaUnitaria.getSuperficieExterior(), DELTA);
    }

    // Superficie = 4 * π * 9 = 36π
    @Test
    void superficieEsferaGeneral() {
        assertEquals(36 * Math.PI, esferaGeneral.getSuperficieExterior(), DELTA);
    }
}
