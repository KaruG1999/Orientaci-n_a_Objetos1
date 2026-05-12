package ar.edu.unlp.oo1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class PoliticasCancelacionTest {

    private Reserva reserva;
    private LocalDate inicioReserva;

    @BeforeEach
    void setUp() {
        inicioReserva = LocalDate.of(2026, 6, 1);
        DateLapse periodo = new DateLapse(inicioReserva, LocalDate.of(2026, 6, 10));
        // 9 noches × $1000 = $9000
        Propiedad propiedad = new Propiedad("Depto", "CABA", 1000.0, new PoliticaFlexible());
        Usuario inquilino = new Usuario("Carlos");
        reserva = new Reserva(inquilino, propiedad, periodo);
    }

    // ============================================================
    // Política FLEXIBLE
    // ============================================================

    @Test
    void politicaFlexibleReembolsaEl100PorCientoSinImportarLaFecha() {
        PoliticaFlexible politica = new PoliticaFlexible();
        LocalDate cancelacion = inicioReserva.minusDays(1); // 1 día antes

        assertEquals(9000.0, politica.calcularReembolso(reserva, cancelacion), 0.01);
    }

    @Test
    void politicaFlexibleReembolsaEl100PorCientoConMuchosDiasDeAntelacion() {
        PoliticaFlexible politica = new PoliticaFlexible();
        LocalDate cancelacion = inicioReserva.minusDays(30);

        assertEquals(9000.0, politica.calcularReembolso(reserva, cancelacion), 0.01);
    }

    // ============================================================
    // Política MODERADA — 3 particiones + 2 bordes
    // ============================================================

    @Test
    void politicaModeradaMasDe7DiasReembolsaEl100() {
        PoliticaModerada politica = new PoliticaModerada();
        LocalDate cancelacion = inicioReserva.minusDays(8); // 8 días antes → > 7

        assertEquals(9000.0, politica.calcularReembolso(reserva, cancelacion), 0.01);
    }

    @Test
    void politicaModeradaBorde7DiasReembolsaEl50() {
        PoliticaModerada politica = new PoliticaModerada();
        LocalDate cancelacion = inicioReserva.minusDays(7); // exactamente 7 días → > 2 pero no > 7

        assertEquals(4500.0, politica.calcularReembolso(reserva, cancelacion), 0.01);
    }

    @Test
    void politicaModeradaEntre2y7DiasReembolsaEl50() {
        PoliticaModerada politica = new PoliticaModerada();
        LocalDate cancelacion = inicioReserva.minusDays(4); // 4 días → > 2

        assertEquals(4500.0, politica.calcularReembolso(reserva, cancelacion), 0.01);
    }

    @Test
    void politicaModeradaBorde2DiasNoReembolsa() {
        PoliticaModerada politica = new PoliticaModerada();
        LocalDate cancelacion = inicioReserva.minusDays(2); // exactamente 2 días → no > 2

        assertEquals(0.0, politica.calcularReembolso(reserva, cancelacion), 0.01);
    }

    @Test
    void politicaModeradaMenosDe2DiasNoReembolsa() {
        PoliticaModerada politica = new PoliticaModerada();
        LocalDate cancelacion = inicioReserva.minusDays(1); // 1 día antes

        assertEquals(0.0, politica.calcularReembolso(reserva, cancelacion), 0.01);
    }

    // ============================================================
    // Política ESTRICTA
    // ============================================================

    @Test
    void politicaEstrictaNoReembolsaNada() {
        PoliticaEstricta politica = new PoliticaEstricta();
        LocalDate cancelacion = inicioReserva.minusDays(30);

        assertEquals(0.0, politica.calcularReembolso(reserva, cancelacion), 0.01);
    }

    // ============================================================
    // Integración: Propiedad con política cambiable
    // ============================================================

    @Test
    void propiedadPuedeCambiarDePolitica() {
        Propiedad prop = new Propiedad("Cabaña", "Bariloche", 1000.0, new PoliticaEstricta());
        prop.setPolitica(new PoliticaFlexible());
        // Verificar que la política fue reemplazada
        assertInstanceOf(PoliticaFlexible.class, prop.getPolitica());
    }

    @Test
    void cancelarConReembolsoFlexibleRetornaPrecioTotal() {
        Propiedad prop = new Propiedad("Cabaña", "Bariloche", 1000.0, new PoliticaFlexible());
        Usuario inquilino = new Usuario("Diana");
        DateLapse futuro = new DateLapse(
            LocalDate.now().plusDays(30),
            LocalDate.now().plusDays(40)
        );
        Reserva r = inquilino.reservar(prop, futuro);
        double reembolso = prop.cancelarConReembolso(r, LocalDate.now());

        assertEquals(r.getPrecioTotal(), reembolso, 0.01);
        // Propiedad debe estar disponible de nuevo
        assertTrue(prop.estaDisponible(futuro));
    }
}
