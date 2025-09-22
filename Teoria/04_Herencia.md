# Herencia en Programación Orientada a Objetos

## ¿Qué es la Herencia?

La **herencia** es un mecanismo que permite a una clase "heredar" estructura y comportamiento de otra clase. Es una estrategia fundamental para:
- Reusar código
- Reusar conceptos y definiciones
- Establecer relaciones jerárquicas entre clases

### Características en Java
- Solo existe **herencia simple** (una clase puede heredar de una sola superclase)
- Es una característica **transitiva** (si C hereda de B, y B hereda de A, entonces C hereda de A)

## Vocabulario Básico

```java
// Ejemplo de jerarquía
class InstrumentoFinanciero { }

class CuentaBancaria extends InstrumentoFinanciero {
    public void extraer(double monto) { /* implementación base */ }
}

class CuentaCorriente extends CuentaBancaria {
    @Override
    public void extraer(double monto) { /* implementación específica */ }
}
```

**Terminología:**
- `CuentaCorriente` es **subclase** de `CuentaBancaria`
- `CuentaBancaria` es **superclase** de `CuentaCorriente`
- `CuentaCorriente` **hereda** de `CuentaBancaria`
- `extraer()` en `CuentaCorriente` **redefine** o **extiende** el método de `CuentaBancaria`

## La Prueba "ES-UN"

Para identificar usos apropiados de herencia, pregúntate: **"¿X es un Y?"**

✅ **Ejemplos correctos:**
- Una cuenta corriente **ES UNA** cuenta bancaria
- Una ventana de texto **ES UNA** ventana
- Un robot **ES UN** agente

❌ **Anti-ejemplo:**
- Un círculo **NO ES UN** punto (un círculo *tiene* un punto como centro)

## Principio de Substitución de Liskov

> Si en cualquier lugar del programa usamos una clase, debemos poder usar cualquiera de sus subclases y que el programa siga funcionando correctamente.

Este principio es más preciso que la intuición "es-un" y nos ayuda a verificar que nuestras jerarquías están bien diseñadas.

## Method Lookup con Herencia

### Proceso de búsqueda:
1. Cuando un objeto recibe un mensaje, se busca el método en su clase
2. Si no lo encuentra, busca en la superclase
3. Continúa hacia arriba en la jerarquía hasta encontrarlo

```java
CuentaCorriente cuenta = new CuentaCorriente();
cuenta.extraer(100); // Busca primero en CuentaCorriente, luego en CuentaBancaria
```

## Override y Super

### Override (Sobreescribir)
```java
class CuentaBancaria {
    public void extraer(double monto) {
        if (saldo >= monto) {
            saldo -= monto;
            System.out.println("Extracción realizada");
        }
    }
}

class CuentaCorriente extends CuentaBancaria {
    @Override
    public void extraer(double monto) {
        // Comportamiento específico para cuenta corriente
        if (saldo + descubiertoPermitido >= monto) {
            saldo -= monto;
            System.out.println("Extracción de cuenta corriente realizada");
        }
    }
}
```

### Super (Extender comportamiento)
```java
class CuentaCorriente extends CuentaBancaria {
    @Override
    public void extraer(double monto) {
        super.extraer(monto); // Llama al método de la superclase
        if (saldo < 0) {
            System.out.println("Advertencia: Saldo negativo");
        }
    }
}
```

### Super en constructores
```java
class CuentaBancaria {
    protected double saldo;
    
    public CuentaBancaria(double saldoInicial) {
        this.saldo = saldoInicial;
    }
}

class CuentaCorriente extends CuentaBancaria {
    private double descubiertoPermitido;
    
    public CuentaCorriente(double saldoInicial, double descubierto) {
        super(saldoInicial); // Debe ser la primera línea
        this.descubiertoPermitido = descubierto;
    }
}
```

## Clases Abstractas

Una **clase abstracta** captura comportamiento común pero no puede ser instanciada directamente.

```java
abstract class Forma {
    protected String color;
    
    public Forma(String color) {
        this.color = color;
    }
    
    // Método concreto
    public void pintar() {
        System.out.println("Pintando de color " + color);
    }
    
    // Método abstracto - debe ser implementado por subclases
    public abstract double calcularArea();
}

class Circulo extends Forma {
    private double radio;
    
    public Circulo(String color, double radio) {
        super(color);
        this.radio = radio;
    }
    
    @Override
    public double calcularArea() {
        return Math.PI * radio * radio;
    }
}
```

## Situaciones de Uso de Herencia

### ✅ Usos Apropiados

1. **Subclasificar para especializar**
   ```java
   class Vehiculo { }
   class Auto extends Vehiculo { } // Auto es un vehículo más específico
   ```

2. **Herencia para especificar** (con clases abstractas)
   ```java
   abstract class Animal {
       public abstract void hacerSonido();
   }
   class Perro extends Animal {
       public void hacerSonido() { System.out.println("Guau"); }
   }
   ```

3. **Subclasificar para extender**
   ```java
   class ListaBasica { }
   class ListaOrdenada extends ListaBasica {
       public void ordenar() { /* nuevo método */ }
   }
   ```

### ❌ Usos Problemáticos

1. **Heredar para construir** - Violar "es-un"
2. **Subclasificar para generalizar** - Hacer métodos menos específicos
3. **Subclasificar para limitar** - Desactivar funcionalidad
4. **Herencia indecisa** - No saber cuál debería ser la superclase

## Modificadores de Visibilidad

```java
class Padre {
    private String datoPrivado;     // Solo visible en esta clase
    protected String datoProtegido; // Visible en subclases
    public String datoPublico;      // Visible en todas partes
}

class Hijo extends Padre {
    public void metodo() {
        // datoPrivado = "x";        // ERROR: no accesible
        datoProtegido = "y";         // OK: accesible en subclases
        datoPublico = "z";           // OK: accesible en todas partes
    }
}
```

### Estrategia recomendada en OO1:
- Variables de instancia: **siempre privadas**
- Si una subclase necesita acceso: crear **accessors protegidos**

```java
class Padre {
    private String dato;
    
    protected String getDato() { return dato; }
    protected void setDato(String valor) { this.dato = valor; }
}
```

## Composición vs Herencia

### Ejemplo: Lista de Parecidos

**Opción 1 - Herencia:**
```java
class ListaDeParecidos extends ArrayList<Object> {
    private Object ejemplo;
    // Problema: "es-un" ArrayList, pero tiene restricciones
}
```

**Opción 2 - Composición (Recomendada):**
```java
class ListaDeParecidos {
    private Object ejemplo;
    private ArrayList<Object> elementos; // "tiene-un" ArrayList
    
    public boolean add(Object objeto) {
        if (esSimilar(objeto, ejemplo)) {
            return elementos.add(objeto);
        }
        throw new IllegalArgumentException("Objeto no similar");
    }
}
```

## Problema de Explosión de Clases

El ejemplo de **Robots** muestra las limitaciones de la herencia:

```java
// Esto lleva a una explosión combinatoria
class SolarRobot extends Robot { }
class NuclearRobot extends Robot { }
class CaterpillarRobot extends Robot { }
class SolarCaterpillarRobot extends ??? { } // ¿De quién hereda?
```

**Solución:** Usar **composición** en lugar de herencia múltiple:

```java
class Robot {
    private EnergySource energySource;
    private MovementSystem movementSystem;
    private WeaponSystem weaponSystem;
    
    public void consumeEnergy() {
        energySource.consume();
    }
    
    public void move() {
        movementSystem.move();
    }
}
```

## Interfaces vs Clases Abstractas

| Clase Abstracta | Interface |
|----------------|-----------|
| **ES UNA** clase | Define un **tipo/contrato** |
| Puede tener métodos concretos | Solo define firmas (pre-Java 8) |
| Herencia simple | Se pueden implementar múltiples |
| Puede tener variables de instancia | Solo constantes |

```java
interface Volador {
    void volar();
}

interface Nadador {
    void nadar();
}

class Pato extends Animal implements Volador, Nadador {
    public void volar() { /* implementación */ }
    public void nadar() { /* implementación */ }
}
```

## Puntos Clave para Recordar

1. **Usa herencia solo cuando se cumple "es-un"**
2. **Prefiere composición cuando hay dudas**
3. **El Principio de Liskov debe respetarse siempre**
4. **Las clases abstractas son útiles para comportamiento común**
5. **Super solo debe usarse para extender, no reemplazar completamente**
6. **La visibilidad debe manejarse cuidadosamente para mantener bajo acoplamiento**