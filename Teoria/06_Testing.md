# Testing y Tests de Unidad - Guía Completa

## 🛠️ ¿Qué es un Bug/Error?
Un bug es cuando el programa:
- **No hace** algo que debería hacer
- **Hace algo mal** 
- **Falla** completamente (revienta)

## 🎯 ¿Qué es Testear?
Asegurarse de que el programa:
- **Hace lo que se espera**
- **Lo hace como se espera**
- **No falla**

> **Objetivo principal**: Maximizar el hallazgo de bugs con la mínima cantidad de tests necesarios.

## 📊 Tipos de Tests (Responsabilidad del Programador)

### Tests Funcionales
Verifican que el sistema cumpla con los requisitos y funcionalidades esperadas.

### Tests de Unidad
Verifican que cada método funcione correctamente de forma **aislada** de otras unidades.

### Tests Automatizados
Se ejecutan automáticamente con herramientas como JUnit, detectando, recolectando y reportando errores/problemas.

> **Otros tipos importantes**: Tests de Integración (componentes trabajando juntos), Tests de Regresión (cambios no rompen funcionalidad existente).

---

## 🔬 Tests de Unidad en Detalle

**Definición**: Aseguran que la unidad mínima del programa (en nuestro caso, **un método**) funciona correctamente y **aislada** de otras unidades.

### ¿Qué considerar al testear un método?

#### **Pre-condiciones** (Estado inicial):
- **Parámetros** que recibe el método
- **Variables de instancia** del objeto
- **Estado del objeto** antes de ejecutar el método

#### **Post-condiciones** (Estado final):
- **Valor que retorna** el método
- **Estado del objeto** después de la ejecución

### Importante en Objetos 1:
- ❌ **NO testear**: setters, getters, constructores
- ✅ **SÍ testear**: métodos que implementan comportamiento del objeto

---

## ⚙️ JUnit 5 - Automatización de Tests

JUnit es una herramienta que simplifica la creación de tests automatizados, detectando, recolectando y reportando errores.

### Reglas básicas:
- **Una clase de test por cada clase** del programa
- Importante seguir la **sintaxis** (imports, anotaciones, estructura)
- El **Test Runner** administra y ejecuta los distintos tests

### Estructura básica de una clase de test:

```java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RobotTest {
    private Robot robot;  // Variables de instancia (Fixture)
    
    @BeforeEach  // Se ejecuta ANTES de cada @Test
    public void setUp() {
        robot = new Robot();  // Preparar el fixture
        // Inicializar estado común para todos los tests
    }
    
    @Test  // Marca un método como test
    public void testAvanzar() {
        robot.avanzar();
        assertEquals(1, robot.getPosicion());  // Verificar resultado
    }
    
    @Test
    public void testRetroceder() {
        robot.avanzar();
        robot.avanzar();
        robot.retroceder();
        assertEquals(1, robot.getPosicion());
    }
}
```

### Características clave:

#### **@BeforeEach**
- Se ejecuta **antes de cada test**
- Inicializa/prepara el objeto (fixture)
- **Uno solo por clase de test**
- Garantiza que cada test empiece con un estado limpio

#### **@Test**
- Marca un método como caso de prueba
- Se crea una **nueva instancia** para cada método de test
- Cada test es **independiente** y aislado

#### **Fixture**
Es la preparación común que necesitan todos (o la mayoría) de los tests. Se define en el `@BeforeEach`.

### Métodos de Assertion más comunes:

```java
// Igualdad
assertEquals(valorEsperado, valorActual);
assertNotEquals(valor1, valor2);

// Booleanos
assertTrue(condicion);
assertFalse(condicion);

// Nulidad
assertNull(objeto);
assertNotNull(objeto);

// Identidad (mismo objeto en memoria)
assertSame(objeto1, objeto2);

// Excepciones
assertThrows(ExcepcionEsperada.class, () -> {
    // código que debe lanzar la excepción
});
```

### ¿Cuándo usar cada assertion?
- **assertEquals**: Para verificar valores esperados
- **assertTrue/False**: Para verificar condiciones booleanas
- **assertNull/NotNull**: Para verificar existencia de objetos
- **assertSame**: Para verificar que dos referencias apuntan al mismo objeto
- **assertThrows**: Para verificar que se lanza una excepción cuando debería

---

## 🎲 Estrategias para Elegir Casos de Test

### Principio fundamental:
> **Maximizar la detección de errores con la mínima cantidad de tests**

### 1. Particiones de Equivalencia

Agrupar casos que **prueban lo mismo** o **revelarían el mismo bug**.

**Concepto**: Combinación de variables que forman **tuplas de datos** (var1, var2...par1, par2) que representan el estado del objeto.

#### Ejemplo con Robot Apagable:

**Variables a considerar**: estado (encendido/apagado), posición

**Particiones identificadas**:
- **Partición 1**: Robot encendido (cualquier posición válida)
  - Tupla ejemplo: `(encendido, posición=5)`
  - Comportamiento esperado: responde a comandos
  
- **Partición 2**: Robot apagado (cualquier posición)
  - Tupla ejemplo: `(apagado, posición=3)`
  - Comportamiento esperado: no responde a comandos

```java
@Test
public void testAvanzarRobotEncendido() {
    // Partición 1: robot encendido
    robot.encender();
    robot.avanzar();
    assertEquals(1, robot.getPosicion());
}

@Test
public void testAvanzarRobotApagado() {
    // Partición 2: robot apagado
    // robot está apagado por defecto
    robot.avanzar();
    assertEquals(0, robot.getPosicion()); // No se movió
}
```

#### Particiones implícitas:
> **Consejo del docente**: Puede agregarse una tupla que no está explícitamente en el código, sino que se deduce por combinaciones lógicas que llevan a la misma conclusión.

### 2. Valores de Borde (Boundary Values)

Los errores ocurren frecuentemente en los **límites**. Buscar:
- Primero/último, máximo/mínimo
- Vacío/lleno, antes/después
- Valores límite en rangos

#### Ejemplo con Robot Positivista (solo posiciones 0 a Integer.MAX_VALUE):

**Tuplas para testear avanzar()**:
- `(posición=0, avanzar(1))` → resultado esperado: `posición=1`
- `(posición=Integer.MAX_VALUE-1, avanzar(1))` → resultado esperado: `posición=Integer.MAX_VALUE`
- `(posición=Integer.MAX_VALUE, avanzar(1))` → ¿Qué debería pasar? ¿Lanza excepción? ¿Se queda en MAX_VALUE?

**Tuplas para testear retroceder()**:
- `(posición=0, retroceder(1))` → ¿Qué debería pasar?
- `(posición=1, retroceder(1))` → resultado esperado: `posición=0`
- `(posición=Integer.MAX_VALUE, retroceder(1))` → resultado esperado: `posición=Integer.MAX_VALUE-1`

```java
@Test
public void testAvanzarDesdeCero() {
    // Valor de borde: posición mínima
    robot.avanzar();
    assertEquals(1, robot.getPosicion());
}

@Test
public void testRetrocederEnCero() {
    // Valor de borde: no puede ser negativo
    robot.retroceder();
    assertEquals(0, robot.getPosicion()); // Se queda en 0
}
```

---

## 🤖 Ejemplo Práctico Completo: Robot Saltarín y Hambriento

```java
// Robot que consume energía según su hambre al saltar
public class SaltarinHambriento {
    private int position = 0;
    private int energy = 0;
    private int hunger = 1;  // mínimo 1
    
    public void jumpForward(int places) {
        int consumed = places * hunger;
        if (energy >= consumed) {
            position += places;
            energy -= consumed;
        }
        // Si no tiene energía suficiente, se queda en el lugar
    }
    
    public void addEnergy(int amount) {
        energy += amount;
    }
    
    public int getPosition() {
        return position;
    }
    
    public int getEnergy() {
        return energy;
    }
}
```

### Identificación de casos de test con tuplas:

**Variables del estado**: `(position, energy, hunger)`

**Parámetros**: `places` (lugares a saltar)

**Tuplas completas para jumpForward()**:

#### Partición 1 - Sin energía suficiente:
- **Tupla**: `(position=0, energy=0, hunger=1, places=1)`
  - **Pre-condición**: Robot en posición 0, sin energía, hambre=1
  - **Acción**: `jumpForward(1)`
  - **Post-condición esperada**: `position=0, energy=0` (no se movió)

```java
@Test
public void testJumpForwardSinEnergia() {
    SaltarinHambriento robot = new SaltarinHambriento();
    // energy=0 por defecto
    robot.jumpForward(1);
    assertEquals(0, robot.getPosition());
    assertEquals(0, robot.getEnergy());
}
```

#### Partición 2 - Con energía suficiente:
- **Tupla**: `(position=0, energy=5, hunger=1, places=2)`
  - **Pre-condición**: Robot en posición 0, energía=5, hambre=1
  - **Acción**: `jumpForward(2)`
  - **Post-condición esperada**: `position=2, energy=3` (consumió 2*1=2 de energía)

```java
@Test
public void testJumpForwardConEnergia() {
    SaltarinHambriento robot = new SaltarinHambriento();
    robot.addEnergy(5);
    robot.jumpForward(2);
    assertEquals(2, robot.getPosition());
    assertEquals(3, robot.getEnergy());
}
```

#### Valor de borde - Energía exacta:
- **Tupla**: `(position=0, energy=4, hunger=2, places=2)`
  - **Pre-condición**: Robot en posición 0, energía=4, hambre=2
  - **Acción**: `jumpForward(2)`
  - **Post-condición esperada**: `position=2, energy=0` (consume exactamente toda la energía: 2*2=4)

```java
@Test
public void testJumpForwardEnergiaExacta() {
    SaltarinHambriento robot = new SaltarinHambriento();
    robot.addEnergy(4);
    // Asumiendo que podemos modificar hunger, o testeando con valores por defecto
    robot.jumpForward(4); // Con hunger=1, consume 4
    assertEquals(4, robot.getPosition());
    assertEquals(0, robot.getEnergy());
}
```

---

## 📝 Documentación de Casos de Test

> **⚠️ MUY IMPORTANTE**: Cuando el enunciado dice "identifique", "escriba", "documente" o "justifique" los casos de test, se debe escribir la **tupla con TODOS sus valores explícitos**.

### Formato de documentación:

**Caso de Test N°**: [número]
**Objetivo**: [qué bug busca encontrar o qué comportamiento verifica]
**Partición/Borde**: [indicar cuál]
**Tupla de estado**: `(var1=valor1, var2=valor2, ..., param1=valor1, ...)`
**Pre-condición**: [descripción del estado inicial]
**Acción**: [método y parámetros]
**Post-condición esperada**: [estado final esperado]

#### Ejemplo:

**Caso de Test 1**
- **Objetivo**: Verificar que el robot no salta si no tiene energía suficiente
- **Partición**: Sin energía suficiente
- **Tupla**: `(position=0, energy=0, hunger=1, places=1)`
- **Pre-condición**: Robot en posición 0, sin energía, nivel de hambre 1
- **Acción**: `jumpForward(1)`
- **Post-condición esperada**: Robot permanece en posición 0, energía sigue en 0

---

## 🔄 Proceso de Testing: TDD (Test-Driven Development)

### Flujo recomendado por el docente:

1. **Antes de modificar código**: Primero reproduzco la falla a modo de test
2. **Encuentro el error puntual**: El test debe fallar mostrando exactamente dónde está el problema
3. **Modifico el código**: Corrijo el bug
4. **Verifico**: El test ahora debe pasar

> **💡 Consejo**: Los tests son una forma de documentar qué se espera del código y cómo debería comportarse.

---

## 📈 Cobertura de Tests

Mide qué tan completos son nuestros tests:

- **Líneas cubiertas**: % de líneas de código ejecutadas por los tests
- **Métodos cubiertos**: % de métodos testeados
- **Branches (ramas)**: % de caminos de ejecución probados (if/else, switch, loops)

### Meta:
Buscar alta cobertura, pero recordar que **100% de cobertura no garantiza 0% de bugs**. Lo importante es la **calidad** de los tests, no solo la cantidad.

---

## ⚠️ Errores Comunes a Evitar

### 1. Tests duplicados entre clases
> **Error común**: Si hay varias clases que se complementan, se realizan los tests para cada una sin tener en cuenta lo que ya testea otra.

**Solución**: Analizar qué responsabilidad tiene cada clase y testear solo esa responsabilidad específica.

### 2. Testear setters/getters/constructores
En Objetos 1, estos métodos triviales no se testean a menos que tengan lógica compleja.

### 3. Tests no independientes
Cada test debe poder ejecutarse solo, sin depender del orden o resultado de otros tests.

### 4. Fixtures incompletos
Si varios tests necesitan el mismo setup complejo, debe estar en `@BeforeEach`.

### 5. No testear excepciones
Si un método debería lanzar una excepción en ciertos casos, eso **debe** testearse:

```java
@Test
public void testAvanzarRobotApagadoLanzaExcepcion() {
    Robot robot = new Robot(); // apagado por defecto
    assertThrows(RobotApagadoException.class, () -> {
        robot.avanzar();
    });
}
```

---

## 🎯 Principios Clave para Testing Efectivo

1. **Testear con propósito**: Cada test busca un bug específico o verifica un comportamiento concreto
2. **Testear temprano y frecuentemente**: Integrar testing desde el inicio del desarrollo
3. **Pensar en qué puede fallar**: Estados límite, parámetros inválidos, relaciones entre objetos
4. **Maximizar detección de errores** con mínima cantidad de tests (usar particiones y bordes)
5. **Tests son parte del software** y un indicador de calidad del código
6. **Independencia**: Cada test debe ejecutarse aislado de otros
7. **Claridad**: El nombre del test debe indicar qué se está probando

---

## 💡 Resumen de Metodología

### Al diseñar tests:

1. **Identificar variables** que afectan el comportamiento (estado + parámetros)
2. **Crear tuplas** con diferentes combinaciones de valores
3. **Agrupar en particiones** de equivalencia
4. **Identificar valores de borde** críticos
5. **Documentar cada caso** con pre y post-condiciones
6. **Implementar en JUnit** siguiendo la estructura correcta
7. **Verificar independencia** de cada test

### Checklist antes de entregar:

- ✅ Cada clase tiene su clase de test
- ✅ Fixture bien definido en `@BeforeEach`
- ✅ Tests independientes entre sí
- ✅ Casos de borde cubiertos
- ✅ Particiones identificadas y documentadas
- ✅ Nombres de tests descriptivos
- ✅ Assertions apropiados
- ✅ No se testean setters/getters/constructores simples

---

## 💭 Recordatorio Final

En Programación Orientada a Objetos, nos enfocamos en que **cada objeto haga lo que se espera, como se espera**. 

Los tests nos ayudan a:
- Entender mejor qué se espera de nuestros objetos
- Garantizar su correcto funcionamiento
- Documentar el comportamiento esperado
- Detectar bugs tempranamente
- Facilitar cambios futuros con confianza

**Los tests son tan importantes como el código mismo**. Son nuestra red de seguridad y la documentación viva de cómo debe comportarse nuestro sistema.