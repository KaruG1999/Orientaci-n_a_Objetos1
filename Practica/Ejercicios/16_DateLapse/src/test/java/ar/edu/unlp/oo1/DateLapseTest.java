package ar.edu.unlp.oo1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class DateLapseTest {

    private DateLapse enero;

    @BeforeEach
    void setUp() {
        // Lapso: 1 de enero al 31 de enero = 30 días
        enero = new DateLapse(
            LocalDate.of(2026, 1, 1),
            LocalDate.of(2026, 1, 31)
        );
    }

    // --- sizeInDays ---

    @Test
    void sizeInDaysRetornaLaCantidadDeDias() {
        assertEquals(30, enero.sizeInDays());
    }

    @Test
    void sizeInDaysDeFechasIgualesEsCero() {
        LocalDate hoy = LocalDate.of(2026, 3, 15);
        DateLapse punto = new DateLapse(hoy, hoy);
        assertEquals(0, punto.sizeInDays());
    }

    // --- includesDate (particiones: antes / en el borde inicial / dentro / borde final / después) ---

    @Test
    void includesDateRetornaTrueParaFechaInterior() {
        LocalDate interior = LocalDate.of(2026, 1, 15);
        assertTrue(enero.includesDate(interior));
    }

    @Test
    void includesDateRetornaTrueParaElBordeInicial() {
        assertTrue(enero.includesDate(LocalDate.of(2026, 1, 1)));
    }

    @Test
    void includesDateRetornaTrueParaElBordeFinal() {
        assertTrue(enero.includesDate(LocalDate.of(2026, 1, 31)));
    }

    @Test
    void includesDateRetornaFalseParaFechaAnterior() {
        assertFalse(enero.includesDate(LocalDate.of(2025, 12, 31)));
    }

    @Test
    void includesDateRetornaFalseParaFechaPosterior() {
        assertFalse(enero.includesDate(LocalDate.of(2026, 2, 1)));
    }

    // --- overlaps ---

    @Test
    void overlapsConPeriodoQueSeSuperpone() {
        // [1 ene - 31 ene] solapa con [15 ene - 15 feb]
        DateLapse otro = new DateLapse(
            LocalDate.of(2026, 1, 15),
            LocalDate.of(2026, 2, 15)
        );
        assertTrue(enero.overlaps(otro));
    }

    @Test
    void overlapsEsSimetrico() {
        DateLapse otro = new DateLapse(
            LocalDate.of(2026, 1, 15),
            LocalDate.of(2026, 2, 15)
        );
        assertTrue(otro.overlaps(enero));
    }

    @Test
    void noOverlapsConPeriodoPosterior() {
        // [1 ene - 31 ene] NO solapa con [1 feb - 28 feb]
        DateLapse febrero = new DateLapse(
            LocalDate.of(2026, 2, 1),
            LocalDate.of(2026, 2, 28)
        );
        assertFalse(enero.overlaps(febrero));
    }

    @Test
    void overlapsBordeExacto() {
        // [1 ene - 31 ene] y [31 ene - 15 feb]: se tocan en el borde → sí solapan
        DateLapse bordeInicio = new DateLapse(
            LocalDate.of(2026, 1, 31),
            LocalDate.of(2026, 2, 15)
        );
        assertTrue(enero.overlaps(bordeInicio));
    }
}
