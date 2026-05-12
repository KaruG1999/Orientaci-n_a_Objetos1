package ar.edu.unlp.oo1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class ServicioEnviosTest {

    private LocalDate hoy;
    private LocalDate ayer;
    private LocalDate manana;
    private PersonaFisica persona;
    private ClienteCorporativo empresa;

    @BeforeEach
    void setUp() {
        hoy = LocalDate.of(2026, 5, 12);
        ayer = hoy.minusDays(1);
        manana = hoy.plusDays(1);

        persona = new PersonaFisica("Laura", "Calle 1", "11111111");
        empresa = new ClienteCorporativo("TechCorp", "Av. 2", "30-12345678-9");
    }

    // ============================================================
    // EnvioLocal
    // ============================================================

    @Test
    void envioLocalEstandarCuesta1000() {
        EnvioLocal envio = new EnvioLocal(hoy, "La Plata", "La Plata", 500, false);
        assertEquals(1000.0, envio.calcularCostoBase(), 0.01);
    }

    @Test
    void envioLocalRapidoCuesta1500() {
        EnvioLocal envio = new EnvioLocal(hoy, "La Plata", "La Plata", 500, true);
        assertEquals(1500.0, envio.calcularCostoBase(), 0.01);
    }

    // ============================================================
    // EnvioInterurbano — 3 particiones + bordes en 100 y 500 km
    // ============================================================

    @Test
    void interurbanoMenosDe100kmCuesta20PorGramo() {
        // 99 km, 100g → 100 × 20 = 2000
        EnvioInterurbano envio = new EnvioInterurbano(hoy, "A", "B", 100, 99);
        assertEquals(2000.0, envio.calcularCostoBase(), 0.01);
    }

    @Test
    void interurbanoBorde100kmCuesta25PorGramo() {
        // exactamente 100 km: cae en la segunda franja (100-500) → $25/g
        EnvioInterurbano envio = new EnvioInterurbano(hoy, "A", "B", 100, 100);
        assertEquals(2500.0, envio.calcularCostoBase(), 0.01);
    }

    @Test
    void interurbanoEntre100y500kmCuesta25PorGramo() {
        // 300 km, 100g → 100 × 25 = 2500
        EnvioInterurbano envio = new EnvioInterurbano(hoy, "A", "B", 100, 300);
        assertEquals(2500.0, envio.calcularCostoBase(), 0.01);
    }

    @Test
    void interurbanoBorde500kmCuesta25PorGramo() {
        // exactamente 500 km: sigue en la segunda franja → $25/g
        EnvioInterurbano envio = new EnvioInterurbano(hoy, "A", "B", 100, 500);
        assertEquals(2500.0, envio.calcularCostoBase(), 0.01);
    }

    @Test
    void interurbanoMasDe500kmCuesta30PorGramo() {
        // 501 km, 100g → 100 × 30 = 3000
        EnvioInterurbano envio = new EnvioInterurbano(hoy, "A", "B", 100, 501);
        assertEquals(3000.0, envio.calcularCostoBase(), 0.01);
    }

    // ============================================================
    // EnvioInternacional — peso <= 1kg vs > 1kg, con/sin rápido
    // ============================================================

    @Test
    void internacionalHasta1kgSinRapido() {
        // 1000g (exactamente 1 kg), sin rápido → 5000 + 1000 × 10 = 15000
        EnvioInternacional envio = new EnvioInternacional(hoy, "AR", "US", 1000, false);
        assertEquals(15000.0, envio.calcularCostoBase(), 0.01);
    }

    @Test
    void internacionalMasDe1kgSinRapido() {
        // 1001g, sin rápido → 5000 + 1001 × 12 = 5000 + 12012 = 17012
        EnvioInternacional envio = new EnvioInternacional(hoy, "AR", "US", 1001, false);
        assertEquals(17012.0, envio.calcularCostoBase(), 0.01);
    }

    @Test
    void internacionalConEntregaRapidaSuma800() {
        // 500g con rápido → 5000 + 500 × 10 + 800 = 10800
        EnvioInternacional envio = new EnvioInternacional(hoy, "AR", "US", 500, true);
        assertEquals(10800.0, envio.calcularCostoBase(), 0.01);
    }

    // ============================================================
    // Descuentos por tipo de cliente
    // ============================================================

    @Test
    void personaFisicaTiene10PorCientoDeDescuento() {
        EnvioLocal envio = new EnvioLocal(hoy, "LP", "LP", 100, false); // base 1000
        assertEquals(900.0, envio.calcularCosto(persona), 0.01);
    }

    @Test
    void clienteCorporativoNoTieneDescuento() {
        EnvioLocal envio = new EnvioLocal(hoy, "LP", "LP", 100, false); // base 1000
        assertEquals(1000.0, envio.calcularCosto(empresa), 0.01);
    }

    // ============================================================
    // montoEnPeriodo — filtrado por fecha
    // ============================================================

    @Test
    void montoEnPeriodoSumaEnviosDentroDelPeriodo() {
        // Agregar 2 envíos de hoy y 1 de mañana
        persona.agregarEnvio(new EnvioLocal(hoy, "LP", "LP", 100, false));   // base 1000 → 900
        persona.agregarEnvio(new EnvioLocal(hoy, "LP", "LP", 100, false));   // base 1000 → 900
        persona.agregarEnvio(new EnvioLocal(manana, "LP", "LP", 100, false)); // fuera del rango

        double monto = persona.montoEnPeriodo(ayer, hoy);
        assertEquals(1800.0, monto, 0.01);
    }

    @Test
    void montoEnPeriodoConSinEnviosEsCero() {
        double monto = empresa.montoEnPeriodo(ayer, hoy);
        assertEquals(0.0, monto, 0.01);
    }

    @Test
    void montoEnPeriodoIncluyeLosEnviosEnLosBordes() {
        empresa.agregarEnvio(new EnvioLocal(ayer, "LP", "LP", 100, false));  // borde inicial
        empresa.agregarEnvio(new EnvioLocal(hoy, "LP", "LP", 100, false));   // borde final

        double monto = empresa.montoEnPeriodo(ayer, hoy);
        assertEquals(2000.0, monto, 0.01);
    }
}
