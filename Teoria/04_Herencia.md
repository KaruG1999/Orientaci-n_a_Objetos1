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

Cuando un objeto recibe un mensaje, la búsqueda del método sigue un orden preciso:

### Proceso de búsqueda:
1. Se busca el método en **la clase del objeto** (la declarada en `new`)
2. Si no se encuentra, se sube a la superclase
3. Se continúa hacia arriba hasta encontrarlo o llegar a `Object`
4. Si no se encuentra en ningún lado → error en tiempo de ejecución

```java
CuentaCorriente cuenta = new CuentaCorriente();
cuenta.extraer(100);
// Java busca extraer() en CuentaCorriente → lo encuentra → lo ejecuta

CuentaBancaria cuenta2 = new CuentaCorriente();
cuenta2.depositar(500);
// Java busca depositar() en CuentaCorriente → no está → sube a CuentaBancaria → lo ejecuta
```

### El tipo de la variable NO determina qué método se ejecuta

Esto es fundamental: el tipo de la variable sirve para el **chequeo del compilador** (¿puedo enviar este mensaje?), pero en tiempo de ejecución lo que importa es el **tipo real del objeto**.

```java
CuentaBancaria ref = new CuentaCorriente(); // variable tipo CuentaBancaria
ref.extraer(100);
// ¿Qué extraer() se ejecuta? → el de CuentaCorriente (el objeto real)
// Aunque la variable sea de tipo CuentaBancaria
```

> Esto es exactamente el **binding dinámico**: el método se resuelve en tiempo de ejecución según el objeto, no según la variable.

### `this` en el contexto de herencia

`this` siempre apunta al **objeto que recibió el mensaje original**, sin importar en qué clase se está ejecutando el código.

```java
public class CuentaBancaria {
    private double saldo;

    public void depositar(double monto) {
        this.saldo += monto;           // this es el objeto real
        this.notificar();              // busca notificar() desde el objeto real (binding dinámico)
    }

    public void notificar() {
        System.out.println("Depósito en cuenta base");
    }
}

public class CuentaCorriente extends CuentaBancaria {
    @Override
    public void notificar() {
        System.out.println("Depósito en cuenta corriente");
    }
}

// Uso:
CuentaBancaria c = new CuentaCorriente();
c.depositar(100);
// Ejecuta depositar() de CuentaBancaria (no redefinido en CuentaCorriente)
// Dentro, this.notificar() → busca desde CuentaCorriente → ejecuta el de CuentaCorriente
// Output: "Depósito en cuenta corriente"
```

### `super` en el contexto de herencia

`super` no apunta al tipo de la variable ni a la superclase del objeto — apunta a la **superclase de la clase donde está escrito el código**.

```java
public class CuentaCorriente extends CuentaBancaria {
    @Override
    public void extraer(double monto) {
        super.extraer(monto);   // busca extraer() en CuentaBancaria (superclase de CuentaCorriente)
        // acá puede agregar comportamiento extra
        this.cobrarComision();
    }
}
```

> **Regla de oro:** `this.metodo()` → empieza desde el objeto real (va hacia abajo).
> `super.metodo()` → empieza desde la superclase del código actual (va hacia arriba desde acá).

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

Los constructores **no se heredan**: cada clase define los suyos. Sin embargo, al construir un objeto de una subclase, la superclase también necesita inicializarse. Para eso se usa `super(...)` como primera instrucción del constructor.

```java
class CuentaBancaria {
    private double saldo;

    public CuentaBancaria(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    protected double getSaldo() { return this.saldo; }
    protected void setSaldo(double s) { this.saldo = s; }
}

class CuentaCorriente extends CuentaBancaria {
    private double descubiertoPermitido;

    public CuentaCorriente(double saldoInicial, double descubierto) {
        super(saldoInicial);                   // ← debe ser la PRIMERA línea
        this.descubiertoPermitido = descubierto;
    }
}
```

**Reglas importantes:**
- `super(...)` debe ser la **primera instrucción** del constructor de la subclase
- Si no se escribe explícitamente, Java llama automáticamente a `super()` (sin argumentos)
- Si la superclase no tiene constructor sin argumentos, la subclase **debe** llamar explícitamente a `super(...)` con los parámetros correctos
- Los constructores no son métodos: no se heredan, no se sobreescriben, no aparecen en el method lookup normal

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

## Patrón Template Method (Método Plantilla)

Una de las aplicaciones más importantes de las clases abstractas es el **Template Method**: una superclase define el "esqueleto" de un algoritmo en un método concreto, y delega los pasos variables a métodos abstractos que las subclases implementan.

### Concepto

La superclase dice: *"Sé cómo hacer esto en general, pero hay pasos que dependen del tipo específico. Vos (subclase) decidís cómo hacer esos pasos."*

```
Superclase (concreta)              Subclases (concretan los pasos)
─────────────────────              ──────────────────────────────
metodoPlantilla() {           →    puedeHacerlo()  → cada subclase lo define
  if (puedeHacerlo()) {            costoAdicional()→ cada subclase lo define
    hacerlo();
    aplicarCosto();
  }
}
abstract puedeHacerlo()
abstract costoAdicional()
```

### Ejemplo: Cuentas con diferentes políticas de extracción

```java
public abstract class Cuenta {
    protected double saldo;

    // MÉTODO PLANTILLA: define el esqueleto del algoritmo (concreto)
    public boolean extraer(double monto) {
        if (this.puedeExtraer(monto)) {
            this.saldo -= monto;
            this.saldo -= this.comision(monto);  // cada cuenta tiene su comisión
            return true;
        }
        return false;
    }

    // GANCHO 1: paso variable → cada subclase define su política
    protected abstract boolean puedeExtraer(double monto);

    // GANCHO 2: paso variable → cada subclase define su comisión
    protected abstract double comision(double monto);

    // Comportamiento común (concreto, compartido por todas)
    public void depositar(double monto) {
        this.saldo += monto;
    }

    public double getSaldo() {
        return this.saldo;
    }
}

public class CajaDeAhorro extends Cuenta {
    @Override
    protected boolean puedeExtraer(double monto) {
        return this.saldo >= monto;   // no puede quedar en negativo
    }

    @Override
    protected double comision(double monto) {
        return 0;                     // sin comisión
    }
}

public class CuentaCorriente extends Cuenta {
    private double descubierto;

    public CuentaCorriente(double saldoInicial, double descubierto) {
        super(saldoInicial);
        this.descubierto = descubierto;
    }

    @Override
    protected boolean puedeExtraer(double monto) {
        return this.saldo + this.descubierto >= monto;  // puede ir en negativo
    }

    @Override
    protected double comision(double monto) {
        return monto * 0.01;          // 1% de comisión
    }
}
```

### Lo que logra el patrón:

- El método `extraer()` está **escrito una sola vez** en la superclase
- Si cambia la lógica general de extracción, se cambia **en un solo lugar**
- Cada subclase solo implementa **su parte específica**
- Se evita la duplicación del algoritmo completo en cada subclase

### Diferencia con Override simple

| Override simple | Template Method |
|----------------|-----------------|
| La subclase **reemplaza** el método completo | La subclase **rellena huecos** del algoritmo |
| `super` es opcional | Los métodos abstractos son **obligatorios** |
| La lógica completa está en la subclase | La lógica general está en la superclase |

```java
// Override simple: CuentaCorriente reimplementa extraer() completo
@Override
public boolean extraer(double monto) {
    // reimplementa toda la lógica desde cero
}

// Template Method: CuentaCorriente solo define el criterio de autorización
@Override
protected boolean puedeExtraer(double monto) {
    return this.saldo + this.descubierto >= monto;
}
```

---

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

## Tipos y herencia — resumen

**Un tipo** es un conjunto de firmas de operaciones/métodos. En Java, cada clase e interfaz define un tipo.

- El tipo de una **variable** determina qué mensajes puede recibir
- El tipo de un **método** es lo que retorna
- El tipo en `new Clase()` es la clase que se instancia

```java
// La variable tiene tipo CuentaBancaria
// El objeto tiene tipo CuentaCorriente
CuentaBancaria ref = new CuentaCorriente();
//     ↑ tipo para el compilador        ↑ tipo real en ejecución

ref.extraer(100);
// El compilador verifica: ¿CuentaBancaria tiene extraer()? → sí, compila
// En ejecución: ¿de qué clase es el objeto? → CuentaCorriente → ejecuta extraer() de CuentaCorriente
```

> Las **interfaces NO son clases**: no tienen instancias propias, solo definen tipos.

### Visibilidad y herencia

- `private` → solo visible en la clase que lo declara. Las subclases no lo ven directamente.
- `protected` → visible en la clase y en todas sus subclases.
- `public` → visible en todas partes.

**Estrategia recomendada en esta materia:**
- Todas las variables de instancia: **privadas**
- Si una subclase necesita acceder: agregar **getters/setters protegidos** en la superclase

```java
public class Cuenta {
    private double saldo;               // privado

    protected double getSaldo() {       // accesible por subclases
        return this.saldo;
    }

    protected void setSaldo(double s) { // accesible por subclases
        this.saldo = s;
    }
}

public class CuentaCorriente extends Cuenta {
    public void aplicarInteres() {
        // this.saldo = ...;            // ❌ no compila, saldo es privado
        this.setSaldo(this.getSaldo() * 1.01);   // ✅ usa el accessor
    }
}
```

> **Nota:** El ocultamiento de información se mantiene incluso en herencia. Una subclase hereda el comportamiento pero no el acceso directo a lo privado.