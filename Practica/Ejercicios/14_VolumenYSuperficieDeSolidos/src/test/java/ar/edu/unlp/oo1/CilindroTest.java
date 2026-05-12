package ar.edu.unlp.oo1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CilindroTest {

    // Particiones:
    //   - radio y altura positivos (caso normal)
    //   - radio = 1, altura = 1 (valor mínimo simple, fácil de verificar a mano)
    //   - radio = 2, altura = 3 (valores distintos)
    // Fórmulas:
    //   Volumen           = π * r² * h
    //   SuperficieExt.    = 2πr*h + 2πr²

    private static final double DELTA = 1e-6;

    private Cilindro cilindroUnitario;   // r=1, h=1
    private Cilindro cilindroGeneral;    // r=2, h=3

    @BeforeEach
    void setUp() {
        cilindroUnitario = new Cilindro("Hierro", "Rojo", 1, 1);
        cilindroGeneral  = new Cilindro("Acero", "Azul", 2, 3);
    }

    @Test
    void getMaterial() {
        assertEquals("Hierro", cilindroUnitario.getMaterial());
    }

    @Test
    void getColor() {
        assertEquals("Rojo", cilindroUnitario.getColor());
    }

    // Volumen = π * 1² * 1 = π
    @Test
    void volumenCilindroUnitario() {
        assertEquals(Math.PI, cilindroUnitario.getVolumen(), DELTA);
    }

    // Volumen = π * 2² * 3 = 12π
    @Test
    void volumenCilindroGeneral() {
        assertEquals(12 * Math.PI, cilindroGeneral.getVolumen(), DELTA);
    }

    // Superficie = 2π*1*1 + 2π*1² = 2π + 2π = 4π
    @Test
    void superficieCilindroUnitario() {
        assertEquals(4 * Math.PI, cilindroUnitario.getSuperficieExterior(), DELTA);
    }

    // Superficie = 2π*2*3 + 2π*2² = 12π + 8π = 20π
    @Test
    void superficieCilindroGeneral() {
        assertEquals(20 * Math.PI, cilindroGeneral.getSuperficieExterior(), DELTA);
    }
}
