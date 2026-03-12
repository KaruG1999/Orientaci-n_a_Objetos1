package ar.edu.unlp.oo1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests del Ejercicio 11 — Cuenta con Ganchos (Template Method).
 *
 * PATRÓN QUE SE PRACTICA:
 *   - Template Method: Cuenta define el esqueleto de extraer() y tranferir()
 *     usando el gancho abstracto puedeExtraer(). Cada subclase implementa
 *     su propia política de autorización.
 *
 * ESTRUCTURA DE LA JERARQUÍA:
 *
 *        Cuenta (abstract)
 *        ├─ depositar(monto)          ← concreto
 *        ├─ extraer(monto)            ← template: llama puedeExtraer() + extraerSinControlar()
 *        ├─ transferirACuenta(...)    ← template: igual
 *        └─ puedeExtraer(monto)      ← abstract (el "gancho")
 *              │
 *   ┌──────────┴──────────┐
 *   CajaDeAhorro           CuentaCorriente
 *   puedeExtraer:          puedeExtraer:
 *   saldo >= monto*1.02    (saldo - monto) >= -descubierto
 *
 * IMPORTANTE — CajaDeAhorro también sobreescribe extraer() y depositar()
 * para aplicar el 2% de comisión AL MONTO REAL descontado/acreditado.
 * Es una limitación del diseño actual: si no se sobreescribieran,
 * el template de Cuenta llamaría extraerSinControlar(monto) sin el recargo.
 */
public class CuentaTest {

    private CuentaCorriente corriente;
    private CajaDeAhorro caja;

    @BeforeEach
    void setUp() {
        corriente = new CuentaCorriente();
        corriente.depositar(1000);

        caja = new CajaDeAhorro();
        caja.depositar(1000); // 2% de comisión → saldo real: 980
    }

    // ============================================================
    // CuentaCorriente
    // ============================================================

    @Test
    void corrienteNuevaArrancarEnCero() {
        CuentaCorriente nueva = new CuentaCorriente();
        assertEquals(0, nueva.getSaldo());
        assertEquals(0, nueva.getDescubierto());
    }

    @Test
    void corrienteDepositarAumentaSaldo() {
        CuentaCorriente nueva = new CuentaCorriente();
        nueva.depositar(500);
        assertEquals(500, nueva.getSaldo());
    }

    @Test
    void corrienteExtraerConFondosSuficientes() {
        boolean resultado = corriente.extraer(400);
        assertTrue(resultado);
        assertEquals(600, corriente.getSaldo());
    }

    @Test
    void corrienteExtraerSinFondosYSinDescubierto() {
        boolean resultado = corriente.extraer(1001);
        assertFalse(resultado);
        assertEquals(1000, corriente.getSaldo()); // saldo no cambia
    }

    @Test
    void corrienteExtraerDentroDelDescubierto() {
        corriente.setDescubierto(500);
        // saldo 1000, extrae 1300 → queda en -300 (dentro del límite de 500)
        boolean resultado = corriente.extraer(1300);
        assertTrue(resultado);
        assertEquals(-300, corriente.getSaldo());
    }

    @Test
    void corrienteExtraerExactoAlLimiteDeDescubierto() {
        corriente.setDescubierto(500);
        // saldo 1000, extrae 1500 → queda exactamente en -500
        boolean resultado = corriente.extraer(1500);
        assertTrue(resultado);
        assertEquals(-500, corriente.getSaldo());
    }

    @Test
    void corrienteExtraerSuperandoElDescubierto() {
        corriente.setDescubierto(500);
        // saldo 1000, extrae 1501 → quedaría en -501 (supera el límite)
        boolean resultado = corriente.extraer(1501);
        assertFalse(resultado);
        assertEquals(1000, corriente.getSaldo()); // saldo no cambia
    }

    @Test
    void corrienteTransferirACuentaExitosa() {
        CuentaCorriente destino = new CuentaCorriente();
        boolean resultado = corriente.transferirACuenta(300, destino);
        assertTrue(resultado);
        assertEquals(700, corriente.getSaldo());
        assertEquals(300, destino.getSaldo());
    }

    @Test
    void corrienteTransferirSinFondosFalla() {
        CuentaCorriente destino = new CuentaCorriente();
        boolean resultado = corriente.transferirACuenta(1001, destino);
        assertFalse(resultado);
        assertEquals(1000, corriente.getSaldo()); // sin cambios
        assertEquals(0, destino.getSaldo());       // sin cambios
    }

    // ============================================================
    // CajaDeAhorro
    // ============================================================

    @Test
    void cajaNuevaArrancarEnCero() {
        CajaDeAhorro nueva = new CajaDeAhorro();
        assertEquals(0, nueva.getSaldo());
    }

    @Test
    void cajaDepositoCobraComisionDelDos() {
        CajaDeAhorro nueva = new CajaDeAhorro();
        nueva.depositar(1000);
        // 2% de comisión: se acreditan 1000 * 0.98 = 980
        assertEquals(980, nueva.getSaldo(), 0.001);
    }

    @Test
    void cajaExtraerCobraComisionDelDos() {
        // caja tiene saldo 980 (luego del setUp con depósito de 1000)
        // extraer 100 → cuesta 100 * 1.02 = 102
        boolean resultado = caja.extraer(100);
        assertTrue(resultado);
        assertEquals(878, caja.getSaldo(), 0.001); // 980 - 102 = 878
    }

    @Test
    void cajaExtraerAlLimiteExacto() {
        // caja tiene saldo 980
        // extraer 960 → cuesta 960 * 1.02 = 979.2 ≤ 980 → debe poder
        boolean resultado = caja.extraer(960);
        assertTrue(resultado);
        assertEquals(980 - 979.2, caja.getSaldo(), 0.001);
    }

    @Test
    void cajaNoExtraeSiElMontoConComisionSuperaElSaldo() {
        // caja tiene saldo 980
        // extraer 962 → cuesta 962 * 1.02 = 981.24 > 980 → no puede
        boolean resultado = caja.extraer(962);
        assertFalse(resultado);
        assertEquals(980, caja.getSaldo(), 0.001); // saldo no cambia
    }

    @Test
    void cajaTransferirCobraComisionSoloAlOrigen() {
        CuentaCorriente destino = new CuentaCorriente();
        // caja tiene 980, transfiere 100 → cuesta 102 a la caja, destino recibe 100
        boolean resultado = caja.transferirACuenta(100, destino);
        assertTrue(resultado);
        assertEquals(878, caja.getSaldo(), 0.001); // 980 - 102 = 878
        assertEquals(100, destino.getSaldo());      // destino recibe el monto completo
    }

    @Test
    void cajaNoTransfiereConFondosInsuficientes() {
        CuentaCorriente destino = new CuentaCorriente();
        // caja tiene 980, intenta transferir 962 → costo 981.24 > 980 → falla
        boolean resultado = caja.transferirACuenta(962, destino);
        assertFalse(resultado);
        assertEquals(980, caja.getSaldo(), 0.001); // sin cambios
        assertEquals(0, destino.getSaldo());        // sin cambios
    }

    // ============================================================
    // Polimorfismo y Method Lookup
    // ============================================================

    @Test
    void methodLookupVariableCuentaInstanciaCorriente() {
        // La variable se declara como Cuenta (tipo estático),
        // pero el objeto real es CuentaCorriente (tipo dinámico).
        // El método extraer() despachado dinámicamente → usa la lógica de CuentaCorriente.
        Cuenta cuenta = new CuentaCorriente();
        cuenta.depositar(500);
        assertTrue(cuenta.extraer(300));
        assertEquals(200, cuenta.getSaldo());
    }

    @Test
    void methodLookupVariableCuentaInstanciaCaja() {
        // La variable es Cuenta, el objeto es CajaDeAhorro.
        // depositar() cobra 2% → saldo = 980.
        // extraer() cobra 2% → extraer 100 descuenta 102.
        Cuenta cuenta = new CajaDeAhorro();
        cuenta.depositar(1000); // saldo: 980
        assertTrue(cuenta.extraer(100));
        assertEquals(878, cuenta.getSaldo(), 0.001); // 980 - 102
    }

    @Test
    void transferirDeCajaACuentaCorriente() {
        // Origen: CajaDeAhorro con 980. Destino: CuentaCorriente.
        CuentaCorriente destino = new CuentaCorriente();
        caja.transferirACuenta(200, destino);
        assertEquals(980 - 204, caja.getSaldo(), 0.001); // 980 - (200*1.02)
        assertEquals(200, destino.getSaldo());             // destino no cobra comisión
    }

    @Test
    void transferirDeCuentaCorrienteACaja() {
        // Origen: CuentaCorriente con 1000. Destino: CajaDeAhorro.
        // CuentaCorriente.transferirACuenta usa la implementación de Cuenta (no cobra comisión al extraer).
        // Pero al llamar destino.depositar(200), CajaDeAhorro cobra 2%.
        CajaDeAhorro destino = new CajaDeAhorro();
        corriente.transferirACuenta(200, destino);
        assertEquals(800, corriente.getSaldo());            // corriente pierde 200 exactos
        assertEquals(196, destino.getSaldo(), 0.001);       // destino recibe 200 - 2% = 196
    }
}
