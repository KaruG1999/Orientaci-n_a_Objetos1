package ar.edu.unlp.info.oo1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BaseTest {

    private BaseIntegral integral;
    private BaseTradicional tradicional;

    @BeforeEach
    void setUp() {
        integral    = new BaseIntegral("arroz", 2);
        tradicional = new BaseTradicional("arroz", 2);
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
