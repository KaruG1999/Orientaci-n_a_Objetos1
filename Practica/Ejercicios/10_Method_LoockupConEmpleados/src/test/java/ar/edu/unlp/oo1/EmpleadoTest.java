package ar.edu.unlp.oo1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests de Method Lookup con jerarquía de Empleados.
 *
 * Jerarquía:
 *   Empleado
 *     └─ EmpleadoJerarquico
 *           └─ Gerente
 *
 * Cálculos esperados:
 *
 * Empleado:
 *   montoBasico()   = 35000
 *   aportes()       = 13500
 *   sueldoBasico()  = 35000 + 13500 = 48500
 *
 * EmpleadoJerarquico:
 *   montoBasico()   = 45000   (override)
 *   aportes()       = 13500   (heredado de Empleado)
 *   bonoPorCategoria() = 8000
 *   sueldoBasico()  = super.sueldoBasico() + bonoPorCategoria()
 *                   = (45000 + 13500) + 8000 = 66500
 *   ─ Nota: super.sueldoBasico() llama this.montoBasico() y this.aportes()
 *     donde this es EmpleadoJerarquico → usa sus propias versiones.
 *
 * Gerente:
 *   montoBasico()   = 57000   (override)
 *   aportes()       = 57000 * 0.05 = 2850  (override — % de su monto)
 *   sueldoBasico()  = (57000 + 2850) + 8000 = 67850
 *   ─ Nota: hereda sueldoBasico() de EmpleadoJerarquico, que internamente
 *     usa this.montoBasico() y this.aportes() → despacha a Gerente.
 */
public class EmpleadoTest {

    private Empleado empleado;
    private EmpleadoJerarquico jerarquico;
    private Gerente gerente;

    @BeforeEach
    void setUp() {
        empleado   = new Empleado("Ana");
        jerarquico = new EmpleadoJerarquico("Bruno");
        gerente    = new Gerente("Carlos");
    }

    // ============================================================
    // Empleado base
    // ============================================================

    @Test
    void empleadoMontoBasico() {
        assertEquals(35000, empleado.montoBasico());
    }

    @Test
    void empleadoAportes() {
        assertEquals(13500, empleado.aportes());
    }

    @Test
    void empleadoSueldoBasico() {
        // 35000 + 13500 = 48500
        assertEquals(48500, empleado.sueldoBasico());
    }

    // ============================================================
    // EmpleadoJerarquico
    // ============================================================

    @Test
    void jerarquicoMontoBasico() {
        assertEquals(45000, jerarquico.montoBasico());
    }

    @Test
    void jerarquicoAportesHeredadoDeEmpleado() {
        // No redefine aportes(), hereda el valor fijo de Empleado
        assertEquals(13500, jerarquico.aportes());
    }

    @Test
    void jerarquicoBonoPorCategoria() {
        assertEquals(8000, jerarquico.bonoPorCategoria());
    }

    @Test
    void jerarquicoSueldoBasico() {
        // super.sueldoBasico() = this.montoBasico() + this.aportes()
        //                      = 45000 + 13500 = 58500
        // + bonoPorCategoria() = 8000
        // total = 66500
        assertEquals(66500, jerarquico.sueldoBasico());
    }

    // ============================================================
    // Gerente
    // ============================================================

    @Test
    void gerenteMontoBasico() {
        assertEquals(57000, gerente.montoBasico());
    }

    @Test
    void gerenteAportesEsPorcentajeDeSuMontoBasico() {
        // aportes() = montoBasico() * 0.05 = 57000 * 0.05 = 2850
        assertEquals(2850, gerente.aportes(), 0.001);
    }

    @Test
    void gerenteSueldoBasico() {
        // sueldoBasico() heredado de EmpleadoJerarquico:
        //   super.sueldoBasico() con this = Gerente →
        //     this.montoBasico() = 57000
        //     this.aportes()     = 2850
        //   Empleado.sueldoBasico() = 57000 + 2850 = 59850
        // + bonoPorCategoria() = 8000
        // total = 67850
        assertEquals(67850, gerente.sueldoBasico(), 0.001);
    }

    // ============================================================
    // Method Lookup: variable de tipo superclase, objeto de subclase
    // ============================================================

    @Test
    void methodLookupEmpleadoVariableConGerenteObjeto() {
        // Variable tipo Empleado → objeto real Gerente.
        // El compilador solo ve los métodos de Empleado,
        // pero en runtime se despacha a Gerente.
        Empleado emp = new Gerente("Diana");
        assertEquals(57000, emp.montoBasico());   // Gerente.montoBasico()
        assertEquals(2850,  emp.aportes(), 0.001); // Gerente.aportes()
        assertEquals(67850, emp.sueldoBasico(), 0.001); // Gerente.sueldoBasico()
    }

    @Test
    void methodLookupEmpleadoVariableConJerarquicoObjeto() {
        // Variable tipo Empleado → objeto real EmpleadoJerarquico.
        Empleado emp = new EmpleadoJerarquico("Elena");
        assertEquals(45000, emp.montoBasico());
        assertEquals(13500, emp.aportes());
        assertEquals(66500, emp.sueldoBasico());
    }

    @Test
    void methodLookupJerarquicoVariableConGerenteObjeto() {
        // Variable tipo EmpleadoJerarquico → objeto real Gerente.
        // Los métodos de Gerente se usan aunque la variable sea del supertipo.
        EmpleadoJerarquico emp = new Gerente("Fabio");
        assertEquals(57000, emp.montoBasico());
        assertEquals(8000, emp.bonoPorCategoria()); // heredado, sin cambio
        assertEquals(67850, emp.sueldoBasico(), 0.001);
    }

    @Test
    void superEnContextoDeGerenteUsaMetodosDelObjetoReal() {
        // Cuando EmpleadoJerarquico.sueldoBasico() llama super.sueldoBasico(),
        // ese super.sueldoBasico() se ejecuta con this = Gerente.
        // Por eso this.montoBasico() devuelve 57000 (Gerente) y no 45000 (EmpleadoJerarquico).
        // Este test lo documenta explícitamente: sueldoBasico de un Gerente
        // NO es 45000+13500+8000 = 66500, SINO 57000+2850+8000 = 67850.
        assertNotEquals(66500, gerente.sueldoBasico(), 0.001);
        assertEquals(67850, gerente.sueldoBasico(), 0.001);
    }
}
