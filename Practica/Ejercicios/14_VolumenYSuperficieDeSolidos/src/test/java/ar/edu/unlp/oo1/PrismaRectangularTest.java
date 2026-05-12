package ar.edu.unlp.oo1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PrismaRectangularTest {

    // Particiones:
    //   - lados y altura con valores enteros positivos (sin π, resultados exactos)
    // Fórmulas:
    //   Volumen         = ladoMayor * ladoMenor * altura
    //   SuperficieExt.  = 2 * (lM*lm + lM*h + lm*h)

    private static final double DELTA = 1e-6;

    private PrismaRectangular prismaA;   // lM=2, lm=3, h=4
    private PrismaRectangular prismaB;   // lM=5, lm=3, h=2

    @BeforeEach
    void setUp() {
        prismaA = new PrismaRectangular("Madera", "Verde", 2, 3, 4);
        prismaB = new PrismaRectangular("Plastico", "Rojo",  5, 3, 2);
    }

    @Test
    void getMaterial() {
        assertEquals("Madera", prismaA.getMaterial());
    }

    @Test
    void getColor() {
        assertEquals("Verde", prismaA.getColor());
    }

    // Volumen = 2 * 3 * 4 = 24
    @Test
    void volumenPrismaA() {
        assertEquals(24.0, prismaA.getVolumen(), DELTA);
    }

    // Volumen = 5 * 3 * 2 = 30
    @Test
    void volumenPrismaB() {
        assertEquals(30.0, prismaB.getVolumen(), DELTA);
    }

    // Superficie = 2 * (2*3 + 2*4 + 3*4) = 2 * (6 + 8 + 12) = 52
    @Test
    void superficiePrismaA() {
        assertEquals(52.0, prismaA.getSuperficieExterior(), DELTA);
    }

    // Superficie = 2 * (5*3 + 5*2 + 3*2) = 2 * (15 + 10 + 6) = 62
    @Test
    void superficiePrismaB() {
        assertEquals(62.0, prismaB.getSuperficieExterior(), DELTA);
    }
}
