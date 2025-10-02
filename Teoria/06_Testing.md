# Testing y Tests de Unidad - Resumen

## 🐛 ¿Qué es un Bug/Error?
Un bug es cuando el programa:
- **No hace** algo que debería hacer
- **Hace algo mal** 
- **Falla** completamente (revienta)

## 🎯 ¿Qué es Testear?
Asegurarse de que el programa:
- **Hace lo que se espera**
- **Lo hace como se espera**
- **No falla**

## 📊 Tipos de Tests (Principales)
- **Tests de Unidad**: Verifican que cada método funcione correctamente de forma aislada
- **Tests de Integración**: Verifican que los componentes trabajen bien juntos
- **Tests Funcionales**: Verifican que el sistema cumpla los requisitos
- **Tests de Regresión**: Verifican que los cambios no rompan funcionalidad existente
- **Tests Automatizados**: Se ejecutan automáticamente con herramientas

## 🔬 Tests de Unidad
**Definición**: Aseguran que la unidad mínima del programa (en nuestro caso, **un método**) funciona correctamente y **aislada** de otras unidades.

### Qué considerar al testear un método:
- **Parámetros** de entrada
- **Estado del objeto** antes de la ejecución
- **Valor que retorna** el método
- **Estado del objeto** después de la ejecución

## ⚙️ JUnit - Automatización de Tests

### Estructura básica de una clase de test:
```java
public class RobotTest {
    private Robot robot;  // Variables de instancia
    
    @BeforeEach  // Se ejecuta antes de cada test
    public void setUp() {
        robot = new Robot();  // Preparar el fixture
    }
    
    @Test  // Marca un método como test
    public void testAvanzar() {
        robot.avanzar();
        assertEquals(1, robot.getPosicion());  // Verificar resultado
    }
}
```

### Características importantes:
- **Independencia**: Cada test se ejecuta de forma aislada
- **Nueva instancia** por cada test
- **Fixture**: Preparación común para todos los tests

## 🎲 Estrategias para Elegir Casos de Test

### 1. Particiones de Equivalencia
Agrupar casos que **prueban lo mismo** o **revelan el mismo bug**.

**Ejemplo con Robot Apagable**:
- **Partición 1**: Robot encendido (en cualquier posición)
- **Partición 2**: Robot apagado (en cualquier posición)

### 2. Valores de Borde
Los errores ocurren frecuentemente en los **límites**. Buscar:
- Primero/último, máximo/mínimo
- Vacío/lleno, antes/después
- Valores límite en rangos

**Ejemplo con Robot Positivista** (solo posiciones 0 a Integer.MAX_VALUE):
- **Bordes para avanzar**: posición 0, Integer.MAX_VALUE-1, Integer.MAX_VALUE
- **Bordes para retroceder**: posición 0, 1, Integer.MAX_VALUE

## 🤖 Ejemplo Práctico: Robot Saltarín y Hambriento

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
}
```

### Casos de test identificados:

**Para jumpForward():**
- **Partición 1 - Sin energía suficiente**: 
  - energy=0, hunger=1, places=1 → No se mueve
- **Partición 2 - Con energía suficiente**: 
  - energy=1, hunger=1, places=1 → Se mueve 1 lugar
- **Valores de borde**:
  - energy=5, hunger=2, places=2 → Consume exactamente toda la energía

## 📈 Cobertura de Tests
Mide qué tan completos son nuestros tests:
- **Líneas cubiertas**: % de líneas de código ejecutadas
- **Métodos cubiertos**: % de métodos testeados
- **Branches**: % de caminos de ejecución probados

## 🎯 Principios Clave para Testing Efectivo

1. **Testear con propósito**: Buscar bugs específicos
2. **Testear temprano y frecuentemente**
3. **Pensar en qué puede fallar**: Estados, parámetros, relaciones
4. **Maximizar detección de errores** con mínima cantidad de tests
5. **Tests son parte del software** y un indicador de calidad

## 💡 Recordatorio Important
Al testear en OO (Orientación a Objetos), nos enfocamos en que **cada objeto haga lo que se espera, como se espera**. Los tests nos ayudan a entender mejor qué se espera de nuestros objetos y garantizar su correcto funcionamiento.


## Apunte de clase 

