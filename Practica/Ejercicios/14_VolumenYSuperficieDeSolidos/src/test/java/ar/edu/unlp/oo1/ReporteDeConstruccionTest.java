package ar.edu.unlp.oo1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReporteDeConstruccionTest {

    // Escenario del @BeforeEach:
    //
    //   cilindro  → material="Hierro",  color="Rojo",   r=1, h=1
    //               volumen = π          superficie = 4π
    //
    //   esfera    → material="Hierro",  color="Azul",   r=1
    //               volumen = 4π/3       superficie = 4π
    //
    //   prisma    → material="Madera",  color="Rojo",   lM=2, lm=3, h=4
    //               volumen = 24         superficie = 52
    //
    // Particiones para volumenDeMaterial:
    //   - material presente en varias piezas ("Hierro")       → suma los dos volúmenes
    //   - material presente en una sola pieza ("Madera")      → devuelve ese volumen
    //   - material inexistente ("Plastico")                    → 0.0  (valor borde)
    //
    // Particiones para superficieDeColor:
    //   - color presente en varias piezas ("Rojo")            → suma las dos superficies
    //   - color presente en una sola pieza ("Azul")           → devuelve esa superficie
    //   - color inexistente ("Verde")                          → 0.0  (valor borde)
    //
    // Caso borde adicional: reporte sin piezas → ambos métodos retornan 0.0

    private static final double DELTA = 1e-6;

    private ReporteDeConstruccion reporte;

    @BeforeEach
    void setUp() {
        reporte = new ReporteDeConstruccion();
        reporte.agregarPieza(new Cilindro("Hierro", "Rojo",  1, 1));
        reporte.agregarPieza(new Esfera  ("Hierro", "Azul",  1));
        reporte.agregarPieza(new PrismaRectangular("Madera", "Rojo", 2, 3, 4));
    }

    // ── volumenDeMaterial ─────────────────────────────────────────────────────

    @Test
    void volumenMaterialPresente_variasPiezas() {
        // cilindro (π) + esfera (4π/3)
        double esperado = Math.PI + (4.0 / 3.0) * Math.PI;
        assertEquals(esperado, reporte.volumenDeMaterial("Hierro"), DELTA);
    }

    @Test
    void volumenMaterialPresente_unaSolaPieza() {
        // prisma → 24
        assertEquals(24.0, reporte.volumenDeMaterial("Madera"), DELTA);
    }

    @Test
    void volumenMaterialInexistente_esZero() {
        assertEquals(0.0, reporte.volumenDeMaterial("Plastico"), DELTA);
    }

    // ── superficieDeColor ─────────────────────────────────────────────────────

    @Test
    void superficieColorPresente_variasPiezas() {
        // cilindro (4π) + prisma (52)
        double esperado = 4 * Math.PI + 52.0;
        assertEquals(esperado, reporte.superficieDeColor("Rojo"), DELTA);
    }

    @Test
    void superficieColorPresente_unaSolaPieza() {
        // esfera (4π)
        assertEquals(4 * Math.PI, reporte.superficieDeColor("Azul"), DELTA);
    }

    @Test
    void superficieColorInexistente_esZero() {
        assertEquals(0.0, reporte.superficieDeColor("Verde"), DELTA);
    }

    // ── reporte vacío ─────────────────────────────────────────────────────────

    @Test
    void reporteVacio_volumenEsZero() {
        ReporteDeConstruccion vacio = new ReporteDeConstruccion();
        assertEquals(0.0, vacio.volumenDeMaterial("Hierro"), DELTA);
    }

    @Test
    void reporteVacio_superficieEsZero() {
        ReporteDeConstruccion vacio = new ReporteDeConstruccion();
        assertEquals(0.0, vacio.superficieDeColor("Rojo"), DELTA);
    }
}
