package ar.edu.unlp.info.oo1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BaseTest {

    private Base integral;
    private Base tradicional;

    @BeforeEach
    void setUp() {
        integral    = new Base("arroz", 2, true);
        tradicional = new Base("arroz", 2, false);
    }

    @Test
    void descripcionBaseIntegral() {
        assertEquals("Base de arroz (integral, 2 porciones)", integral.getDescripcion());
    }

    @Test
    void descripcionBaseTradicional() {
        assertEquals("Base de arroz (tradicional, 2 porciones)", tradicional.getDescripcion());
    }

    @Test
    void costoBaseIntegral() {
        assertEquals(2200.0, integral.getCostoEstimado());
    }

    @Test
    void costoBaseTradicional() {
        assertEquals(1500.0, tradicional.getCostoEstimado());
    }
}
