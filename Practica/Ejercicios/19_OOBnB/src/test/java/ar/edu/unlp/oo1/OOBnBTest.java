package ar.edu.unlp.oo1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class OOBnBTest {

    private Usuario propietario;
    private Usuario inquilino;
    private Propiedad propiedad;
    private DateLapse enero;
    private DateLapse febrero;
    private DateLapse mediadosEneroFebrero;

    @BeforeEach
    void setUp() {
        propietario = new Usuario("Ana", "Calle 1", "12345678");
        inquilino = new Usuario("Bob", "Calle 2", "87654321");

        // $1000 por noche
        propiedad = new Propiedad("Casa de playa", "Mar del Plata", 1000.0);
        propietario.agregarPropiedad(propiedad);

        enero = new DateLapse(LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 31));
        febrero = new DateLapse(LocalDate.of(2026, 2, 1), LocalDate.of(2026, 2, 28));
        mediadosEneroFebrero = new DateLapse(LocalDate.of(2026, 1, 15), LocalDate.of(2026, 2, 15));
    }

    // --- Disponibilidad ---

    @Test
    void propiedadSinReservasEstaDisponible() {
        assertTrue(propiedad.estaDisponible(enero));
    }

    @Test
    void propiedadConReservaNoestaDisponibleEnPeriodoSolapado() {
        inquilino.reservar(propiedad, enero);
        assertFalse(propiedad.estaDisponible(mediadosEneroFebrero));
    }

    @Test
    void propiedadConReservaEstaDisponibleEnPeriodoDistinto() {
        inquilino.reservar(propiedad, enero);
        assertTrue(propiedad.estaDisponible(febrero));
    }

    // --- Reservar ---

    @Test
    void reservarDisponibleCreaReserva() {
        Reserva r = inquilino.reservar(propiedad, enero);
        assertNotNull(r);
    }

    @Test
    void reservarNoDisponibleRetornaNull() {
        inquilino.reservar(propiedad, enero);
        // Segundo intento en el mismo período
        Reserva r2 = inquilino.reservar(propiedad, enero);
        assertNull(r2);
    }

    // --- Precio de reserva ---

    @Test
    void precioTotalDeReservaEsNochesXPrecioPorNoche() {
        // enero tiene 30 días → 30 noches × $1000 = $30000
        Reserva r = inquilino.reservar(propiedad, enero);
        assertEquals(30000.0, r.getPrecioTotal(), 0.01);
    }

    // --- Cancelar ---

    @Test
    void cancelarReservaFuturaLiberaPropiedad() {
        // Reserva en el futuro lejano → se puede cancelar
        DateLapse futuro = new DateLapse(
            LocalDate.now().plusDays(30),
            LocalDate.now().plusDays(40)
        );
        Reserva r = inquilino.reservar(propiedad, futuro);
        assertTrue(r.cancelar());
        // Después de cancelar, la propiedad debe estar disponible de nuevo
        assertTrue(propiedad.estaDisponible(futuro));
    }

    @Test
    void cancelarReservaEnCursoFalla() {
        // Reserva que ya comenzó (del pasado al futuro)
        DateLapse enCurso = new DateLapse(
            LocalDate.now().minusDays(5),
            LocalDate.now().plusDays(5)
        );
        Reserva r = inquilino.reservar(propiedad, enCurso);
        assertFalse(r.cancelar());
    }

    // --- Ingresos del propietario ---

    @Test
    void ingresosDelPropietarioEsEl75PorCiento() {
        // enero: 30 noches × $1000 = $30000 → propietario recibe 75% = $22500
        inquilino.reservar(propiedad, enero);
        DateLapse todo2026 = new DateLapse(LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31));
        assertEquals(22500.0, propiedad.calcularIngresosEnPeriodo(todo2026), 0.01);
    }

    @Test
    void ingresosConSinReservasEsCero() {
        DateLapse todo2026 = new DateLapse(LocalDate.of(2026, 1, 1), LocalDate.of(2026, 12, 31));
        assertEquals(0.0, propiedad.calcularIngresosEnPeriodo(todo2026), 0.01);
    }
}
