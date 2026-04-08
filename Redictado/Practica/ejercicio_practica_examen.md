# Ejercicio de práctica para examen — Agencia de Viajes

> Tiempo estimado: 2 horas. Intentar resolverlo sin mirar las respuestas.
> Las soluciones están al final en bloques colapsables.

---

## Por qué este ejercicio cubre todo el examen

Analicé los 5 parciales reales disponibles (08/11/2025, 29/11/2025, redictado 2024, ARBA,
Municipio). En **todos** se repite exactamente la misma estructura de 4 ejercicios:

| Ej del parcial real | Tipo | Cómo lo cubre este ejercicio |
|---|---|---|
| Ej1 | UML desde texto | Ej1: modelar agencia con 4+ clases desde descripción |
| Ej2 | Código con 2 listas → rediseño polimórfico + Java | Ej2: `Cliente` con `List<PaqueteNacional>` + `List<PaqueteInternacional>` → unificar |
| Ej3 | Method lookup, múltiple opción, binding dinámico | Ej3: jerarquía con `super`, preguntas A/B/C/D idénticas a los parciales reales |
| Ej4 | Instanciación + tests particiones/bordes | Ej4: instanciar con `LocalDate.of()` + 2 métodos con condiciones para testear |

Conceptos cubiertos:

| Concepto | Dónde aparece |
|---|---|
| Clase abstracta | `Paquete` en Ej2 y Ej3 |
| Polimorfismo | `calcularCosto()` sobre una sola colección (Ej2) |
| Herencia + `super` | `PaqueteNacional.getAdicional()` llama `super.getAdicional()` (Ej3) |
| Binding dinámico | `this.calcularBase()` en `Paquete.calcularCosto()` se resuelve en runtime (Ej3) |
| Tipos válidos en compilación vs runtime | Preguntas A y B del Ej3 |
| Colecciones | `List<Paquete>` en `Cliente` (Ej2) |
| LocalDate | `fechaSalida` en cada paquete, instanciación con `LocalDate.of()` (Ej4) |
| Delegación (GRASP Experto) | `Cliente.calcularGastoTotal()` delega en cada `Paquete` (Ej2) |
| Particiones equivalentes | Ej4: ≤4 vs >4 personas; ≤10 vs >10 días |
| Valores de borde | Ej4: exactamente 4, 5, 10 y 11 |
| Encapsulamiento | Atributos privados, colecciones inicializadas en constructor |

---

## Top 3 de la guía — lo que sí o sí hay que dominar

### 1. Method lookup y binding dinámico (Ej3 de todos los parciales)

Es el ejercicio donde más se pierde y el más mecánico de aprender.
La clave es una sola regla: **el método se busca desde la clase del objeto real, no de la variable.**

```java
Paquete p = new PaqueteNacional(...);  // variable tipo Paquete, objeto real PaqueteNacional
p.calcularCosto();  // busca calcularCosto() empezando en PaqueteNacional
```

Cuando dentro del método aparece `this.algo()`, `this` siempre es el objeto real.
Cuando aparece `super.algo()`, sube UN nivel en la jerarquía desde la clase actual.

Practicar: para cada pregunta del Ej3, trazar el recorrido paso a paso a mano.

### 2. El patrón de rediseño con polimorfismo (Ej2 de todos los parciales)

El enunciado siempre es el mismo: hay una clase con 2 (o 3) listas separadas y un método
con 2 (o 3) for-loops repetidos. La solución siempre es la misma:

1. Crear clase abstracta con `calcularX()` abstracto
2. Cada clase concreta implementa `calcularX()` a su manera
3. La clase "dueña" pasa a tener `List<ClaseAbstracta>` y un único for-loop
4. La nueva subclase pedida en el enunciado solo requiere implementar `calcularX()`

Memorizar esta estructura. En el parcial real no hay tiempo para reinventarla.

### 3. Tests: identificar particiones y bordes rápido (Ej4 de todos los parciales)

El método para hacerlo en 2 minutos:
1. Buscar todos los `if` en el método → cada condición crea 2 particiones
2. Para cada condición, el borde es el valor exacto donde cambia el comportamiento
   (probar el valor del límite mismo + el inmediato siguiente)
3. Si hay 2 condiciones independientes → 4 particiones mínimas

Ejemplo para `if (personas > 4)`:
- Partición 1: personas ≤ 4 → representante: personas = 3
- Partición 2: personas > 4 → representante: personas = 6
- Borde 1: personas = 4 (NO descuenta)
- Borde 2: personas = 5 (SÍ descuenta)

---

## Contexto del dominio

Una agencia de viajes gestiona paquetes turísticos para sus clientes. Los paquetes pueden ser
de distintos tipos y el costo de cada uno se calcula de manera diferente.

De cada **cliente** se conoce su nombre y email. Un cliente puede haber contratado varios paquetes.

Los **paquetes** tienen fecha de salida, cantidad de días y cantidad de personas.
Actualmente existen dos tipos:

- **Paquete nacional**: precio por persona por día.
  El costo base es `precioPorPersona × personas × días`.
  Si hay **más de 4 personas**, se aplica un **descuento del 15%** sobre el costo base.

- **Paquete internacional**: precio base del destino + seguro obligatorio de **$300 por persona**.
  Si el viaje dura **más de 10 días**, se aplica un **recargo del 20%** sobre el total (base + seguro).

Cada paquete registra si ya fue **pagado** (boolean, inicia en `false`).

---

## Ejercicio 1 — Modelo de dominio (UML)

Realice un diagrama de clases UML donde identifique las clases del dominio, sus atributos, y las
relaciones con sus roles y cardinalidades según corresponda.

El sistema debe permitir:

- **Contratar un paquete**: asociar un paquete a un cliente.
- **Calcular el gasto total de un cliente**: suma de costos de todos sus paquetes.
- **Listar paquetes pendientes de pago** de un cliente (los no pagados aún).
- **Marcar un paquete como pagado**.

No debe incluir comportamiento (métodos) en el diagrama.

---

## Ejercicio 2 — Rediseño con polimorfismo

El sistema actual fue implementado de la siguiente manera:

```java
public class Cliente {
    private String nombre;
    private String email;
    private List<PaqueteNacional> paquetesNacionales;
    private List<PaqueteInternacional> paquetesInternacionales;

    public Cliente(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
        this.paquetesNacionales = new ArrayList<>();
        this.paquetesInternacionales = new ArrayList<>();
    }

    public void agregarPaqueteNacional(PaqueteNacional p) {
        paquetesNacionales.add(p);
    }

    public void agregarPaqueteInternacional(PaqueteInternacional p) {
        paquetesInternacionales.add(p);
    }

    public double calcularGastoTotal() {
        double total = 0;
        for (PaqueteNacional p : paquetesNacionales) {
            total += p.calcularCosto();
        }
        for (PaqueteInternacional p : paquetesInternacionales) {
            total += p.calcularCosto();
        }
        return total;
    }
}

public class PaqueteNacional {
    private double precioPorPersona;
    private int personas;
    private int dias;
    private LocalDate fechaSalida;
    private boolean pagado;

    public PaqueteNacional(double precioPorPersona, int personas, int dias,
                           LocalDate fechaSalida) {
        this.precioPorPersona = precioPorPersona;
        this.personas = personas;
        this.dias = dias;
        this.fechaSalida = fechaSalida;
        this.pagado = false;
    }

    public double calcularCosto() {
        double base = precioPorPersona * personas * dias;
        if (personas > 4) {
            base *= 0.85;
        }
        return base;
    }
}

public class PaqueteInternacional {
    private double precioBase;
    private int personas;
    private int dias;
    private LocalDate fechaSalida;
    private boolean pagado;

    public PaqueteInternacional(double precioBase, int personas, int dias,
                                LocalDate fechaSalida) {
        this.precioBase = precioBase;
        this.personas = personas;
        this.dias = dias;
        this.fechaSalida = fechaSalida;
        this.pagado = false;
    }

    public double calcularCosto() {
        double total = precioBase + (300 * personas);
        if (dias > 10) {
            total *= 1.20;
        }
        return total;
    }
}
```

Se desea incorporar un nuevo tipo: **PaqueteTodoIncluido**, que tiene un precio fijo por persona
más $500 por persona en concepto de actividades. Sin descuentos ni recargos.
`calcularCosto() = (precioFijoPorPersona + 500) * personas`

### Tareas:

1. Rediseñe para que `Cliente` tenga **una única colección** de paquetes y calcule los costos
   **polimórficamente**. Aplique herencia, clase abstracta y polimorfismo. Realice un
   **diagrama de clases UML completo** incluyendo métodos y constructores.

2. **Reimplemente en Java** respetando el nuevo diseño.

---

## Ejercicio 3 — Method lookup y binding dinámico

Dado el siguiente diagrama y los fragmentos de código indicados:

```
[abstract] Paquete
  # personas: int
  # dias: int
  + calcularCosto(): double        →  return this.calcularBase() + this.getAdicional()
  + calcularBase(): double         [abstract]
  + getAdicional(): double         →  return 0.0          ← concreto en Paquete

PaqueteNacional extends Paquete
  - precioPorPersona: double
  + calcularBase(): double         →  return precioPorPersona * personas * dias
  + getAdicional(): double         →  return super.getAdicional() + 100.0

PaqueteInternacional extends Paquete
  - precioBase: double
  + calcularBase(): double         →  return precioBase + (300 * personas)
```

Dado el siguiente fragmento, responda A y B:

```java
? p1 = new PaqueteNacional(200.0, 2, 3);
p1.calcularCosto();
p1.getAdicional();
```

**A.** Indique la opción que incluye **todos** los tipos válidos para declarar `p1` en tiempo de compilación:
1. `PaqueteNacional`
2. `Paquete, PaqueteNacional`
3. `Object, Paquete, PaqueteNacional`
4. `Object, PaqueteNacional`

**B.** Suponiendo `p1` declarada como `Paquete`, indique **todos** los tipos que pueden instanciarse y asignarse a `p1`:
1. `Paquete, PaqueteNacional, PaqueteInternacional`
2. `PaqueteNacional, PaqueteInternacional`
3. Solo `PaqueteNacional`
4. Solo `Paquete`

**C.** ¿Qué retorna el siguiente fragmento?
```java
Paquete p = new PaqueteNacional(200.0, 2, 3);
p.calcularCosto();
```
1. 1200.0
2. 1300.0
3. 1100.0
4. 1400.0

**D.** ¿Qué retorna el siguiente fragmento?
```java
Paquete p = new PaqueteInternacional(1000.0, 2, 5);
p.calcularCosto();
```
1. 1600.0
2. 1000.0
3. 1700.0
4. 1300.0

---

## Ejercicio 4 — Instanciación y casos de test

Usando el diseño del Ejercicio 2 (después del rediseño):

### Tarea 1 — Instanciación

Instancie en Java los siguientes objetos:

- Cliente **Ana García**, email `ana@mail.com`:
  - Paquete nacional: $500/persona, 3 personas, 7 días, salida 15/07/2026
  - Paquete internacional: precio base $2000, 5 personas, 12 días, salida 01/08/2026

- Cliente **Marcos Díaz**, email `marcos@mail.com`:
  - Paquete todo incluido: $1500/persona, 2 personas, 5 días, salida 20/07/2026

Agregue cada paquete al cliente correspondiente usando el método `agregarPaquete()`.

> Ayuda: `LocalDate.of(year, month, day)`

### Tarea 2 — Casos de test

Diseñe los casos de test para `calcularCosto()` de `PaqueteNacional` y `PaqueteInternacional`,
aplicando **particiones equivalentes** y **valores de borde**.

Para cada caso indicar: nombre del test, fixture, resultado esperado y justificación.

---

## Soluciones

<details>
<summary>Ejercicio 2 — Diseño (no abrir hasta intentarlo)</summary>

```java
// Clase abstracta
public abstract class Paquete {
    protected int personas;
    protected int dias;
    protected LocalDate fechaSalida;
    protected boolean pagado;

    public Paquete(int personas, int dias, LocalDate fechaSalida) {
        this.personas = personas;
        this.dias = dias;
        this.fechaSalida = fechaSalida;
        this.pagado = false;
    }

    public abstract double calcularCosto();

    public void marcarComoPagado() { this.pagado = true; }
    public boolean isPagado() { return pagado; }
}

// Subclases
public class PaqueteNacional extends Paquete {
    private double precioPorPersona;

    public PaqueteNacional(double precioPorPersona, int personas, int dias,
                           LocalDate fechaSalida) {
        super(personas, dias, fechaSalida);
        this.precioPorPersona = precioPorPersona;
    }

    @Override
    public double calcularCosto() {
        double base = precioPorPersona * personas * dias;
        if (personas > 4) base *= 0.85;
        return base;
    }
}

public class PaqueteInternacional extends Paquete {
    private double precioBase;

    public PaqueteInternacional(double precioBase, int personas, int dias,
                                LocalDate fechaSalida) {
        super(personas, dias, fechaSalida);
        this.precioBase = precioBase;
    }

    @Override
    public double calcularCosto() {
        double total = precioBase + (300 * personas);
        if (dias > 10) total *= 1.20;
        return total;
    }
}

public class PaqueteTodoIncluido extends Paquete {
    private double precioFijoPorPersona;

    public PaqueteTodoIncluido(double precioFijoPorPersona, int personas,
                               int dias, LocalDate fechaSalida) {
        super(personas, dias, fechaSalida);
        this.precioFijoPorPersona = precioFijoPorPersona;
    }

    @Override
    public double calcularCosto() {
        return (precioFijoPorPersona + 500) * personas;
    }
}

// Cliente rediseñado
public class Cliente {
    private String nombre;
    private String email;
    private List<Paquete> paquetes;

    public Cliente(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
        this.paquetes = new ArrayList<>();
    }

    public void agregarPaquete(Paquete p) { paquetes.add(p); }

    public double calcularGastoTotal() {
        double total = 0;
        for (Paquete p : paquetes) {
            total += p.calcularCosto();
        }
        return total;
    }

    public List<Paquete> getPaquetesPendientes() {
        List<Paquete> pendientes = new ArrayList<>();
        for (Paquete p : paquetes) {
            if (!p.isPagado()) pendientes.add(p);
        }
        return pendientes;
    }
}
```

</details>

<details>
<summary>Ejercicio 3 — Respuestas</summary>

**A → opción 3** (`Object, Paquete, PaqueteNacional`)
- `PaqueteNacional`: siempre válido (tipo exacto)
- `Paquete`: válido, PaqueteNacional es-un Paquete
- `Object`: toda clase en Java hereda de Object
- `PaqueteInternacional` NO válido: es clase hermana, no padre/ancestro

**B → opción 2** (`PaqueteNacional, PaqueteInternacional`)
- `Paquete` es abstracta: `new Paquete()` no compila
- Ambas subclases concretas pueden asignarse a una variable de tipo `Paquete`

**C → opción 2** (1300.0)

Traza de ejecución paso a paso:
- `p.calcularCosto()` → busca en `PaqueteNacional` → no está → sube a `Paquete` → lo encuentra
- Dentro: `this.calcularBase() + this.getAdicional()`
- `this` es `PaqueteNacional`, entonces:
  - `this.calcularBase()` → busca en `PaqueteNacional` → `200.0 × 2 × 3 = 1200.0`
  - `this.getAdicional()` → busca en `PaqueteNacional` → `super.getAdicional() + 100.0`
    - `super.getAdicional()` → va a `Paquete` → retorna `0.0`
    - resultado: `0.0 + 100.0 = 100.0`
- Total: `1200.0 + 100.0 = 1300.0`

**D → opción 1** (1600.0)

Traza:
- `p.calcularCosto()` → en `Paquete`: `this.calcularBase() + this.getAdicional()`
- `this` es `PaqueteInternacional`:
  - `this.calcularBase()` → `1000.0 + (300 × 2) = 1600.0`
  - `this.getAdicional()` → no está en `PaqueteInternacional` → hereda de `Paquete` → `0.0`
- Total: `1600.0 + 0.0 = 1600.0`

</details>

<details>
<summary>Ejercicio 4 — Instanciación y casos de test</summary>

### Instanciación

```java
// Ana García
Cliente ana = new Cliente("Ana García", "ana@mail.com");

PaqueteNacional p1 = new PaqueteNacional(500.0, 3, 7, LocalDate.of(2026, 7, 15));
PaqueteInternacional p2 = new PaqueteInternacional(2000.0, 5, 12, LocalDate.of(2026, 8, 1));
ana.agregarPaquete(p1);
ana.agregarPaquete(p2);

// Marcos Díaz
Cliente marcos = new Cliente("Marcos Díaz", "marcos@mail.com");
PaqueteTodoIncluido p3 = new PaqueteTodoIncluido(1500.0, 2, 5, LocalDate.of(2026, 7, 20));
marcos.agregarPaquete(p3);
```

### Casos de test — PaqueteNacional.calcularCosto()

Condición: `personas > 4` → 2 particiones, borde en 4/5

| Test | personas | días | precio/p | Esperado | Justificación |
|---|---|---|---|---|---|
| `costoSinDescuento` | 3 | 5 | 200 | 3000.0 | Partición: ≤4 personas (no descuenta) |
| `costoConDescuento` | 6 | 5 | 200 | 5100.0 | Partición: >4 personas (descuenta 15%) |
| `bordeNoDescuenta` | 4 | 5 | 200 | 4000.0 | Borde: exactamente 4 → NO descuenta |
| `bordeDescuenta` | 5 | 5 | 200 | 4250.0 | Borde: exactamente 5 → SÍ descuenta |

### Casos de test — PaqueteInternacional.calcularCosto()

Condición: `dias > 10` → 2 particiones, borde en 10/11

| Test | días | personas | precioBase | Esperado | Justificación |
|---|---|---|---|---|---|
| `costoSinRecargo` | 7 | 2 | 1000 | 1600.0 | Partición: ≤10 días (no recarga) |
| `costoConRecargo` | 15 | 2 | 1000 | 1920.0 | Partición: >10 días (recarga 20%) |
| `bordeNoRecarga` | 10 | 2 | 1000 | 1600.0 | Borde: exactamente 10 → NO recarga |
| `bordeRecarga` | 11 | 2 | 1000 | 1920.0 | Borde: exactamente 11 → SÍ recarga |

</details>
